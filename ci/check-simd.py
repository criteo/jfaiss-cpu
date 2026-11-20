#!/usr/bin/env python3
"""Check that a runtime-dispatch (FAISS_OPT_LEVEL=dd) build is safe on every CPU.

A dd build compiles most of Faiss for a baseline x86-64 CPU (SSE4.2 + POPCNT)
and a few files once per SIMD level, with -mavx2 or -mavx512*. At run time only
the files of the selected level are entered. Two things can go wrong, and
neither shows on a build machine that has AVX-512:

1. No SIMD code in the final library. This happened to an earlier jar: the
   wrong static library was linked, and the "AVX2" jar was generic.

2. SIMD instructions on a path that every CPU runs. An inline function or a
   template used by both a baseline file and an AVX-512 file is compiled into
   both objects under the same symbol name, and the linker keeps one copy for
   everybody. If it keeps the AVX-512 one, a machine without AVX-512 dies with
   an illegal instruction, whatever FAISS_SIMD_LEVEL says.

Checks, with level 0 = baseline, 1 = AVX2, 2 = AVX-512:

  A. objects compiled at level 0 hold no level 1 or 2 instructions, and
     objects compiled at level 1 hold no level 2 instructions;
  B. for every function of the final library that is defined in objects of
     several levels, the copy the linker kept uses no instruction above the
     lowest of these levels;
  C. the final library holds both level 1 and level 2 functions.

The level of an instruction is recognised from objdump's disassembly (register
names and mnemonics). This is a heuristic, not a decoder: it can miss an
AVX-512 instruction that uses neither a zmm, a mask nor a high register.

usage: check-simd.py FAISS_BUILD_DIR LIBSWIGFAISS_SO [BASELINE_OBJECT...]

FAISS_BUILD_DIR is the CMake build directory of Faiss; BASELINE_OBJECT are
extra objects linked into the library (the SWIG wrapper), compiled at level 0.
"""
import collections
import os
import re
import subprocess
import sys

OBJDUMP = os.environ.get("OBJDUMP", "objdump")
NM = os.environ.get("NM", "nm")
LEVEL_NAMES = ["baseline", "AVX2", "AVX-512"]

# Operands: 512-bit registers, mask registers, registers 16-31, EVEX decorations.
AVX512_OPERAND = re.compile(r"%zmm|%k[0-7]\b|%[xy]mm(?:1[6-9]|2[0-9]|3[01])\b|\{1to|\{z\}")
# Mnemonics that only exist with an EVEX encoding.
AVX512_MNEMONIC = re.compile(
    r"v(?:pternlog|pconflict|plzcnt|prol|pror|pcompress|pexpand|pmullq"
    r"|pmovm2|pmov[bwdq]2m|pabsq|pmaxsq|pminsq|pmaxuq|pminuq|psraq|permi2"
    r"|permt2|pblendm|ptestm|ptestnm|cvtusi2|cvttp[sd]2u|cvtp[sd]2u|range"
    r"|reduce|rndscale|scalef|getexp|getmant|fixupimm|dbpsadbw|ppopcnt"
    r"|fpclass|align[dq]|broadcast[fi](?:32|64)x|extract[fi](?:32|64)x"
    r"|insert[fi](?:32|64)x|shuf[fi](?:32|64)x|blendmp|compressp|expandp)")
# VEX-encoded instructions (AVX, AVX2, FMA, F16C) all start with "v"; BMI2.
AVX2_MNEMONIC = re.compile(r"v[a-z]|(?:bzhi|mulx|pdep|pext|rorx|sarx|shlx|shrx)[lq]?$")
# Symbol names and comments objdump appends to an instruction.
ANNOTATION = re.compile(r"<[^>]*>|#.*")


def instruction_level(text):
    mnemonic, _, operands = ANNOTATION.sub("", text).strip().partition(" ")
    if AVX512_OPERAND.search(operands) or AVX512_MNEMONIC.match(mnemonic):
        return 2
    if "%ymm" in operands or AVX2_MNEMONIC.match(mnemonic):
        return 1
    return 0


SYMBOL = re.compile(r"^[0-9a-f]+ <(.+)>:$")
INSTRUCTION = re.compile(r"^\s*[0-9a-f]+:\t(.*)$")


