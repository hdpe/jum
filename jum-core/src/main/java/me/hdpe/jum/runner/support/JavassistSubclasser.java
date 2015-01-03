package me.hdpe.jum.runner.support;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;

public class JavassistSubclasser implements Subclasser {

    private static final Map<Class<?>, Class<?>> SUBCLASS_CACHE = new HashMap<>();
    
    @Override
    public <T> Class<? extends T> generateSubclass(Class<T> clazz) {
        Class<? extends T> result = getSubclassFromCache(clazz);
        if (result == null) {
            result = doGenerate(clazz);
            putSubclassToCache(clazz, result);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends T> getSubclassFromCache(Class<T> clazz) {
        return (Class<? extends T>) SUBCLASS_CACHE.get(clazz);
    }
    
    private static <T> void putSubclassToCache(Class<T> clazz, Class<? extends T> result) {
        SUBCLASS_CACHE.put(clazz, result);
    }
    
    @SuppressWarnings("unchecked")
    private <T> Class<? extends T> doGenerate(Class<T> clazz) {
        ClassPool pool = ClassPool.getDefault();
        
        try {
            CtClass superclass = pool.get(clazz.getName());
            CtClass subclass = pool.makeClass(createSubclassName(clazz));
            subclass.setSuperclass(superclass);
            subclass.setModifiers(Modifier.PUBLIC);
            
            return subclass.toClass();
        } catch (NotFoundException | CannotCompileException e) {
            throw new SubclassException(e);
        }
    }

    private <T> String createSubclassName(Class<T> clazz) {
        return clazz.getName() + "$___byJum" + JavassistSubclasser.class.getSimpleName();
    }
}
