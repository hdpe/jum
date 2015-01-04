package me.hdpe.jum.examples.mixins;

import static org.junit.Assert.assertNotNull;
import me.hdpe.jum.annotation.TestReference;

import org.junit.Test;

public abstract class FeatureC {

    public interface SupportsFeatureC {
        Object getFeatureCDependency();
    }
    
    @TestReference
    public SupportsFeatureC test;

    @Test
    public void featureCTest() {
        assertNotNull(test.getFeatureCDependency());
    }
}
