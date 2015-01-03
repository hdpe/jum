package me.hdpe.jum.runner.support;

public interface Subclasser {

    <T> Class<? extends T> generateSubclass(Class<T> clazz);

}