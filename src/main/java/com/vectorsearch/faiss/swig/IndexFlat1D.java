/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class IndexFlat1D extends IndexFlatL2 {
  private transient long swigCPtr;

  protected IndexFlat1D(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexFlat1D_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexFlat1D obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_IndexFlat1D(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setContinuous_update(boolean value) {
    swigfaissJNI.IndexFlat1D_continuous_update_set(swigCPtr, this, value);
  }

  public boolean getContinuous_update() {
    return swigfaissJNI.IndexFlat1D_continuous_update_get(swigCPtr, this);
  }

  public void setPerm(LongVector value) {
    swigfaissJNI.IndexFlat1D_perm_set(swigCPtr, this, LongVector.getCPtr(value), value);
  }

  public LongVector getPerm() {
    long cPtr = swigfaissJNI.IndexFlat1D_perm_get(swigCPtr, this);
    return (cPtr == 0) ? null : new LongVector(cPtr, false);
  }

  public IndexFlat1D(boolean continuous_update) {
    this(swigfaissJNI.new_IndexFlat1D__SWIG_0(continuous_update), true);
  }

  public IndexFlat1D() {
    this(swigfaissJNI.new_IndexFlat1D__SWIG_1(), true);
  }

  public void update_permutation() {
    swigfaissJNI.IndexFlat1D_update_permutation(swigCPtr, this);
  }

  public void add(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexFlat1D_add(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void reset() {
    swigfaissJNI.IndexFlat1D_reset(swigCPtr, this);
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels) {
    swigfaissJNI.IndexFlat1D_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels));
  }

}
