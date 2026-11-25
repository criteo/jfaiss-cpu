package com.vectorsearch.faiss;

import com.vectorsearch.faiss.swig.RawJniEntryPoints;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OneFlat.class,
        TwoIVFFlat.class,
        ThreeIVFPQ.class,
        IndexFiles.class,
        SimdLevels.class,
        RawJniEntryPoints.class
})
public class FaissTestSuite {
}
