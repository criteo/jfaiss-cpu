/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class Repeats {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Repeats(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Repeats obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_Repeats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setDim(int value) {
    swigfaissJNI.Repeats_dim_set(swigCPtr, this, value);
  }

  public int getDim() {
    return swigfaissJNI.Repeats_dim_get(swigCPtr, this);
  }

  public void setRepeats(RepeatVector value) {
    swigfaissJNI.Repeats_repeats_set(swigCPtr, this, RepeatVector.getCPtr(value), value);
  }

  public RepeatVector getRepeats() {
    long cPtr = swigfaissJNI.Repeats_repeats_get(swigCPtr, this);
    return (cPtr == 0) ? null : new RepeatVector(cPtr, false);
  }

  public Repeats(int dim, SWIGTYPE_p_float c) {
    this(swigfaissJNI.new_Repeats__SWIG_0(dim, SWIGTYPE_p_float.getCPtr(c)), true);
  }

  public Repeats(int dim) {
    this(swigfaissJNI.new_Repeats__SWIG_1(dim), true);
  }

  public Repeats() {
    this(swigfaissJNI.new_Repeats__SWIG_2(), true);
  }

  public long count() {
    return swigfaissJNI.Repeats_count(swigCPtr, this);
  }

  public long encode(SWIGTYPE_p_float c) {
    return swigfaissJNI.Repeats_encode(swigCPtr, this, SWIGTYPE_p_float.getCPtr(c));
  }

  public void decode(long code, SWIGTYPE_p_float c) {
    swigfaissJNI.Repeats_decode(swigCPtr, this, code, SWIGTYPE_p_float.getCPtr(c));
  }

}
