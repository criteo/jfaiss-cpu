package com.vectorsearch.faiss.utils;

import com.vectorsearch.faiss.swig.SIMDConfig;

import java.io.IOException;
import java.util.logging.Logger;

public class JFaissInitializer {

    private static final Logger LOGGER = Logger.getLogger(JFaissInitializer.class.getName());

    private static volatile boolean initialized = false;

    public static void initialize() throws IOException {
        if (!initialized) {
            initialized = true;
            NativeUtils2.loadLibraryFromJar(JFaissConstants.SWIGFAISS_SO_FILE, JFaissConstants.REQUIRED_SO_FILE);
            // The level is chosen once, when the native library is loaded: the best
            // one the CPU supports, or the one forced with FAISS_SIMD_LEVEL. Log it,
            // since it is otherwise invisible from the Java side.
            LOGGER.info("Faiss SIMD level: " + SIMDConfig.get_level_name()
                    + (SIMDConfig.has_dynamic_dispatch() ? " (runtime dispatch)" : " (fixed at build time)"));
        }
    }
}
