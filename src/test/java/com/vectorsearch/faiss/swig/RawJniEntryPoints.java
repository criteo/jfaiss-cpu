package com.vectorsearch.faiss.swig;

import com.vectorsearch.faiss.utils.JFaissInitializer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Some consumers were compiled against jfaiss-cpu 1.7.0 and call the SWIG-generated JNI class directly. The
 * numbered overloads ({@code __SWIG_0}, {@code __SWIG_1}, ...) follow the order of the C++ declarations, so a
 * new upstream overload can silently change what a number means. This test lives in the generated package to
 * make the same calls and pins the meaning of the entry points known to be used that way.
 */
public class RawJniEntryPoints {
    private static final int D = 16;
    private static final int NLIST = 8;
    private static final int M = 4;
    private static final int NBITS = 6;
    private static final int NB = 2000;

    @BeforeClass
    public static void loadLibrary() throws IOException {
        JFaissInitializer.initialize();
    }

    /** new_IndexIVFPQ__SWIG_0 is (quantizer, d, nlist, M, nbits, metric), as in FAISS 1.7.0. */
    @Test
    public void indexIvfPqSixArgumentConstructor() {
        final IndexFlatL2 quantizer = new IndexFlatL2(D);
        final long pointer = swigfaissJNI.new_IndexIVFPQ__SWIG_0(
                Index.getCPtr(quantizer), quantizer, D, NLIST, M, NBITS, MetricType.METRIC_INNER_PRODUCT.swigValue());
        final IndexIVFPQ index = new IndexIVFPQ(pointer, true);

        assertEquals(D, index.getD());
        assertEquals(NLIST, index.getInvlists().getNlist());
        assertEquals(M, index.getPq().getM());
        assertEquals(NBITS, index.getPq().getNbits());
        assertEquals(MetricType.METRIC_INNER_PRODUCT, index.getMetric_type());

        final Random random = new Random(7);
        final floatArray vectors = new floatArray(NB * D);
        for (int i = 0; i < NB * D; i++) {
            vectors.setitem(i, random.nextFloat());
        }
        index.train(NB, vectors.cast());
        index.add(NB, vectors.cast());
        assertEquals(NB, index.getNtotal());

        final int k = 5;
        final floatArray distances = new floatArray(k);
        final longArray ids = new longArray(k);
        index.setNprobe(NLIST);
        index.search(1, vectors.cast(), k, distances.cast(), ids.cast());
        for (int i = 0; i < k; i++) {
            assertTrue("id " + ids.getitem(i), ids.getitem(i) >= 0 && ids.getitem(i) < NB);
        }
    }

    /** new_IndexHNSWFlat__SWIG_1 is (d, M, metric). */
    @Test
    public void indexHnswFlatThreeArgumentConstructor() {
        final int m = 12;
        final long pointer = swigfaissJNI.new_IndexHNSWFlat__SWIG_1(
                D, m, MetricType.METRIC_INNER_PRODUCT.swigValue());
        final IndexHNSWFlat index = new IndexHNSWFlat(pointer, true);

        assertEquals(D, index.getD());
        assertEquals(MetricType.METRIC_INNER_PRODUCT, index.getMetric_type());
        // M links per node on the upper layers, 2 * M on the bottom layer.
        assertEquals(m, index.getHnsw().nb_neighbors(1));
        assertEquals(2 * m, index.getHnsw().nb_neighbors(0));
    }

    /** new_OPQMatrix__SWIG_0 is (d, M, d2). */
    @Test
    public void opqMatrixThreeArgumentConstructor() {
        final OPQMatrix opq = new OPQMatrix(swigfaissJNI.new_OPQMatrix__SWIG_0(D, M, 8), true);

        assertEquals(D, opq.getD_in());
        assertEquals(M, opq.getM());
        assertEquals(8, opq.getD_out());
    }

    /**
     * The other calls of the same consumers, together: an index made by the factory, a direct map to
     * reconstruct stored vectors, parameters set by name, and a transform in front of an index.
     */
    @Test
    public void factoryDirectMapParametersAndPreTransform() {
        final Random random = new Random(11);
        final floatArray vectors = new floatArray(NB * D);
        for (int i = 0; i < NB * D; i++) {
            vectors.setitem(i, random.nextFloat());
        }

        final Index flat = swigfaiss.index_factory(D, "IVF8,Flat", MetricType.METRIC_L2);
        swigfaiss.extract_index_ivf(flat).set_direct_map_type(DirectMap.Type.Array);
        flat.train(NB, vectors.cast());
        flat.add(NB, vectors.cast());
        new ParameterSpace().set_index_parameters(flat, "nprobe=5");
        assertEquals(5, swigfaiss.extract_index_ivf(flat).getNprobe());
        final floatArray stored = new floatArray(D);
        flat.reconstruct(NB - 1, stored.cast());
        for (int j = 0; j < D; j++) {
            assertEquals(vectors.getitem((NB - 1) * D + j), stored.getitem(j), 0f);
        }

        final OPQMatrix opq = new OPQMatrix(swigfaissJNI.new_OPQMatrix__SWIG_0(D, M, D), true);
        final IndexFlatL2 quantizer = new IndexFlatL2(D);
        final IndexIVFPQ ivfPq = new IndexIVFPQ(swigfaissJNI.new_IndexIVFPQ__SWIG_0(
                Index.getCPtr(quantizer), quantizer, D, NLIST, M, NBITS, MetricType.METRIC_L2.swigValue()), true);
        final IndexPreTransform index = new IndexPreTransform(opq, ivfPq);
        index.setOwn_fields(false);
        index.train(NB, vectors.cast());
        index.add(NB, vectors.cast());
        assertTrue(index.getIs_trained());
        assertEquals(NB, index.getNtotal());

        final int k = 5;
        final floatArray distances = new floatArray(k);
        final longArray ids = new longArray(k);
        new ParameterSpace().set_index_parameters(index, "nprobe=" + NLIST);
        index.search(1, vectors.cast(), k, distances.cast(), ids.cast());
        for (int i = 0; i < k; i++) {
            assertTrue("id " + ids.getitem(i), ids.getitem(i) >= 0 && ids.getitem(i) < NB);
        }
    }
}
