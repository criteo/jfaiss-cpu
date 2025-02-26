/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class IndexHNSW2Level extends IndexHNSW {
  private transient long swigCPtr;

  protected IndexHNSW2Level(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexHNSW2Level_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexHNSW2Level obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_IndexHNSW2Level(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public IndexHNSW2Level() {
    this(swigfaissJNI.new_IndexHNSW2Level__SWIG_0(), true);
  }

  public IndexHNSW2Level(Index quantizer, long nlist, int m_pq, int M) {
    this(swigfaissJNI.new_IndexHNSW2Level__SWIG_1(Index.getCPtr(quantizer), quantizer, nlist, m_pq, M), true);
  }

  public void flip_to_ivf() {
    swigfaissJNI.IndexHNSW2Level_flip_to_ivf(swigCPtr, this);
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels) {
    swigfaissJNI.IndexHNSW2Level_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels));
  }

}
