/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class SliceInvertedLists extends ReadOnlyInvertedLists {
  private transient long swigCPtr;

  protected SliceInvertedLists(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.SliceInvertedLists_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SliceInvertedLists obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_SliceInvertedLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setIl(InvertedLists value) {
    swigfaissJNI.SliceInvertedLists_il_set(swigCPtr, this, InvertedLists.getCPtr(value), value);
  }

  public InvertedLists getIl() {
    long cPtr = swigfaissJNI.SliceInvertedLists_il_get(swigCPtr, this);
    return (cPtr == 0) ? null : new InvertedLists(cPtr, false);
  }

  public void setI0(long value) {
    swigfaissJNI.SliceInvertedLists_i0_set(swigCPtr, this, value);
  }

  public long getI0() {
    return swigfaissJNI.SliceInvertedLists_i0_get(swigCPtr, this);
  }

  public void setI1(long value) {
    swigfaissJNI.SliceInvertedLists_i1_set(swigCPtr, this, value);
  }

  public long getI1() {
    return swigfaissJNI.SliceInvertedLists_i1_get(swigCPtr, this);
  }

  public SliceInvertedLists(InvertedLists il, long i0, long i1) {
    this(swigfaissJNI.new_SliceInvertedLists(InvertedLists.getCPtr(il), il, i0, i1), true);
  }

  public long list_size(long list_no) {
    return swigfaissJNI.SliceInvertedLists_list_size(swigCPtr, this, list_no);
  }

  public SWIGTYPE_p_unsigned_char get_codes(long list_no) {
    long cPtr = swigfaissJNI.SliceInvertedLists_get_codes(swigCPtr, this, list_no);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public SWIGTYPE_p_long get_ids(long list_no) {
    long cPtr = swigfaissJNI.SliceInvertedLists_get_ids(swigCPtr, this, list_no);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public void release_codes(long list_no, SWIGTYPE_p_unsigned_char codes) {
    swigfaissJNI.SliceInvertedLists_release_codes(swigCPtr, this, list_no, SWIGTYPE_p_unsigned_char.getCPtr(codes));
  }

  public void release_ids(long list_no, SWIGTYPE_p_long ids) {
    swigfaissJNI.SliceInvertedLists_release_ids(swigCPtr, this, list_no, SWIGTYPE_p_long.getCPtr(ids));
  }

  public long get_single_id(long list_no, long offset) {
    return swigfaissJNI.SliceInvertedLists_get_single_id(swigCPtr, this, list_no, offset);
  }

  public SWIGTYPE_p_unsigned_char get_single_code(long list_no, long offset) {
    long cPtr = swigfaissJNI.SliceInvertedLists_get_single_code(swigCPtr, this, list_no, offset);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public void prefetch_lists(SWIGTYPE_p_long list_nos, int nlist) {
    swigfaissJNI.SliceInvertedLists_prefetch_lists(swigCPtr, this, SWIGTYPE_p_long.getCPtr(list_nos), nlist);
  }

}
