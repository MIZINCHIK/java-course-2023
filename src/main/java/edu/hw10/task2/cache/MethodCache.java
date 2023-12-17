package edu.hw10.task2.cache;

import java.lang.reflect.Method;

public interface MethodCache {
    boolean inCache(Object object, Method method, Object[] args);

    Object retrieveFromCache(Object object, Method method, Object[] args);

    void putInCache(Object object, Method method, Object[] args, Object result);
}
