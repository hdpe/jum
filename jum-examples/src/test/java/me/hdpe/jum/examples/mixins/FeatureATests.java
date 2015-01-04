package me.hdpe.jum.examples.mixins;

import static org.junit.Assert.assertNotNull;
import me.hdpe.jum.annotation.TestReference;

import org.junit.Ignore;
import org.junit.Test;

public abstract class FeatureATests {

    public interface SupportsFeatureATests {
        Object getFeatureADependency();
    }
    
    @TestReference
    public SupportsFeatureATests test;

    @Test
    @Ignore
    public void featureATest1() {
        assertNotNull(test.getFeatureADependency());
    }
    
    @Test
    public void featureATest2() {
        assertNotNull(test.getFeatureADependency());
    }
}
