package me.hdpe.jum.examples.tests;

import me.hdpe.jum.annotation.Mixins;
import me.hdpe.jum.examples.mixins.FeatureA;
import me.hdpe.jum.examples.mixins.FeatureA.SupportsFeatureA;
import me.hdpe.jum.examples.mixins.FeatureB;
import me.hdpe.jum.examples.mixins.FeatureB.SupportsFeatureB;
import me.hdpe.jum.runner.MixinsRunner;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(MixinsRunner.class)
@Mixins({FeatureA.class, FeatureB.class})
public class Test2 implements SupportsFeatureA, SupportsFeatureB {

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
