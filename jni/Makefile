# Copyright (c) Facebook, Inc. and its affiliates.
#
# This source code is licensed under the MIT license found in the
# LICENSE file in the root directory of this source tree.

# Builds the JNI wrapper `libswigfaiss.so` (and the SWIG-generated Java
# sources) against a static FAISS library (`libfaiss.a`, built with CMake).
#
# Every location is overridable from the command line or the environment, e.g.
#   make SWIG=/path/to/swig JAVA_HOME=/path/to/jdk BLAS_LIBDIR=/path/to/lib64 \
#        OUT_ROOT=.. LIBGOMP=/lib64/libgomp.so.1 BUNDLE_LIBDIR=/path/to/lib64

-include ../faiss/makefile.inc

SWIG ?= swig
CXX ?= g++

# FAISS sources (headers) and the static library built from them.
FAISS_SRC ?= ../faiss
FAISS_LIB ?= $(FAISS_SRC)/build/faiss/libfaiss.a

# JNI headers.
JAVA_HOME ?= /usr/lib/jvm/java
JAVACFLAGS ?= -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux

# BLAS/LAPACK used to link libswigfaiss.so (OpenBLAS by default).
BLAS_LIBDIR ?= /usr/lib64
BLAS_LIBS ?= -lopenblas

# FAISS >= 1.15 headers need C++20 (concepts, <span>): use a compiler with full
# C++20 support (e.g. gcc-toolset-13 on EL8). CXXFLAGS from the environment
# (e.g. the Dockerfile's "-fvisibility=hidden -fdata-sections
# -ffunction-sections") are appended.
JNI_CXXFLAGS = -std=c++20 -O2 -fPIC $(CXXFLAGS)

# How $(FAISS_LIB) was built: the value given to CMake's FAISS_OPT_LEVEL.
#   dd       runtime SIMD dispatch: libfaiss.a holds the generic, AVX2 and
#            AVX-512 code paths and picks one when the library is loaded.
#   generic  no SIMD code paths.
# The wrapper is compiled with the same FAISS_ENABLE_DD define as upstream's
# own SWIG module, and with no -mavx* flag: it must run on every CPU.
# "avx2" and "avx512" are refused on purpose: CMake then puts the SIMD code in
# libfaiss_avx2.a / libfaiss_avx512.a and leaves libfaiss.a generic, so linking
# libfaiss.a would silently produce a library without any SIMD code.
FAISS_OPT_LEVEL ?= dd
ifeq ($(FAISS_OPT_LEVEL),dd)
SWIGFLAGS += -DFAISS_ENABLE_DD
JNI_CXXFLAGS += -DFAISS_ENABLE_DD
else ifneq ($(FAISS_OPT_LEVEL),generic)
$(error FAISS_OPT_LEVEL must be "dd" or "generic", not "$(FAISS_OPT_LEVEL)")
endif

ifneq ($(strip $(NVCC)),)
	SWIGFLAGS += -DGPU_WRAPPER
endif

# Where the generated Java sources and the bundled native libraries go:
# $(OUT_ROOT)/src/main/{java,resources}. The default (../cpu) is what the
# Dockerfile / CI workflow expect; use OUT_ROOT=.. to build in place.
OUT_ROOT ?= ../cpu
SWIGJAVASRC = $(OUT_ROOT)/src/main/java/com/vectorsearch/faiss/swig
RESOURCES = $(OUT_ROOT)/src/main/resources

# Native libraries bundled at the jar root (see JFaissConstants).
BUNDLE_LIBDIR ?= /lib64
LIBGOMP ?= /lib64/libgomp.so.1
LIBQUADMATH ?= $(BUNDLE_LIBDIR)/libquadmath.so.0
LIBGFORTRAN ?= $(BUNDLE_LIBDIR)/libgfortran.so.5
LIBOPENBLAS ?= $(BUNDLE_LIBDIR)/libopenblas.so.0

all: build

# NOTE: do not pass -DSWIGWORDSIZE64 to SWIG: swigfaiss.swig defines it.
swigfaiss.cpp: swigfaiss.swig $(FAISS_LIB)
	rm -rf $(SWIGJAVASRC)
	mkdir -p $(SWIGJAVASRC)
	$(SWIG) -java -c++ -Doverride= -I$(FAISS_SRC) $(SWIGFLAGS) -package com.vectorsearch.faiss.swig -outdir $(SWIGJAVASRC) -o $@ $<

%.o: %.cpp
	$(CXX) $(CPPFLAGS) $(JNI_CXXFLAGS) -DSWIGWORDSIZE64 $(CPUFLAGS) $(JAVACFLAGS) \
               -I$(FAISS_SRC) -c $< -o $@

# Extension is .so even on OSX.
lib%.so: %.o $(FAISS_LIB)
	$(CXX) $(SHAREDFLAGS) -shared $(LDFLAGS) -fopenmp -Wl,--export-dynamic,--disable-new-dtags,-rpath='$$ORIGIN' -o $@ $^ $(LIBS) -L$(BLAS_LIBDIR) $(BLAS_LIBS)
	chmod 755 $@

build: libswigfaiss.so
	mkdir -p $(RESOURCES)
	cp libswigfaiss.so $(RESOURCES)
	cp $(LIBGOMP) $(RESOURCES)/libgomp.so.1
	cp $(LIBQUADMATH) $(RESOURCES)/libquadmath.so.0
	cp $(LIBGFORTRAN) $(RESOURCES)/libgfortran.so.5
	cp $(LIBOPENBLAS) $(RESOURCES)/libopenblas.so.0

clean:
	rm -f swigfaiss*.cpp swigfaiss*.o  libswigfaiss*.so
	rm -rf $(SWIGJAVASRC)


.PHONY: all build install clean
