FROM quay.io/centos/centos:stream8

RUN sed -i s/mirror.centos.org/vault.centos.org/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^#.*baseurl=http/baseurl=http/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^mirrorlist=http/#mirrorlist=http/g /etc/yum.repos.d/CentOS-*.repo
RUN dnf update -y

# Install necessary build tools
RUN dnf install -y git gcc-c++ cmake make swig patch
RUN dnf install -y openblas
ENV SWIG=swig

# NOTE: a small fix to make it work the linker option `-lopenblas`.
RUN ln -s /usr/lib64/libopenblas.so.0 /usr/lib64/libopenblas.so

# Install Java 1.8: JDK and development packages (for JNI).
RUN dnf install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel

ENV FAISS_LDFLAGS="-l:libfaiss.a -l:libopenblas.a -lgfortran"
ENV CXXFLAGS="-fvisibility=hidden -fdata-sections -ffunction-sections"

COPY . /opt/JFaiss
WORKDIR /opt/JFaiss/faiss

# Faiss build optimization level: generic, avx2, avx512
ARG FAISS_OPT_LEVEL=generic

# Build Faiss native library.
RUN cmake . \
    -B build \
    -DFAISS_ENABLE_GPU=OFF \
    -DFAISS_ENABLE_PYTHON=OFF \
    -DBUILD_TESTING=OFF \
    -DFAISS_OPT_LEVEL=${FAISS_OPT_LEVEL} \
    -DCMAKE_BUILD_TYPE=Release
RUN make -C build -j $(nproc)
RUN make -C build install

# Generate Java wrapper `libswigfaiss.so`.
WORKDIR /opt/JFaiss/jni
RUN make

# NOTE: SWIG-generated Java code has some issues due to conversion of `int64_t`
# resulting in duplicated definitions of constructors of some classes.
# Apply a patch to generated Java code.
RUN cp patch.txt ../cpu
WORKDIR /opt/JFaiss/cpu
RUN patch -p1 < patch.txt

WORKDIR /opt/JFaiss/jni
ENTRYPOINT [ "cp", "-r", "/opt/JFaiss/cpu/src/main", "/github/workspace/build" ]

# EOF