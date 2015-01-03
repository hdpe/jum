package me.hdpe.jum.runner;

import java.util.List;

import me.hdpe.jum.annotation.Mixins;
import me.hdpe.jum.runner.support.JumRunnerBuilder;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

public class MixinsRunner extends ParentRunner<Runner> {

    private List<Runner> runners;
    
    public MixinsRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        
        runners = new JumRunnerBuilder(clazz).runners(clazz, getMixinClasses(clazz));
        
        if (!getTestClass().getAnnotatedMethods(Test.class).isEmpty()) {
            runners.add(new BlockJUnit4ClassRunner(clazz));
        }
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    @Override
    protected Description describeChild(Runner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(Runner runner, final RunNotifier notifier) {
        runner.run(notifier);
    }

    private static Class<?>[] getMixinClasses(Class<?> clazz) throws InitializationError {
        Mixins annotation = clazz.getAnnotation(Mixins.class);
        if (annotation == null) {
            throw new InitializationError(String.format("class '%s' must have a %s annotation", clazz.getName(),
                    Mixins.class.getSimpleName()));
        }
        return annotation.value();
    }
}
