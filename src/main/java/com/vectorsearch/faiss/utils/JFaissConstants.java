package com.vectorsearch.faiss.utils;

import java.util.Collections;
import java.util.List;

public class JFaissConstants {
    public static final List<String> SUPPORTED_OS = Collections.singletonList("Linux");
    public static final String SWIGFAISS_SO_FILE = "/libswigfaiss.so";
    public static final String[] REQUIRED_SO_FILE = new String[]{
            "/libgomp.so.1",
            "/libquadmath.so.0",
            "/libgfortran.so.5",
            "/libopenblas.so.0",
    };
}
