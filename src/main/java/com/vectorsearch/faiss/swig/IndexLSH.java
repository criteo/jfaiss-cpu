/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class IndexLSH extends Index {
  private transient long swigCPtr;

  protected IndexLSH(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexLSH_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexLSH obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_IndexLSH(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setNbits(int value) {
    swigfaissJNI.IndexLSH_nbits_set(swigCPtr, this, value);
  }

  public int getNbits() {
    return swigfaissJNI.IndexLSH_nbits_get(swigCPtr, this);
  }

  public void setBytes_per_vec(int value) {
    swigfaissJNI.IndexLSH_bytes_per_vec_set(swigCPtr, this, value);
  }

  public int getBytes_per_vec() {
    return swigfaissJNI.IndexLSH_bytes_per_vec_get(swigCPtr, this);
  }

  public void setRotate_data(boolean value) {
    swigfaissJNI.IndexLSH_rotate_data_set(swigCPtr, this, value);
  }

  public boolean getRotate_data() {
    return swigfaissJNI.IndexLSH_rotate_data_get(swigCPtr, this);
  }

  public void setTrain_thresholds(boolean value) {
    swigfaissJNI.IndexLSH_train_thresholds_set(swigCPtr, this, value);
  }

  public boolean getTrain_thresholds() {
    return swigfaissJNI.IndexLSH_train_thresholds_get(swigCPtr, this);
  }

  public void setRrot(RandomRotationMatrix value) {
    swigfaissJNI.IndexLSH_rrot_set(swigCPtr, this, RandomRotationMatrix.getCPtr(value), value);
  }

  public RandomRotationMatrix getRrot() {
    long cPtr = swigfaissJNI.IndexLSH_rrot_get(swigCPtr, this);
    return (cPtr == 0) ? null : new RandomRotationMatrix(cPtr, false);
  }

  public void setThresholds(FloatVector value) {
    swigfaissJNI.IndexLSH_thresholds_set(swigCPtr, this, FloatVector.getCPtr(value), value);
  }

  public FloatVector getThresholds() {
    long cPtr = swigfaissJNI.IndexLSH_thresholds_get(swigCPtr, this);
    return (cPtr == 0) ? null : new FloatVector(cPtr, false);
  }

  public void setCodes(ByteVector value) {
    swigfaissJNI.IndexLSH_codes_set(swigCPtr, this, ByteVector.getCPtr(value), value);
  }

  public ByteVector getCodes() {
    long cPtr = swigfaissJNI.IndexLSH_codes_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ByteVector(cPtr, false);
  }

  public IndexLSH(long d, int nbits, boolean rotate_data, boolean train_thresholds) {
    this(swigfaissJNI.new_IndexLSH__SWIG_0(d, nbits, rotate_data, train_thresholds), true);
  }

  public IndexLSH(long d, int nbits, boolean rotate_data) {
    this(swigfaissJNI.new_IndexLSH__SWIG_1(d, nbits, rotate_data), true);
  }

  public IndexLSH(long d, int nbits) {
    this(swigfaissJNI.new_IndexLSH__SWIG_2(d, nbits), true);
  }

  public SWIGTYPE_p_float apply_preprocess(long n, SWIGTYPE_p_float x) {
    long cPtr = swigfaissJNI.IndexLSH_apply_preprocess(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexLSH_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void add(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexLSH_add(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels) {
    swigfaissJNI.IndexLSH_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels));
  }

  public void reset() {
    swigfaissJNI.IndexLSH_reset(swigCPtr, this);
  }

  public void transfer_thresholds(LinearTransform vt) {
    swigfaissJNI.IndexLSH_transfer_thresholds(swigCPtr, this, LinearTransform.getCPtr(vt), vt);
  }

  public IndexLSH() {
    this(swigfaissJNI.new_IndexLSH__SWIG_3(), true);
  }

  public long sa_code_size() {
    return swigfaissJNI.IndexLSH_sa_code_size(swigCPtr, this);
  }

  public void sa_encode(long n, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char bytes) {
    swigfaissJNI.IndexLSH_sa_encode(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(bytes));
  }

  public void sa_decode(long n, SWIGTYPE_p_unsigned_char bytes, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexLSH_sa_decode(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(bytes), SWIGTYPE_p_float.getCPtr(x));
  }

}
