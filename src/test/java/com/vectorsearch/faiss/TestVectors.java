package com.vectorsearch.faiss;

import com.vectorsearch.faiss.swig.Index;
import com.vectorsearch.faiss.swig.floatArray;
import com.vectorsearch.faiss.swig.longArray;

import java.util.Random;

/**
 * Deterministic test data and small helpers shared by the tests.
 */
final class TestVectors {

    private TestVectors() {
    }

    /**
     * {@code n} vectors of dimension {@code d} drawn around {@code clusters} random centres. Clustered data
     * gives well separated neighbours, so that results do not depend on the last bits of a distance.
     */
    static float[] clustered(int n, int d, int clusters, long seed) {
        final Random random = new Random(seed);
        final float[] centres = new float[clusters * d];
        for (int i = 0; i < centres.length; i++) {
            centres[i] = (float) random.nextGaussian();
        }
        final float[] vectors = new float[n * d];
        for (int i = 0; i < n; i++) {
            final int c = random.nextInt(clusters);
            for (int j = 0; j < d; j++) {
                vectors[i * d + j] = centres[c * d + j] + 0.3f * (float) random.nextGaussian();
            }
        }
        return vectors;
    }

    static floatArray toNative(float[] values) {
        final floatArray array = new floatArray(values.length);
        for (int i = 0; i < values.length; i++) {
            array.setitem(i, values[i]);
        }
        return array;
    }

    /** The {@code k} nearest neighbours of {@code nq} queries. */
    static final class Result {
        final int[] ids;
        final float[] distances;

        Result(int[] ids, float[] distances) {
            this.ids = ids;
            this.distances = distances;
        }
    }

    static Result search(Index index, floatArray queries, int nq, int k) {
        final floatArray distances = new floatArray(nq * k);
        final longArray ids = new longArray(nq * k);
        index.search(nq, queries.cast(), k, distances.cast(), ids.cast());
        final Result result = new Result(new int[nq * k], new float[nq * k]);
        for (int i = 0; i < nq * k; i++) {
            result.ids[i] = ids.getitem(i);
            result.distances[i] = distances.getitem(i);
        }
        return result;
    }
}
