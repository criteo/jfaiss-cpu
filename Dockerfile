FROM quay.io/centos/centos:stream8

ARG MINIFORGE_VERSION=24.11.3-0
ARG GXX_VERSION=8.5.0
ARG SWIG_VERSION=3.0.12
ARG MAKE_VERSION=4.3
ARG BLAS_VERSION="3.8.0=21_mkl"
ARG OPENJDK_VERSION=8.0

# Install Miniforge (Conda) package manager.
RUN curl -L -o Miniforge3.sh "https://github.com/conda-forge/miniforge/releases/download/${MINIFORGE_VERSION}/Miniforge3-$(uname)-$(uname -m).sh"
RUN bash Miniforge3.sh -b
RUN rm -f Miniforge3.sh
ENV PATH="$PATH:/root/miniforge3/bin"

RUN conda create -n criteo

# Install required build tools.
RUN conda install -y -n criteo gxx=${GXX_VERSION}
RUN conda install -y -n criteo swig=${SWIG_VERSION}
RUN conda install -y -n criteo make=${MAKE_VERSION}
RUN conda install -y -n criteo blas-devel=${BLAS_VERSION}
RUN conda install -y -n criteo openjdk=${OPENJDK_VERSION}

# Set up environment variables for Conda packages.
ENV CONDA_ENV_ROOT="/root/miniforge3/envs/criteo"
ENV PATH="$PATH:$CONDA_ENV_ROOT/bin"
ENV LD_LIBRARY_PATH=$CONDA_ENV_ROOT/lib:$LD_LIBRARY_PATH
ENV LIBRARY_PATH=$CONDA_ENV_ROOT/lib:$LIBRARY_PATH

COPY . /opt/JFaiss
WORKDIR /opt/JFaiss/faiss

# Compile Faiss.
ENV CXXFLAGS=${CXXFLAGS}" -mavx2 -mf16c -I/opt/JFaiss"
RUN ./configure --prefix=/usr --without-cuda
RUN make -j $(nproc)
RUN make install

# Build SWIG wrapper for Java.
WORKDIR /opt/JFaiss/jni
RUN make 
ENTRYPOINT [ "cp", "-r", "/opt/JFaiss/cpu/src/main", "/github/workspace/build" ]

# EOF