package me.hdpe.jum.examples.tests;

import static org.junit.Assert.fail;
import me.hdpe.jum.annotation.Mixins;
import me.hdpe.jum.examples.mixins.FeatureATests;
import me.hdpe.jum.examples.mixins.FeatureATests.SupportsFeatureATests;
import me.hdpe.jum.runner.MixinsRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MixinsRunner.class)
@Mixins({FeatureATests.class})
public class Test1 implements SupportsFeatureATests {

    @Before
    public void setUp() {
        System.out.println("before each test in " + Test1.class.getSimpleName());
    }
    
    @Test
    public void specificTest() {
       fail(); 
    }
    
    public Object getFeatureADependency() {
        return "A";
    }
}
