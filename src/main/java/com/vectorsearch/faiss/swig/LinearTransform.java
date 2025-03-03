/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class LinearTransform extends VectorTransform {
  private transient long swigCPtr;

  protected LinearTransform(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.LinearTransform_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LinearTransform obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_LinearTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setHave_bias(boolean value) {
    swigfaissJNI.LinearTransform_have_bias_set(swigCPtr, this, value);
  }

  public boolean getHave_bias() {
    return swigfaissJNI.LinearTransform_have_bias_get(swigCPtr, this);
  }

  public void setIs_orthonormal(boolean value) {
    swigfaissJNI.LinearTransform_is_orthonormal_set(swigCPtr, this, value);
  }

  public boolean getIs_orthonormal() {
    return swigfaissJNI.LinearTransform_is_orthonormal_get(swigCPtr, this);
  }

  public void setA(FloatVector value) {
    swigfaissJNI.LinearTransform_A_set(swigCPtr, this, FloatVector.getCPtr(value), value);
  }

  public FloatVector getA() {
    long cPtr = swigfaissJNI.LinearTransform_A_get(swigCPtr, this);
    return (cPtr == 0) ? null : new FloatVector(cPtr, false);
  }

  public void setB(FloatVector value) {
    swigfaissJNI.LinearTransform_b_set(swigCPtr, this, FloatVector.getCPtr(value), value);
  }

  public FloatVector getB() {
    long cPtr = swigfaissJNI.LinearTransform_b_get(swigCPtr, this);
    return (cPtr == 0) ? null : new FloatVector(cPtr, false);
  }

  public LinearTransform(int d_in, int d_out, boolean have_bias) {
    this(swigfaissJNI.new_LinearTransform__SWIG_0(d_in, d_out, have_bias), true);
  }

  public LinearTransform(int d_in, int d_out) {
    this(swigfaissJNI.new_LinearTransform__SWIG_1(d_in, d_out), true);
  }

  public LinearTransform(int d_in) {
    this(swigfaissJNI.new_LinearTransform__SWIG_2(d_in), true);
  }

  public LinearTransform() {
    this(swigfaissJNI.new_LinearTransform__SWIG_3(), true);
  }

  public void apply_noalloc(long n, SWIGTYPE_p_float x, SWIGTYPE_p_float xt) {
    swigfaissJNI.LinearTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_float.getCPtr(xt));
  }

  public void transform_transpose(long n, SWIGTYPE_p_float y, SWIGTYPE_p_float x) {
    swigfaissJNI.LinearTransform_transform_transpose(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(y), SWIGTYPE_p_float.getCPtr(x));
  }

  public void reverse_transform(long n, SWIGTYPE_p_float xt, SWIGTYPE_p_float x) {
    swigfaissJNI.LinearTransform_reverse_transform(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(xt), SWIGTYPE_p_float.getCPtr(x));
  }

  public void set_is_orthonormal() {
    swigfaissJNI.LinearTransform_set_is_orthonormal(swigCPtr, this);
  }

  public void setVerbose(boolean value) {
    swigfaissJNI.LinearTransform_verbose_set(swigCPtr, this, value);
  }

  public boolean getVerbose() {
    return swigfaissJNI.LinearTransform_verbose_get(swigCPtr, this);
  }

  public void print_if_verbose(String name, DoubleVector mat, int n, int d) {
    swigfaissJNI.LinearTransform_print_if_verbose(swigCPtr, this, name, DoubleVector.getCPtr(mat), mat, n, d);
  }

}
