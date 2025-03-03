/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vectorsearch.faiss.swig;

public class Level1Quantizer {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Level1Quantizer(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Level1Quantizer obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_Level1Quantizer(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setQuantizer(Index value) {
    swigfaissJNI.Level1Quantizer_quantizer_set(swigCPtr, this, Index.getCPtr(value), value);
  }

  public Index getQuantizer() {
    long cPtr = swigfaissJNI.Level1Quantizer_quantizer_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public void setNlist(long value) {
    swigfaissJNI.Level1Quantizer_nlist_set(swigCPtr, this, value);
  }

  public long getNlist() {
    return swigfaissJNI.Level1Quantizer_nlist_get(swigCPtr, this);
  }

  public void setQuantizer_trains_alone(char value) {
    swigfaissJNI.Level1Quantizer_quantizer_trains_alone_set(swigCPtr, this, value);
  }

  public char getQuantizer_trains_alone() {
    return swigfaissJNI.Level1Quantizer_quantizer_trains_alone_get(swigCPtr, this);
  }

  public void setOwn_fields(boolean value) {
    swigfaissJNI.Level1Quantizer_own_fields_set(swigCPtr, this, value);
  }

  public boolean getOwn_fields() {
    return swigfaissJNI.Level1Quantizer_own_fields_get(swigCPtr, this);
  }

  public void setCp(ClusteringParameters value) {
    swigfaissJNI.Level1Quantizer_cp_set(swigCPtr, this, ClusteringParameters.getCPtr(value), value);
  }

  public ClusteringParameters getCp() {
    long cPtr = swigfaissJNI.Level1Quantizer_cp_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ClusteringParameters(cPtr, false);
  }

  public void setClustering_index(Index value) {
    swigfaissJNI.Level1Quantizer_clustering_index_set(swigCPtr, this, Index.getCPtr(value), value);
  }

  public Index getClustering_index() {
    long cPtr = swigfaissJNI.Level1Quantizer_clustering_index_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public void train_q1(long n, SWIGTYPE_p_float x, boolean verbose, MetricType metric_type) {
    swigfaissJNI.Level1Quantizer_train_q1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), verbose, metric_type.swigValue());
  }

  public long coarse_code_size() {
    return swigfaissJNI.Level1Quantizer_coarse_code_size(swigCPtr, this);
  }

  public void encode_listno(long list_no, SWIGTYPE_p_unsigned_char code) {
    swigfaissJNI.Level1Quantizer_encode_listno(swigCPtr, this, list_no, SWIGTYPE_p_unsigned_char.getCPtr(code));
  }

  public long decode_listno(SWIGTYPE_p_unsigned_char code) {
    return swigfaissJNI.Level1Quantizer_decode_listno(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(code));
  }

  public Level1Quantizer(Index quantizer, long nlist) {
    this(swigfaissJNI.new_Level1Quantizer__SWIG_0(Index.getCPtr(quantizer), quantizer, nlist), true);
  }

  public Level1Quantizer() {
    this(swigfaissJNI.new_Level1Quantizer__SWIG_1(), true);
  }

}
