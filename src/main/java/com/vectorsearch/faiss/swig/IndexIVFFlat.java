/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class IndexIVFFlat extends IndexIVF {
  private transient long swigCPtr;

  protected IndexIVFFlat(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexIVFFlat_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexIVFFlat obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_IndexIVFFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public IndexIVFFlat(Index quantizer, long d, long nlist_, MetricType arg3) {
    this(swigfaissJNI.new_IndexIVFFlat__SWIG_0(Index.getCPtr(quantizer), quantizer, d, nlist_, arg3.swigValue()), true);
  }

  public IndexIVFFlat(Index quantizer, long d, long nlist_) {
    this(swigfaissJNI.new_IndexIVFFlat__SWIG_1(Index.getCPtr(quantizer), quantizer, d, nlist_), true);
  }

  public void add_core(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long xids, SWIGTYPE_p_long precomputed_idx) {
    swigfaissJNI.IndexIVFFlat_add_core(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(xids), SWIGTYPE_p_long.getCPtr(precomputed_idx));
  }

  public void add_with_ids(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long xids) {
    swigfaissJNI.IndexIVFFlat_add_with_ids(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(xids));
  }

  public void encode_vectors(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long list_nos, SWIGTYPE_p_unsigned_char codes, boolean include_listnos) {
    swigfaissJNI.IndexIVFFlat_encode_vectors__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(list_nos), SWIGTYPE_p_unsigned_char.getCPtr(codes), include_listnos);
  }

  public void encode_vectors(long n, SWIGTYPE_p_float x, SWIGTYPE_p_long list_nos, SWIGTYPE_p_unsigned_char codes) {
    swigfaissJNI.IndexIVFFlat_encode_vectors__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long.getCPtr(list_nos), SWIGTYPE_p_unsigned_char.getCPtr(codes));
  }

  public SWIGTYPE_p_faiss__InvertedListScanner get_InvertedListScanner(boolean store_pairs) {
    long cPtr = swigfaissJNI.IndexIVFFlat_get_InvertedListScanner(swigCPtr, this, store_pairs);
    return (cPtr == 0) ? null : new SWIGTYPE_p_faiss__InvertedListScanner(cPtr, false);
  }

  public void reconstruct_from_offset(long list_no, long offset, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexIVFFlat_reconstruct_from_offset(swigCPtr, this, list_no, offset, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void sa_decode(long n, SWIGTYPE_p_unsigned_char bytes, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVFFlat_sa_decode(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(bytes), SWIGTYPE_p_float.getCPtr(x));
  }

  public IndexIVFFlat() {
    this(swigfaissJNI.new_IndexIVFFlat__SWIG_2(), true);
  }

}
