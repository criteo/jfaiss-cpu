# Copyright (c) Facebook, Inc. and its affiliates.
#
# This source code is licensed under the MIT license found in the
# LICENSE file in the root directory of this source tree.

-include ../faiss/makefile.inc

ifneq ($(strip $(NVCC)),)
	SWIGFLAGS += -DGPU_WRAPPER
endif

JAVACFLAGS = -I /usr/lib/jvm/java/include/ -I /usr/lib/jvm/java/include/linux/
SWIGJAVASRC = ../cpu/src/main/java/com/vectorsearch/faiss/swig

all: build

# Also silently generates swigfaiss.py.
swigfaiss.cpp: swigfaiss.swig ../faiss/build/faiss/libfaiss.a
	mkdir -p $(SWIGJAVASRC)
	$(SWIG) -java -c++ -Doverride= -I../faiss $(SWIGFLAGS) -package com.vectorsearch.faiss.swig -outdir $(SWIGJAVASRC) -o $@ $<

# swigfaiss_avx2.cpp: swigfaiss.swig ../faiss/build/faiss/libfaiss.a
#	mkdir -p $(SWIGJAVASRC)
#	$(SWIG) -java -c++ -Doverride= -module swigfaiss_avx2 -I../ $(SWIGFLAGS) -package com.vectorsearch.faiss.swig -outdir $(SWIGJAVASRC) -o $@ $<

%.o: %.cpp
	$(CXX) $(CPPFLAGS) -fPIC $(CXXFLAGS) -DSWIGWORDSIZE64 $(CPUFLAGS) $(JAVACFLAGS) \
               -I../ -c $< -o $@

# Extension is .so even on OSX.
lib%.so: %.o ../faiss/build/faiss/libfaiss.a
	$(CXX) $(SHAREDFLAGS) -shared $(LDFLAGS) -fopenmp -Wl,--export-dynamic,-rpath='$$ORIGIN' -o $@ $^ $(LIBS) -lopenblas
	chmod 755 $@

build: libswigfaiss.so
	$(eval _resources_path = ../cpu/src/main/resources)
	mkdir -p ${_resources_path}
	cp libswigfaiss.so ${_resources_path}
	cp /lib64/libgomp.so.1 ${_resources_path}
	cp /lib64/libquadmath.so.0 ${_resources_path}
	cp /lib64/libgfortran.so.5 ${_resources_path}
	cp /lib64/libopenblas.so.0 ${_resources_path}


clean:
	rm -f swigfaiss*.cpp swigfaiss*.o  _swigfaiss*.so
	rm -rf $(SWIGJAVASRC)


.PHONY: all build install clean