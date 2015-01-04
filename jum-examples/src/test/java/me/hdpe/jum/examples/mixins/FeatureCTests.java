package me.hdpe.jum.examples.mixins;

import static org.junit.Assert.assertNotNull;
import me.hdpe.jum.annotation.TestReference;

import org.junit.Test;

public abstract class FeatureCTests {

    public interface SupportsFeatureCTests {
        Object getFeatureCDependency();
    }
    
    @TestReference
    public SupportsFeatureCTests test;

    @Test
    public void featureCTest() {
        assertNotNull(test.getFeatureCDependency());
    }
}
