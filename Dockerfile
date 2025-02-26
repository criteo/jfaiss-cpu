FROM quay.io/centos/centos:stream8

ARG FAISS_OPT_LEVEL=generic
#ARG MINIFORGE_VERSION=24.11.3-0

#RUN curl -L -o Miniforge3.sh "https://github.com/conda-forge/miniforge/releases/download/${MINIFORGE_VERSION}/Miniforge3-$(uname)-$(uname -m).sh"
#RUN bash Miniforge3.sh -b
#RUN rm -f Miniforge3.sh
#ENV PATH="$PATH:/root/miniforge3/bin"

#RUN conda install swig==4.3.0

#ENV SWIG="/root/miniforge3/bin/swig"

RUN sed -i s/mirror.centos.org/vault.centos.org/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^#.*baseurl=http/baseurl=http/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^mirrorlist=http/#mirrorlist=http/g /etc/yum.repos.d/CentOS-*.repo

RUN dnf install --enablerepo powertools -y lapack lapack-devel dnf-plugins-core

# Install necessary build tools
RUN dnf install -y git gcc-c++ cmake make swig
RUN dnf install -y openblas
ENV SWIG=swig

RUN ln -s /usr/lib64/libopenblas.so.0 /usr/lib64/libopenblas.so

# RUN dnf config-manager --add-repo https://yum.repos.intel.com/mkl/setup/intel-mkl.repo
# RUN rpm --import https://yum.repos.intel.com/intel-gpg-keys/GPG-PUB-KEY-INTEL-SW-PRODUCTS-2019.PUB
# RUN dnf install -y intel-mkl-2019.3-062

RUN dnf install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel maven
RUN dnf install -y python3-numpy

ENV FAISS_LDFLAGS="-l:libfaiss.a -l:libopenblas.a -lgfortran"
ENV CXXFLAGS="-fvisibility=hidden -fdata-sections -ffunction-sections"

#ENV LD_LIBRARY_PATH=/opt/intel/mkl/lib/intel64:$LD_LIBRARY_PATH
#ENV LIBRARY_PATH=/opt/intel/mkl/lib/intel64:$LIBRARY_PATH
#ENV LD_PRELOAD=${LD_PRELOAD}:/usr/lib64/libgomp.so.1
#ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_def.so
#ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_avx2.so
#ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_core.so
#ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_intel_lp64.so
#ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_gnu_thread.so

COPY . /opt/JFaiss
WORKDIR /opt/JFaiss/faiss

RUN cmake . \
    -B build \
    -DFAISS_ENABLE_GPU=OFF \
    -DFAISS_ENABLE_PYTHON=OFF \
    -DBUILD_TESTING=ON \
    -DFAISS_OPT_LEVEL=${FAISS_OPT_LEVEL} \
    -DCMAKE_BUILD_TYPE=Release
RUN make -C build -j $(nproc)
RUN make -C build install

# Create source files
WORKDIR /opt/JFaiss/jni
RUN make
RUN cp patch.txt ../cpu

RUN dnf install -y patch
WORKDIR /opt/JFaiss/cpu
RUN patch -p1 < patch.txt

WORKDIR /opt/JFaiss/jni
ENTRYPOINT [ "cp", "-r", "/opt/JFaiss/cpu/src/main", "/github/workspace/build" ]

# EOF