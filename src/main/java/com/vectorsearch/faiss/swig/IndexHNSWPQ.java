/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class IndexHNSWPQ extends IndexHNSW {
  private transient long swigCPtr;

  protected IndexHNSWPQ(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexHNSWPQ_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexHNSWPQ obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_IndexHNSWPQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public IndexHNSWPQ() {
    this(swigfaissJNI.new_IndexHNSWPQ__SWIG_0(), true);
  }

  public IndexHNSWPQ(int d, int pq_m, int M) {
    this(swigfaissJNI.new_IndexHNSWPQ__SWIG_1(d, pq_m, M), true);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexHNSWPQ_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

}