def function_levels(path):
    """{symbol: [level, ...]}: one entry per function body found under that name."""
    levels = collections.defaultdict(list)
    process = subprocess.Popen([OBJDUMP, "-d", "--no-show-raw-insn", path],
                               stdout=subprocess.PIPE, universal_newlines=True)
    current = None
    for line in process.stdout:
        match = SYMBOL.match(line)
        if match:
            current = levels[match.group(1)]
            current.append(0)
            continue
        if current is None or current[-1] == 2:
            continue
        match = INSTRUCTION.match(line)
        if not match:
            continue
        current[-1] = max(current[-1], instruction_level(match.group(1)))
    if process.wait() != 0:
        sys.exit("objdump failed on " + path)
    return levels


def defined_functions(path):
    """Names of the non-local functions an object defines (strong or weak)."""
    out = subprocess.check_output([NM, "--defined-only", "-f", "posix", path],
                                  universal_newlines=True)
    return {parts[0] for parts in (line.split() for line in out.splitlines())
            if len(parts) >= 2 and parts[1] in ("T", "W", "V", "u", "i")}


def compiled_levels(build_dir):
    """{object path: level}, from the per-file options CMake recorded."""
    target_dir = os.path.join(build_dir, "faiss", "CMakeFiles", "faiss.dir")
    levels = {}
    for root, _, files in os.walk(target_dir):
        for name in files:
            if name.endswith(".o"):
                levels[os.path.join(root, name)] = 0
    custom = re.compile(r"^# Custom options: (\S+\.o)_OPTIONS = (.*)$")
    with open(os.path.join(target_dir, "flags.make")) as flags:
        for line in flags:
            match = custom.match(line)
            if not match:
                continue
            path = os.path.join(build_dir, match.group(1))
            if path not in levels:
                sys.exit("object named in flags.make was not built: " + path)
            options = match.group(2)
            levels[path] = 2 if "-mavx512" in options else 1 if "-mavx2" in options else 0
    return levels


def main():
    if len(sys.argv) < 3:
        sys.exit(__doc__)
    build_dir, library, extra_objects = sys.argv[1], sys.argv[2], sys.argv[3:]
    objects = compiled_levels(build_dir)
    per_level = collections.Counter(objects.values())
    print("Faiss objects: %d baseline, %d AVX2, %d AVX-512"
          % (per_level[0], per_level[1], per_level[2]))
    if not per_level[1] or not per_level[2]:
        sys.exit("FAIL: no per-level objects found: is this a FAISS_OPT_LEVEL=dd build?")
    for path in extra_objects:
        objects[path] = 0

    errors = []

    # A. no instruction above the level an object was compiled for.
    lowest = {}  # function -> lowest level of the objects defining it
    for path, level in sorted(objects.items()):
        name = os.path.relpath(path, build_dir) if path.startswith(build_dir) else path
        for symbol in defined_functions(path):
            lowest[symbol] = min(level, lowest.get(symbol, 2))
        if level == 2:
            continue
        for symbol, found in sorted(function_levels(path).items()):
            if max(found) > level:
                errors.append("A: %s (compiled for %s) has %s instructions in %s"
                              % (name, LEVEL_NAMES[level], LEVEL_NAMES[max(found)], symbol))

    # B. the copy the linker kept of a function shared between levels.
    kept = function_levels(library)
    shared = 0
    for symbol, found in sorted(kept.items()):
        if symbol not in lowest or len(found) != 1:
            continue  # not from these objects, or several local functions of that name
        shared += 1
        if found[0] > lowest[symbol]:
            errors.append("B: the library keeps a %s copy of %s, which %s code also calls"
                          % (LEVEL_NAMES[found[0]], symbol, LEVEL_NAMES[lowest[symbol]]))

    if shared < 100:
        errors.append("B: only %d functions of %s could be matched to their objects: is it stripped?"
                      % (shared, library))

    # C. the SIMD code is really there.
    in_library = collections.Counter(max(found) for found in kept.values())
    print("%s: %d functions, %d with AVX2 and %d with AVX-512 instructions; %d checked against their objects"
          % (os.path.basename(library), sum(in_library.values()), in_library[1], in_library[2], shared))
    if not in_library[1]:
        errors.append("C: no AVX2 code in " + library)
    if not in_library[2]:
        errors.append("C: no AVX-512 code in " + library)

    for error in errors[:50]:
        print("FAIL " + error)
    if errors:
        sys.exit("%d problem(s): this library is not safe on CPUs without AVX2 / AVX-512" % len(errors))
    print("OK: SIMD code is present and confined to the per-level objects")


if __name__ == "__main__":
    main()
