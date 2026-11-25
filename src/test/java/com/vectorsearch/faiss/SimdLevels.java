package com.vectorsearch.faiss;

import com.vectorsearch.faiss.swig.Index;
import com.vectorsearch.faiss.swig.IndexFlatL2;
import com.vectorsearch.faiss.swig.IndexHNSWFlat;
import com.vectorsearch.faiss.swig.IndexIVFFlat;
import com.vectorsearch.faiss.swig.IndexIVFPQ;
import com.vectorsearch.faiss.swig.MetricType;
import com.vectorsearch.faiss.swig.SIMDConfig;
import com.vectorsearch.faiss.swig.SIMDLevel;
import com.vectorsearch.faiss.swig.floatArray;
import com.vectorsearch.faiss.utils.JFaissInitializer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * The native library holds several implementations of the hot loops (generic, AVX2, AVX-512, ...) and picks
 * one when it is loaded: the best one the CPU supports, or the one named by the environment variable
 * FAISS_SIMD_LEVEL. These tests check that the choice is the expected one and that every level this CPU can
 * run returns the same neighbours.
 */
public class SimdLevels {
    private static final Logger LOGGER = Logger.getLogger(SimdLevels.class.getName());

    private static final SIMDLevel[] X86_LEVELS = {
            SIMDLevel.NONE, SIMDLevel.AVX2, SIMDLevel.AVX512, SIMDLevel.AVX512_VPOPCNT, SIMDLevel.AVX512_SPR
    };

    private static final int D = 32;
    private static final int NB = 20000;
    private static final int NQ = 200;
    private static final int K = 10;

    @BeforeClass
    public static void loadLibrary() throws IOException {
        JFaissInitializer.initialize();
    }

    private static List<SIMDLevel> availableLevels() {
        final List<SIMDLevel> levels = new ArrayList<>();
        for (SIMDLevel level : X86_LEVELS) {
            if (SIMDConfig.is_simd_level_available(level)) {
                levels.add(level);
            }
        }
        return levels;
    }

    /**
     * A jar built without runtime dispatch would silently run generic code everywhere. Pass
     * -Djfaiss.optLevel=generic to test a deliberately generic build (jni/Makefile FAISS_OPT_LEVEL=generic).
     */
    @Test
    public void libraryHasRuntimeDispatch() {
        assumeTrue(!"generic".equals(System.getProperty("jfaiss.optLevel")));
        assertTrue("libswigfaiss.so was built without runtime SIMD dispatch (FAISS_OPT_LEVEL=dd)",
                SIMDConfig.has_dynamic_dispatch());
        assertTrue(SIMDConfig.is_simd_level_available(SIMDLevel.NONE));
    }

    /** When FAISS_SIMD_LEVEL is set (as in the extra surefire executions), it decides the level. */
    @Test
    public void environmentVariableSelectsTheLevel() {
        final String forced = System.getenv("FAISS_SIMD_LEVEL");
        assumeTrue("FAISS_SIMD_LEVEL is not set", forced != null);
        assertEquals(forced, SIMDConfig.get_level_name());
    }

    @Test
    public void everyAvailableLevelReturnsTheSameNeighbours() {
        final floatArray database = TestVectors.toNative(TestVectors.clustered(NB, D, 50, 1));
        final floatArray queries = TestVectors.toNative(TestVectors.clustered(NQ, D, 50, 2));

        final Map<String, Index> indexes = new LinkedHashMap<>();
        indexes.put("Flat", new IndexFlatL2(D));
        final IndexFlatL2 ivfFlatQuantizer = new IndexFlatL2(D);
        final IndexIVFFlat ivfFlat = new IndexIVFFlat(ivfFlatQuantizer, D, 64, MetricType.METRIC_L2);
        ivfFlat.setNprobe(8);
        indexes.put("IVFFlat", ivfFlat);
        final IndexFlatL2 ivfPqQuantizer = new IndexFlatL2(D);
        final IndexIVFPQ ivfPq = new IndexIVFPQ(ivfPqQuantizer, D, 64, 8, 8);
        ivfPq.setNprobe(8);
        indexes.put("IVFPQ", ivfPq);
        final IndexHNSWFlat hnsw = new IndexHNSWFlat(D, 16);
        hnsw.getHnsw().setEfSearch(64);
        indexes.put("HNSW", hnsw);

        final SIMDLevel initial = SIMDConfig.get_level();
        final List<SIMDLevel> levels = availableLevels();
        LOGGER.info("SIMD level at load: " + SIMDConfig.get_level_name() + ", available: " + levels);
        try {
            // Build with the generic code, so that every level searches the very same index.
            SIMDConfig.set_level(SIMDLevel.NONE);
            for (Index index : indexes.values()) {
                index.train(NB, database.cast());
                index.add(NB, database.cast());
                assertEquals(NB, index.getNtotal());
            }

            for (Map.Entry<String, Index> entry : indexes.entrySet()) {
                SIMDConfig.set_level(SIMDLevel.NONE);
                final TestVectors.Result reference = TestVectors.search(entry.getValue(), queries, NQ, K);
                for (SIMDLevel level : levels) {
                    SIMDConfig.set_level(level);
                    assertEquals(level.toString(), SIMDConfig.get_level_name());
                    final TestVectors.Result result = TestVectors.search(entry.getValue(), queries, NQ, K);
                    final String what = entry.getKey() + " at " + level + " vs NONE";
                    assertArrayEquals("ids, " + what, reference.ids, result.ids);
                    for (int i = 0; i < reference.distances.length; i++) {
                        final float expected = reference.distances[i];
                        assertEquals("distance " + i + ", " + what, expected, result.distances[i],
                                1e-4f * Math.max(1f, Math.abs(expected)));
                    }
                }
            }
        } finally {
            SIMDConfig.set_level(initial);
        }
    }
}
