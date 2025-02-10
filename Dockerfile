FROM quay.io/centos/centos:stream8

RUN sed -i s/mirror.centos.org/vault.centos.org/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^#.*baseurl=http/baseurl=http/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^mirrorlist=http/#mirrorlist=http/g /etc/yum.repos.d/CentOS-*.repo

RUN dnf install --enablerepo powertools -y lapack lapack-devel dnf-plugins-core

# Install necessary build tools
RUN dnf install -y gcc-c++ make swig
RUN dnf install -y blas-devel
RUN dnf config-manager --add-repo https://yum.repos.intel.com/mkl/setup/intel-mkl.repo
RUN rpm --import https://yum.repos.intel.com/intel-gpg-keys/GPG-PUB-KEY-INTEL-SW-PRODUCTS-2019.PUB
RUN dnf install -y intel-mkl-2019.3-062
RUN dnf install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel maven
RUN dnf install -y python3-numpy

ENV LD_LIBRARY_PATH=/opt/intel/mkl/lib/intel64:$LD_LIBRARY_PATH
ENV LIBRARY_PATH=/opt/intel/mkl/lib/intel64:$LIBRARY_PATH
ENV LD_PRELOAD=${LD_PRELOAD}:/usr/lib64/libgomp.so.1
ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_def.so
ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_avx2.so
ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_core.so
ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_intel_lp64.so
ENV LD_PRELOAD=${LD_PRELOAD}:/opt/intel/mkl/lib/intel64/libmkl_gnu_thread.so

COPY . /opt/JFaiss
WORKDIR /opt/JFaiss/faiss

ENV CXXFLAGS=${CXXFLAGS}" -mavx2 -mf16c -I/opt/JFaiss"
# Install faiss
RUN ./configure --prefix=/usr --without-cuda
RUN make -j $(nproc)
RUN make install

# Create source files
WORKDIR /opt/JFaiss/jni
RUN make
ENTRYPOINT [ "cp", "-r", "/opt/JFaiss/cpu/src/main", "/github/workspace/build" ]
#&& tail -f /dev/null
