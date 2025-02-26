/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class RandomRotationMatrix extends LinearTransform {
  private transient long swigCPtr;

  protected RandomRotationMatrix(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.RandomRotationMatrix_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(RandomRotationMatrix obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_RandomRotationMatrix(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public RandomRotationMatrix(int d_in, int d_out) {
    this(swigfaissJNI.new_RandomRotationMatrix__SWIG_0(d_in, d_out), true);
  }

  public void init(int seed) {
    swigfaissJNI.RandomRotationMatrix_init(swigCPtr, this, seed);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.RandomRotationMatrix_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public RandomRotationMatrix() {
    this(swigfaissJNI.new_RandomRotationMatrix__SWIG_1(), true);
  }

}
