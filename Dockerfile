FROM quay.io/centos/centos:stream8

RUN sed -i s/mirror.centos.org/vault.centos.org/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^#.*baseurl=http/baseurl=http/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^mirrorlist=http/#mirrorlist=http/g /etc/yum.repos.d/CentOS-*.repo
RUN dnf update -y

# Install necessary build tools. FAISS >= 1.15 needs C++20, which the system
# GCC 8.5 lacks: build with gcc-toolset-13 (GCC 13). Its libstdc++/libgomp
# linker scripts link against the system GCC 8.5 runtime (newer bits come from
# libstdc++_nonshared.a), so libswigfaiss.so stays within the GLIBCXX/GLIBC
# envelope of the prod images (GLIBCXX <= 3.4.25, GLIBC <= 2.28).
# CMake and SWIG come from pip (below), not from dnf: the CentOS Stream 8
# packages are too old for FAISS (CMake < 3.24) or differ from the SWIG used
# to generate the committed Java sources.
RUN dnf install -y git gcc-toolset-13-gcc-c++ make patch python3.11 python3.11-pip
RUN dnf install -y openblas libgfortran libquadmath libgomp

# Pinned CMake / SWIG, the same versions as the reference build
# (cmake 4.4.4, SWIG 4.5.1).
ARG CMAKE_VERSION=4.4.4
ARG SWIG_VERSION=4.5.1
RUN python3.11 -m venv /opt/tools \
 && /opt/tools/bin/pip install --no-cache-dir "cmake==${CMAKE_VERSION}" "swig==${SWIG_VERSION}"
ENV PATH=/opt/tools/bin:$PATH
ENV SWIG=/opt/tools/bin/swig

# NOTE: a small fix to make it work the linker option `-lopenblas`.
RUN ln -s /usr/lib64/libopenblas.so.0 /usr/lib64/libopenblas.so

# Install Java 11: JDK and development packages (for JNI).
RUN dnf install -y java-11-openjdk java-11-openjdk-devel
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk

ENV PATH=/opt/rh/gcc-toolset-13/root/usr/bin:$PATH
ENV CC=/opt/rh/gcc-toolset-13/root/usr/bin/gcc
ENV CXX=/opt/rh/gcc-toolset-13/root/usr/bin/g++
ENV CXXFLAGS="-fvisibility=hidden -fdata-sections -ffunction-sections"

COPY . /opt/JFaiss
WORKDIR /opt/JFaiss/faiss

# Faiss build optimization level:
#   dd       runtime SIMD dispatch (default): one library with the generic,
#            AVX2 and AVX-512 code paths; the best one the CPU supports is
#            picked when the library is loaded (see README, "SIMD level").
#   generic  no SIMD code paths.
# Do not use "avx2" / "avx512" here: they build a separate libfaiss_avx2.a /
# libfaiss_avx512.a that jni/Makefile does not link, and the result would crash
# on CPUs without these instructions anyway.
ARG FAISS_OPT_LEVEL=dd

# Build the FAISS static library (faiss/build/faiss/libfaiss.a) against the
# system OpenBLAS (0.3.15).
RUN cmake . \
    -B build \
    -DFAISS_ENABLE_GPU=OFF \
    -DFAISS_ENABLE_PYTHON=OFF \
    -DFAISS_ENABLE_MKL=OFF \
    -DFAISS_ENABLE_EXTRAS=OFF \
    -DBUILD_TESTING=OFF \
    -DFAISS_OPT_LEVEL=${FAISS_OPT_LEVEL} \
    -DCMAKE_BUILD_TYPE=Release \
    -DBLA_VENDOR=OpenBLAS \
    -DBLAS_LIBRARIES=/usr/lib64/libopenblas.so \
    -DLAPACK_LIBRARIES=/usr/lib64/libopenblas.so
RUN make -C build -j $(nproc) faiss

# Generate the SWIG Java sources and the JNI wrapper `libswigfaiss.so`, and
# bundle libswigfaiss.so, libgomp.so.1, libquadmath.so.0, libgfortran.so.5 and
# libopenblas.so.0 into /opt/JFaiss/cpu/src/main/{java,resources}
# (jni/Makefile defaults: OUT_ROOT=../cpu, BUNDLE_LIBDIR=/lib64).
WORKDIR /opt/JFaiss/jni
RUN make SWIG=${SWIG} CXX=${CXX} JAVA_HOME=${JAVA_HOME} FAISS_SRC=../faiss \
    FAISS_OPT_LEVEL=${FAISS_OPT_LEVEL} \
    BLAS_LIBDIR=/usr/lib64 BUNDLE_LIBDIR=/usr/lib64 LIBGOMP=/usr/lib64/libgomp.so.1

# NOTE: the duplicated constructors caused by the `int64_t` conversion are now
# removed with %ignore in swigfaiss.swig (formerly jni/patch.txt).

# Check what was built, while the object files are still at hand:
# - the SIMD code is in the library, and only on the code paths of its level
#   (a library that passes every test on an AVX-512 build machine can still
#   crash on a CPU without AVX-512);
# - the library needs no newer glibc / libstdc++ than CentOS 8 provides and
#   finds its bundled dependencies through DT_RPATH.
WORKDIR /opt/JFaiss
RUN if [ "${FAISS_OPT_LEVEL}" = dd ]; then \
      python3 ci/check-simd.py faiss/build cpu/src/main/resources/libswigfaiss.so jni/swigfaiss.o; \
    fi
RUN ci/check-abi.sh cpu/src/main/resources

WORKDIR /opt/JFaiss/jni
ENTRYPOINT [ "cp", "-r", "/opt/JFaiss/cpu/src/main", "/github/workspace/build" ]

# EOF
