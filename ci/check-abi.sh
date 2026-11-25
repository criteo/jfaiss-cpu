#!/usr/bin/env bash
# Check that the native libraries bundled in the jar still load on the oldest
# systems the jar supports, and that the class files still load on Java 8.
#
# The native library is compiled with a recent GCC (Faiss needs C++20) on an
# old distribution, CentOS 8, precisely so that it only needs an old glibc and
# an old libstdc++. Nothing else enforces that: a toolchain or base image
# change can raise the requirements without any test failing on the build
# machine. This script reads them from the binary.
#
#   - libswigfaiss.so needs at most GLIBC_2.28, GLIBCXX_3.4.25 and
#     CXXABI_1.3.11 (CentOS 8 / GCC 8); override with MAX_GLIBC, MAX_GLIBCXX,
#     MAX_CXXABI;
#   - it finds its bundled dependencies next to itself: DT_RPATH is $ORIGIN.
#     DT_RUNPATH, the default of recent linkers, is not enough: it is not used
#     for the dependencies of the dependencies (libopenblas needs libgfortran);
#   - every library it or a bundled library needs is either bundled or part of
#     glibc / the GCC runtime;
#   - class files are at most version 52 (Java 8).
#
# usage: check-abi.sh RESOURCES_DIR [CLASSES_DIR]
set -euo pipefail

resources=${1:?usage: check-abi.sh RESOURCES_DIR [CLASSES_DIR]}
classes=${2:-}
MAX_GLIBC=${MAX_GLIBC:-2.28}
MAX_GLIBCXX=${MAX_GLIBCXX:-3.4.25}
MAX_CXXABI=${MAX_CXXABI:-1.3.11}
# Expected on the target system: glibc, the GCC runtime, and zlib (needed by the
# CentOS 8 libgfortran; the 1.7.0 jar already relied on it).
SYSTEM_LIBS="libc.so.6 libm.so.6 libpthread.so.0 libdl.so.2 librt.so.1 ld-linux-x86-64.so.2 libstdc++.so.6 libgcc_s.so.1 libz.so.1"

failures=0
fail() { echo "FAIL: $*"; failures=$((failures + 1)); }

library=$resources/libswigfaiss.so
[ -f "$library" ] || { echo "FAIL: $library not found"; exit 1; }

# Highest version of a versioned symbol set ($1, e.g. GLIBC) the library imports.
highest() {
    objdump -T "$library" | grep -o "\b$1_[0-9][0-9.]*" | sed "s/^$1_//" | sort -uV | tail -1
}
check_max() {
    local found
    found=$(highest "$1")
    if [ -z "$found" ]; then
        echo "ok    $1: not used"
    elif [ "$(printf '%s\n%s\n' "$found" "$2" | sort -V | tail -1)" = "$2" ]; then
        echo "ok    $1_$found (limit $1_$2)"
    else
        fail "libswigfaiss.so needs $1_$found, above the limit $1_$2"
    fi
}
check_max GLIBC "$MAX_GLIBC"
check_max GLIBCXX "$MAX_GLIBCXX"
check_max CXXABI "$MAX_CXXABI"

dynamic=$(readelf -d "$library")
if echo "$dynamic" | grep -q '(RUNPATH)'; then
    fail "libswigfaiss.so has DT_RUNPATH: link with -Wl,--disable-new-dtags to get DT_RPATH"
elif echo "$dynamic" | grep '(RPATH)' | grep -qF '[$ORIGIN]'; then
    echo 'ok    DT_RPATH is $ORIGIN'
else
    fail 'libswigfaiss.so has no DT_RPATH $ORIGIN'
fi

before=$failures
bundled=
for file in "$resources"/*.so*; do
    bundled="$bundled $(basename "$file")"
    for needed in $(readelf -d "$file" | sed -n 's/.*(NEEDED).*\[\(.*\)\]/\1/p'); do
        if [ -f "$resources/$needed" ] || echo " $SYSTEM_LIBS " | grep -qF " $needed "; then
            continue
        fi
        fail "$(basename "$file") needs $needed, which is neither bundled nor a system library"
    done
done
[ "$failures" -gt "$before" ] || echo "ok    dependencies of$bundled"

if [ -n "$classes" ]; then
    count=0
    while IFS= read -r -d '' class; do
        count=$((count + 1))
        # Bytes 6-7 of a class file: major version, big endian.
        set -- $(od -An -j6 -N2 -tu1 "$class")
        major=$(($1 * 256 + $2))
        [ "$major" -le 52 ] || fail "$class has class file version $major (Java 8 reads up to 52)"
    done < <(find "$classes" -name '*.class' -print0)
    [ "$count" -gt 0 ] || fail "no class file in $classes"
    echo "ok    $count class files checked for Java 8"
fi

if [ "$failures" -gt 0 ]; then
    echo "$failures problem(s)"
    exit 1
fi
echo "OK"
