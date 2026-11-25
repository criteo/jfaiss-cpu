#!/usr/bin/env bash
# List the classes, methods and fields of this project that compiled code uses.
#
# Reads the constant pools of the class files in a jar or a directory and
# prints every reference into com/vectorsearch/faiss, one per line, in the
# form the JVM resolves it:
#
#   com/vectorsearch/faiss/swig/floatArray
#   com/vectorsearch/faiss/swig/floatArray.<init>:(I)V
#   com/vectorsearch/faiss/swig/MetricType.METRIC_L2:Lcom/vectorsearch/faiss/swig/MetricType;
#
# Run it on a consumer of jfaiss-cpu to produce or refresh compat/required-api.txt,
# which ci/ApiCheck.java then checks against every new build.
#
# usage: list-api-refs.sh JAR_OR_CLASSES_DIR
set -euo pipefail

input=${1:?usage: list-api-refs.sh JAR_OR_CLASSES_DIR}
if [ -d "$input" ]; then
    classes=$(cd "$input" && find . -name '*.class' | sed 's|^\./||')
else
    classes=$(unzip -Z1 "$input" | grep '\.class$' || true)
fi
[ -n "$classes" ] || { echo "no class file in $input" >&2; exit 1; }

# javap prints e.g.
#   #7 = Methodref  #8.#9  // com/vectorsearch/faiss/swig/floatArray."<init>":(I)V
#   #8 = Class      #10    // com/vectorsearch/faiss/swig/floatArray
# References to the classes of the input itself are left out.
echo "$classes" | sed 's/\.class$//' | xargs javap -v -cp "$input" 2>/dev/null \
    | awk -v own="$(echo "$classes" | sed 's/\.class$//' | tr '\n' ' ')" '
      BEGIN { n = split(own, names, " "); for (i = 1; i <= n; i++) mine[names[i]] = 1 }
      $2 == "=" && $3 ~ /^(Class|Methodref|InterfaceMethodref|Fieldref)$/ {
        ref = $0
        sub(/^.*\/\/ +/, "", ref)
        gsub(/"/, "", ref)
        if ($3 == "Class" && ref ~ /^\[/) {   # array of a class: [Lpkg/Class;
            sub(/^\[+L/, "", ref)
            sub(/;$/, "", ref)
        }
        owner = ref
        sub(/[.:].*$/, "", owner)
        if (ref ~ /^com\/vectorsearch\/faiss\// && !(owner in mine)) print ref
      }' \
    | LC_ALL=C sort -u
