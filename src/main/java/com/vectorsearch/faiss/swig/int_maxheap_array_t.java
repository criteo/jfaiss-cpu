/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class int_maxheap_array_t {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected int_maxheap_array_t(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(int_maxheap_array_t obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_int_maxheap_array_t(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setNh(long value) {
    swigfaissJNI.int_maxheap_array_t_nh_set(swigCPtr, this, value);
  }

  public long getNh() {
    return swigfaissJNI.int_maxheap_array_t_nh_get(swigCPtr, this);
  }

  public void setK(long value) {
    swigfaissJNI.int_maxheap_array_t_k_set(swigCPtr, this, value);
  }

  public long getK() {
    return swigfaissJNI.int_maxheap_array_t_k_get(swigCPtr, this);
  }

  public void setIds(SWIGTYPE_p_long value) {
    swigfaissJNI.int_maxheap_array_t_ids_set(swigCPtr, this, SWIGTYPE_p_long.getCPtr(value));
  }

  public SWIGTYPE_p_long getIds() {
    long cPtr = swigfaissJNI.int_maxheap_array_t_ids_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public void setVal(SWIGTYPE_p_int value) {
    swigfaissJNI.int_maxheap_array_t_val_set(swigCPtr, this, SWIGTYPE_p_int.getCPtr(value));
  }

  public SWIGTYPE_p_int getVal() {
    long cPtr = swigfaissJNI.int_maxheap_array_t_val_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public SWIGTYPE_p_int get_val(long key) {
    long cPtr = swigfaissJNI.int_maxheap_array_t_get_val(swigCPtr, this, key);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public SWIGTYPE_p_long get_ids(long key) {
    long cPtr = swigfaissJNI.int_maxheap_array_t_get_ids(swigCPtr, this, key);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public void heapify() {
    swigfaissJNI.int_maxheap_array_t_heapify(swigCPtr, this);
  }

  public void addn(long nj, SWIGTYPE_p_int vin, long j0, long i0, long ni) {
    swigfaissJNI.int_maxheap_array_t_addn__SWIG_0(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), j0, i0, ni);
  }

  public void addn(long nj, SWIGTYPE_p_int vin, long j0, long i0) {
    swigfaissJNI.int_maxheap_array_t_addn__SWIG_1(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), j0, i0);
  }

  public void addn(long nj, SWIGTYPE_p_int vin, long j0) {
    swigfaissJNI.int_maxheap_array_t_addn__SWIG_2(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), j0);
  }

  public void addn(long nj, SWIGTYPE_p_int vin) {
    swigfaissJNI.int_maxheap_array_t_addn__SWIG_3(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin));
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_int vin, SWIGTYPE_p_long id_in, long id_stride, long i0, long ni) {
    swigfaissJNI.int_maxheap_array_t_addn_with_ids__SWIG_0(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), SWIGTYPE_p_long.getCPtr(id_in), id_stride, i0, ni);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_int vin, SWIGTYPE_p_long id_in, long id_stride, long i0) {
    swigfaissJNI.int_maxheap_array_t_addn_with_ids__SWIG_1(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), SWIGTYPE_p_long.getCPtr(id_in), id_stride, i0);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_int vin, SWIGTYPE_p_long id_in, long id_stride) {
    swigfaissJNI.int_maxheap_array_t_addn_with_ids__SWIG_2(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), SWIGTYPE_p_long.getCPtr(id_in), id_stride);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_int vin, SWIGTYPE_p_long id_in) {
    swigfaissJNI.int_maxheap_array_t_addn_with_ids__SWIG_3(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin), SWIGTYPE_p_long.getCPtr(id_in));
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_int vin) {
    swigfaissJNI.int_maxheap_array_t_addn_with_ids__SWIG_4(swigCPtr, this, nj, SWIGTYPE_p_int.getCPtr(vin));
  }

  public void reorder() {
    swigfaissJNI.int_maxheap_array_t_reorder(swigCPtr, this);
  }

  public void per_line_extrema(SWIGTYPE_p_int vals_out, SWIGTYPE_p_long idx_out) {
    swigfaissJNI.int_maxheap_array_t_per_line_extrema(swigCPtr, this, SWIGTYPE_p_int.getCPtr(vals_out), SWIGTYPE_p_long.getCPtr(idx_out));
  }

  public int_maxheap_array_t() {
    this(swigfaissJNI.new_int_maxheap_array_t(), true);
  }

}
