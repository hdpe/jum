package me.hdpe.jum.runner.support;

import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.Runner;

class JumJUnit4Builder extends JUnit4Builder {

    private Class<?> suiteClass;

    public JumJUnit4Builder(Class<?> suiteClass) {
        this.suiteClass = suiteClass;
    }

    public Runner runnerForClass(Class<?> testClass) throws Throwable {
       return new JumJUnit4ClassRunner(suiteClass, testClass);
    }
}