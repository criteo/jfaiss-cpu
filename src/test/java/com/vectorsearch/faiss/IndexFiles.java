package com.vectorsearch.faiss;

import com.vectorsearch.faiss.swig.Index;
import com.vectorsearch.faiss.swig.IndexFlatL2;
import com.vectorsearch.faiss.swig.IndexIVFPQ;
import com.vectorsearch.faiss.swig.floatArray;
import com.vectorsearch.faiss.swig.swigfaiss;
import com.vectorsearch.faiss.utils.JFaissInitializer;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Writing an index to a file and reading it back, which is how indexes built elsewhere reach a server.
 */
public class IndexFiles {
    private static final int D = 32;
    private static final int NB = 5000;
    private static final int NQ = 50;
    private static final int K = 10;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @BeforeClass
    public static void loadLibrary() throws IOException {
        JFaissInitializer.initialize();
    }

    @Test
    public void indexReadBackFromAFileReturnsTheSameResults() throws IOException {
        final floatArray database = TestVectors.toNative(TestVectors.clustered(NB, D, 20, 3));
        final floatArray queries = TestVectors.toNative(TestVectors.clustered(NQ, D, 20, 4));
        final IndexFlatL2 quantizer = new IndexFlatL2(D);
        final IndexIVFPQ index = new IndexIVFPQ(quantizer, D, 32, 8, 8);
        index.train(NB, database.cast());
        index.add(NB, database.cast());
        index.setNprobe(4);
        final TestVectors.Result expected = TestVectors.search(index, queries, NQ, K);

        final File file = folder.newFile("ivfpq.index");
        swigfaiss.write_index(index, file.getAbsolutePath());
        final Index loaded = swigfaiss.read_index(file.getAbsolutePath());

        assertEquals(NB, loaded.getNtotal());
        assertEquals(D, loaded.getD());
        swigfaiss.extract_index_ivf(loaded).setNprobe(4);
        final TestVectors.Result actual = TestVectors.search(loaded, queries, NQ, K);
        assertArrayEquals(expected.ids, actual.ids);
        assertArrayEquals(expected.distances, actual.distances, 0f);
    }

    /** A Faiss error must surface as a Java exception, not abort the JVM. */
    @Test
    public void readingAMissingFileThrows() {
        final File missing = new File(folder.getRoot(), "missing.index");
        try {
            swigfaiss.read_index(missing.getAbsolutePath());
            fail("read_index on a missing file did not throw");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("missing.index"));
        }
    }
}
