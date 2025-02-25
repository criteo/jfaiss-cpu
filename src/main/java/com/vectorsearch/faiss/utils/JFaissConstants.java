package com.vectorsearch.faiss.utils;

import java.util.Collections;
import java.util.List;

public class JFaissConstants {
    public static final List<String> SUPPORTED_OS = Collections.singletonList("Linux");
    public static final String SWIGFAISS_SO_FILE = "/_swigfaiss_avx2.so";
    public static final String[] REQUIRED_SO_FILE = new String[]{
            "/libgomp.so.1",
            "/libmkl_core.so",
            "/libmkl_gnu_thread.so",
            "/libmkl_intel_lp64.so"};
}
