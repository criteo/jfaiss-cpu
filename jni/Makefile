# Copyright (c) Facebook, Inc. and its affiliates.
#
# This source code is licensed under the MIT license found in the
# LICENSE file in the root directory of this source tree.

-include ../faiss/makefile.inc

ifneq ($(strip $(NVCC)),)
	SWIGFLAGS += -DGPU_WRAPPER
endif

JAVACFLAGS = -I $(CONDA_ENV_ROOT)/include/ -I $(CONDA_ENV_ROOT)/include/linux/
SWIGJAVASRC = ../cpu/src/main/java/com/vectorsearch/faiss/swig

all: build

# Also silently generates swigfaiss.py.
swigfaiss.cpp: swigfaiss.swig ../faiss/libfaiss.a
	mkdir -p $(SWIGJAVASRC)
	$(SWIG) -java -c++ -Doverride= -I../ $(SWIGFLAGS) -package com.vectorsearch.faiss.swig -outdir $(SWIGJAVASRC) -o $@ $<

swigfaiss_avx2.cpp: swigfaiss.swig ../faiss/libfaiss.a
	mkdir -p $(SWIGJAVASRC)
	$(SWIG) -java -c++ -Doverride= -module swigfaiss_avx2 -I../ $(SWIGFLAGS) -package com.vectorsearch.faiss.swig -outdir $(SWIGJAVASRC) -o $@ $<

%.o: %.cpp
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(CPUFLAGS) $(JAVACFLAGS) \
               -I../ -c $< -o $@

# Extension is .so even on OSX.
_%.so: %.o ../faiss/libfaiss.a
	$(CXX) $(SHAREDFLAGS) $(LDFLAGS) -static-libstdc++ -static-libgcc -Wl,--export-dynamic,-rpath='$$ORIGIN' -o $@ $^ $(LIBS)
	chmod 755 $@

build: _swigfaiss.so
	$(eval _resources_path = ../cpu/src/main/resources)
	mkdir -p ${_resources_path}
	cp _swigfaiss.so ${_resources_path}
	cp $(CONDA_ENV_ROOT)/lib/libgomp.so.1 ${_resources_path}
	cp $(CONDA_ENV_ROOT)/lib/libmkl_core.so ${_resources_path}
	cp $(CONDA_ENV_ROOT)/lib/libmkl_intel_lp64.so ${_resources_path}
	cp $(CONDA_ENV_ROOT)/lib/libmkl_gnu_thread.so ${_resources_path}

clean:
	rm -f swigfaiss*.cpp swigfaiss*.o  _swigfaiss*.so
	rm -rf $(SWIGJAVASRC)


.PHONY: all build install clean