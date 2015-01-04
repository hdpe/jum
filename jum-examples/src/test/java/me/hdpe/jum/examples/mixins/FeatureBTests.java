package me.hdpe.jum.examples.mixins;

import static org.junit.Assert.assertNotNull;
import me.hdpe.jum.annotation.TestReference;

import org.junit.Before;
import org.junit.Test;

public abstract class FeatureBTests {

    public interface SupportsFeatureBTests {
        Object getFeatureBDependency();
    }
    
    @TestReference
    public SupportsFeatureBTests test;
    
    @Before
    public void setUp() {
        System.out.println("before each test in " + FeatureBTests.class.getSimpleName());
    }
    
    @Test
    public void featureBTest() {
        assertNotNull(test.getFeatureBDependency());
    }
}
