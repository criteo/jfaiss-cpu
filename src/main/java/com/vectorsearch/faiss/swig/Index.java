/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class Index {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Index(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Index obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_Index(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setD(int value) {
    swigfaissJNI.Index_d_set(swigCPtr, this, value);
  }

  public int getD() {
    return swigfaissJNI.Index_d_get(swigCPtr, this);
  }

  public void setNtotal(long value) {
    swigfaissJNI.Index_ntotal_set(swigCPtr, this, value);
  }

  public long getNtotal() {
    return swigfaissJNI.Index_ntotal_get(swigCPtr, this);
  }

  public void setVerbose(boolean value) {
    swigfaissJNI.Index_verbose_set(swigCPtr, this, value);
  }

  public boolean getVerbose() {
    return swigfaissJNI.Index_verbose_get(swigCPtr, this);
  }

  public void setIs_trained(boolean value) {
    swigfaissJNI.Index_is_trained_set(swigCPtr, this, value);
  }

  public boolean getIs_trained() {
    return swigfaissJNI.Index_is_trained_get(swigCPtr, this);
  }

  public void setMetric_type(MetricType value) {
    swigfaissJNI.Index_metric_type_set(swigCPtr, this, value.swigValue());
  }

  public MetricType getMetric_type() {
    return MetricType.swigToEnum(swigfaissJNI.Index_metric_type_get(swigCPtr, this));
  }

  public void setMetric_arg(float value) {
    swigfaissJNI.Index_metric_arg_set(swigCPtr, this, value);
  }

  public float getMetric_arg() {
    return swigfaissJNI.Index_metric_arg_get(swigCPtr, this);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.Index_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void add(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.Index_add(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long xids) {
    swigfaissJNI.Index_add_with_ids(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(xids));
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels) {
    swigfaissJNI.Index_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels));
  }

  public void range_search(long n, SWIGTYPE_p_float x, float radius, RangeSearchResult result) {
    swigfaissJNI.Index_range_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), radius, RangeSearchResult.getCPtr(result), result);
  }

  public void assign(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long labels, long k) {
    swigfaissJNI.Index_assign__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(labels), k);
  }

  public void assign(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long labels) {
    swigfaissJNI.Index_assign__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(labels));
  }

  public void reset() {
    swigfaissJNI.Index_reset(swigCPtr, this);
  }

  public long remove_ids(IDSelector sel) {
    return swigfaissJNI.Index_remove_ids(swigCPtr, this, IDSelector.getCPtr(sel), sel);
  }

  public void reconstruct(long key, SWIGTYPE_p_float recons) {
    swigfaissJNI.Index_reconstruct(swigCPtr, this, key, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void reconstruct_n(long i0, long ni, SWIGTYPE_p_float recons) {
    swigfaissJNI.Index_reconstruct_n(swigCPtr, this, i0, ni, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void search_and_reconstruct(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, SWIGTYPE_p_long labels, SWIGTYPE_p_float recons) {
    swigfaissJNI.Index_search_and_reconstruct(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long.getCPtr(labels), SWIGTYPE_p_float.getCPtr(recons));
  }

  public void compute_residual(SWIGTYPE_p_float x, SWIGTYPE_p_float residual, long key) {
    swigfaissJNI.Index_compute_residual(swigCPtr, this, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_float.getCPtr(residual), key);
  }

  public void compute_residual_n(long n, SWIGTYPE_p_float xs, SWIGTYPE_p_float residuals, SWIGTYPE_p_long keys) {
    swigfaissJNI.Index_compute_residual_n(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(xs), SWIGTYPE_p_float.getCPtr(residuals), SWIGTYPE_p_long.getCPtr(keys));
  }

  public DistanceComputer get_distance_computer() {
    long cPtr = swigfaissJNI.Index_get_distance_computer(swigCPtr, this);
    return (cPtr == 0) ? null : new DistanceComputer(cPtr, true);
  }

  public long sa_code_size() {
    return swigfaissJNI.Index_sa_code_size(swigCPtr, this);
  }

  public void sa_encode(long n, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char bytes) {
    swigfaissJNI.Index_sa_encode(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(bytes));
  }

  public void sa_decode(long n, SWIGTYPE_p_unsigned_char bytes, SWIGTYPE_p_float x) {
    swigfaissJNI.Index_sa_decode(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(bytes), SWIGTYPE_p_float.getCPtr(x));
  }

}
