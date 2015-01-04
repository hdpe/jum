package me.hdpe.jum.runner.support;

import java.util.List;

import me.hdpe.jum.annotation.TestReference;

import org.junit.runner.Description;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

class JumJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    private Class<?> suiteClass;
    private Subclasser subclasser = new JavassistSubclasser();
    
    public JumJUnit4ClassRunner(Class<?> suiteClass, Class<?> testClass) throws InitializationError {
        super(testClass);
        this.suiteClass = suiteClass;
    }

    protected Object createTest() throws Exception {
        Object test = subclasser.generateSubclass(getTestClass().getJavaClass()).getConstructor().newInstance();
       
        List<FrameworkField> supportFields = getTestClass().getAnnotatedFields(TestReference.class);
        
        if (!supportFields.isEmpty()) {
            Object suite = suiteClass.getConstructor().newInstance();
            for (FrameworkField field : supportFields) {
                field.getField().setAccessible(true);
                field.getField().set(test, suite);
            }
        }
        
        return test;
    }

    protected Description describeChild(FrameworkMethod method) {
        return Description.createTestDescription(suiteClass, method.getName(), method.getAnnotations());
    }
}