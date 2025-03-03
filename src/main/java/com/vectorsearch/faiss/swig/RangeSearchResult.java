/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class RangeSearchResult {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected RangeSearchResult(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(RangeSearchResult obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_RangeSearchResult(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setNq(long value) {
    swigfaissJNI.RangeSearchResult_nq_set(swigCPtr, this, value);
  }

  public long getNq() {
    return swigfaissJNI.RangeSearchResult_nq_get(swigCPtr, this);
  }

  public void setLims(SWIGTYPE_p_long value) {
    swigfaissJNI.RangeSearchResult_lims_set(swigCPtr, this, SWIGTYPE_p_long.getCPtr(value));
  }

  public SWIGTYPE_p_long getLims() {
    long cPtr = swigfaissJNI.RangeSearchResult_lims_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public void setLabels(SWIGTYPE_p_long value) {
    swigfaissJNI.RangeSearchResult_labels_set(swigCPtr, this, SWIGTYPE_p_long.getCPtr(value));
  }

  public SWIGTYPE_p_long getLabels() {
    long cPtr = swigfaissJNI.RangeSearchResult_labels_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public void setDistances(SWIGTYPE_p_float value) {
    swigfaissJNI.RangeSearchResult_distances_set(swigCPtr, this, SWIGTYPE_p_float.getCPtr(value));
  }

  public SWIGTYPE_p_float getDistances() {
    long cPtr = swigfaissJNI.RangeSearchResult_distances_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public void setBuffer_size(long value) {
    swigfaissJNI.RangeSearchResult_buffer_size_set(swigCPtr, this, value);
  }

  public long getBuffer_size() {
    return swigfaissJNI.RangeSearchResult_buffer_size_get(swigCPtr, this);
  }

  public RangeSearchResult(int nq, boolean alloc_lims) {
    this(swigfaissJNI.new_RangeSearchResult__SWIG_0(nq, alloc_lims), true);
  }

  public RangeSearchResult(long nq) {
    this(swigfaissJNI.new_RangeSearchResult__SWIG_1(nq), true);
  }

  public void do_allocation() {
    swigfaissJNI.RangeSearchResult_do_allocation(swigCPtr, this);
  }

}
