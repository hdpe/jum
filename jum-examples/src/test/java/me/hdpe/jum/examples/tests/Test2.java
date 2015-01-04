package me.hdpe.jum.examples.tests;

import me.hdpe.jum.annotation.Mixins;
import me.hdpe.jum.examples.mixins.FeatureATests;
import me.hdpe.jum.examples.mixins.FeatureATests.SupportsFeatureATests;
import me.hdpe.jum.examples.mixins.FeatureBTests;
import me.hdpe.jum.examples.mixins.FeatureBTests.SupportsFeatureBTests;
import me.hdpe.jum.runner.MixinsRunner;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(MixinsRunner.class)
@Mixins({FeatureATests.class, FeatureBTests.class})
public class Test2 implements SupportsFeatureATests, SupportsFeatureBTests {

    @Before
    public void setUp() {
        System.out.println("before each test in " + Test2.class.getSimpleName());
    }
    
    public Object getFeatureADependency() {
        return "A";
    }
    
    public Object getFeatureBDependency() {
        return "B";
    }
}
