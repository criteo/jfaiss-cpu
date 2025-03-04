/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class DirectMapAdd {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected DirectMapAdd(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DirectMapAdd obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_DirectMapAdd(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setDirect_map(DirectMap value) {
    swigfaissJNI.DirectMapAdd_direct_map_set(swigCPtr, this, DirectMap.getCPtr(value), value);
  }

  public DirectMap getDirect_map() {
    return new DirectMap(swigfaissJNI.DirectMapAdd_direct_map_get(swigCPtr, this), false);
  }

  public void setType(DirectMap.Type value) {
    swigfaissJNI.DirectMapAdd_type_set(swigCPtr, this, value.swigValue());
  }

  public DirectMap.Type getType() {
    return DirectMap.Type.swigToEnum(swigfaissJNI.DirectMapAdd_type_get(swigCPtr, this));
  }

  public void setNtotal(long value) {
    swigfaissJNI.DirectMapAdd_ntotal_set(swigCPtr, this, value);
  }

  public long getNtotal() {
    return swigfaissJNI.DirectMapAdd_ntotal_get(swigCPtr, this);
  }

  public void setN(long value) {
    swigfaissJNI.DirectMapAdd_n_set(swigCPtr, this, value);
  }

  public long getN() {
    return swigfaissJNI.DirectMapAdd_n_get(swigCPtr, this);
  }

  public void setXids(SWIGTYPE_p_long value) {
    swigfaissJNI.DirectMapAdd_xids_set(swigCPtr, this, SWIGTYPE_p_long.getCPtr(value));
  }

  public SWIGTYPE_p_long getXids() {
    long cPtr = swigfaissJNI.DirectMapAdd_xids_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public void setAll_ofs(LongVector value) {
    swigfaissJNI.DirectMapAdd_all_ofs_set(swigCPtr, this, LongVector.getCPtr(value), value);
  }

  public LongVector getAll_ofs() {
    long cPtr = swigfaissJNI.DirectMapAdd_all_ofs_get(swigCPtr, this);
    return (cPtr == 0) ? null : new LongVector(cPtr, false);
  }

  public DirectMapAdd(DirectMap direct_map, long n, SWIGTYPE_p_long xids) {
    this(swigfaissJNI.new_DirectMapAdd(DirectMap.getCPtr(direct_map), direct_map, n, SWIGTYPE_p_long.getCPtr(xids)), true);
  }

  public void add(long i, long list_no, long offset) {
    swigfaissJNI.DirectMapAdd_add(swigCPtr, this, i, list_no, offset);
  }

}
