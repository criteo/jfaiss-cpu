package com.vectorsearch.faiss;

import com.vectorsearch.faiss.swig.Index;
import com.vectorsearch.faiss.swig.floatArray;
import com.vectorsearch.faiss.swig.longArray;
import com.vectorsearch.faiss.utils.JFaissInitializer;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public abstract class FaissTestCase {
    public static final Random random = new Random(42);

    public abstract void train();

    public abstract void search();

    static {
        try {
            JFaissInitializer.initialize();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load the Faiss native library", e);
        }
    }

    /** Every row holds existing ids, ordered from the nearest to the farthest. */
    static void assertValidResults(floatArray distances, longArray ids, int rows, int k, long ntotal) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < k; j++) {
                final int id = ids.getitem(i * k + j);
                assertTrue("row " + i + ": id " + id, id >= 0 && id < ntotal);
                if (j > 0) {
                    assertTrue("row " + i + ": distances are not sorted",
                            distances.getitem(i * k + j - 1) <= distances.getitem(i * k + j));
                }
            }
        }
    }

    /**
     * Searches the first {@code n} indexed vectors and returns how many of them are their own nearest
     * neighbour: all of them for an exact index, nearly all for a compressed one.
     */
    static int countSelfMatches(Index index, floatArray vectors, int n) {
        final floatArray distances = new floatArray(n);
        final longArray ids = new longArray(n);
        index.search(n, vectors.cast(), 1, distances.cast(), ids.cast());
        int matches = 0;
        for (int i = 0; i < n; i++) {
            if (ids.getitem(i) == i) {
                matches++;
            }
        }
        return matches;
    }
}
