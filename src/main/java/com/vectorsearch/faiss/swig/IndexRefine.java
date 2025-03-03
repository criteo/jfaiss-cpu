/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class IndexRefine extends Index {
  private transient long swigCPtr;

  protected IndexRefine(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexRefine_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexRefine obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_IndexRefine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setBase_index(Index value) {
    swigfaissJNI.IndexRefine_base_index_set(swigCPtr, this, Index.getCPtr(value), value);
  }

  public Index getBase_index() {
    long cPtr = swigfaissJNI.IndexRefine_base_index_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public void setRefine_index(Index value) {
    swigfaissJNI.IndexRefine_refine_index_set(swigCPtr, this, Index.getCPtr(value), value);
  }

  public Index getRefine_index() {
    long cPtr = swigfaissJNI.IndexRefine_refine_index_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public void setOwn_fields(boolean value) {
    swigfaissJNI.IndexRefine_own_fields_set(swigCPtr, this, value);
  }

  public boolean getOwn_fields() {
    return swigfaissJNI.IndexRefine_own_fields_get(swigCPtr, this);
  }

  public void setOwn_refine_index(boolean value) {
    swigfaissJNI.IndexRefine_own_refine_index_set(swigCPtr, this, value);
  }

  public boolean getOwn_refine_index() {
    return swigfaissJNI.IndexRefine_own_refine_index_get(swigCPtr, this);
  }

  public void setK_factor(float value) {
    swigfaissJNI.IndexRefine_k_factor_set(swigCPtr, this, value);
  }

  public float getK_factor() {
    return swigfaissJNI.IndexRefine_k_factor_get(swigCPtr, this);
  }

  public IndexRefine(Index base_index, Index refine_index) {
    this(swigfaissJNI.new_IndexRefine__SWIG_0(Index.getCPtr(base_index), base_index, Index.getCPtr(refine_index), refine_index), true);
  }

  public IndexRefine() {
    this(swigfaissJNI.new_IndexRefine__SWIG_1(), true);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexRefine_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void add(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexRefine_add(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void reset() {
    swigfaissJNI.IndexRefine_reset(swigCPtr, this);
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels) {
    swigfaissJNI.IndexRefine_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels));
  }

  public void reconstruct(long key, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexRefine_reconstruct(swigCPtr, this, key, SWIGTYPE_p_float.getCPtr(recons));
  }

}
