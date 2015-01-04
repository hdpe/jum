package me.hdpe.jum.examples.mixins;

import static org.junit.Assert.assertNotNull;
import me.hdpe.jum.annotation.TestReference;

import org.junit.Before;
import org.junit.Test;

public abstract class FeatureB {

    public interface SupportsFeatureB {
        Object getFeatureBDependency();
    }
    
    @TestReference
    public SupportsFeatureB test;
    
    @Before
    public void setUp() {
        System.out.println("before each test in " + FeatureB.class.getSimpleName());
    }
    
    @Test
    public void featureBTest() {
        assertNotNull(test.getFeatureBDependency());
    }
}
