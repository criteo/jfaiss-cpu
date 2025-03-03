/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class MultiIndexQuantizer extends Index {
  private transient long swigCPtr;

  protected MultiIndexQuantizer(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.MultiIndexQuantizer_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MultiIndexQuantizer obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_MultiIndexQuantizer(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setPq(ProductQuantizer value) {
    swigfaissJNI.MultiIndexQuantizer_pq_set(swigCPtr, this, ProductQuantizer.getCPtr(value), value);
  }

  public ProductQuantizer getPq() {
    long cPtr = swigfaissJNI.MultiIndexQuantizer_pq_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ProductQuantizer(cPtr, false);
  }

  public MultiIndexQuantizer(int d, long M, long nbits) {
    this(swigfaissJNI.new_MultiIndexQuantizer__SWIG_0(d, M, nbits), true);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.MultiIndexQuantizer_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels) {
    swigfaissJNI.MultiIndexQuantizer_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels));
  }

  public void add(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.MultiIndexQuantizer_add(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void reset() {
    swigfaissJNI.MultiIndexQuantizer_reset(swigCPtr, this);
  }

  public MultiIndexQuantizer() {
    this(swigfaissJNI.new_MultiIndexQuantizer__SWIG_1(), true);
  }

  public void reconstruct(long key, SWIGTYPE_p_float recons) {
    swigfaissJNI.MultiIndexQuantizer_reconstruct(swigCPtr, this, key, SWIGTYPE_p_float.getCPtr(recons));
  }

}
