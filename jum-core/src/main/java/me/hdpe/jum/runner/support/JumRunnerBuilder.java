package me.hdpe.jum.runner.support;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.internal.builders.JUnit4Builder;

public class JumRunnerBuilder extends AllDefaultPossibilitiesBuilder {

    private Class<?> suiteClass;
    
    public JumRunnerBuilder(Class<?> suiteClass) {
        super(true);
        this.suiteClass = suiteClass;
    }

    protected JUnit4Builder junit4Builder() {
        return new JumJUnit4Builder(suiteClass);
    }
}