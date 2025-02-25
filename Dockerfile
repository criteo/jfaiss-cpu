FROM centos:7

RUN sed -i s/mirror.centos.org/vault.centos.org/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^#.*baseurl=http/baseurl=http/g /etc/yum.repos.d/CentOS-*.repo
RUN sed -i s/^mirrorlist=http/#mirrorlist=http/g /etc/yum.repos.d/CentOS-*.repo

RUN yum install -y lapack lapack-devel

# Install necessary build tools
RUN yum install -y gcc-c++ make swig3
RUN yum install -y blas-devel
RUN yum-config-manager --add-repo https://yum.repos.intel.com/mkl/setup/intel-mkl.repo
RUN rpm --import https://yum.repos.intel.com/intel-gpg-keys/GPG-PUB-KEY-INTEL-SW-PRODUCTS-2019.PUB
RUN yum install -y intel-mkl-2019.3-062
RUN yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel maven
RUN yum install -y numpy

ENV MKL_ROOT=/opt/intel/mkl/lib/intel64

ENV LD_LIBRARY_PATH=$MKL_ROOT:$LD_LIBRARY_PATH
ENV LIBRARY_PATH=$MKL_ROOT:$LIBRARY_PATH

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