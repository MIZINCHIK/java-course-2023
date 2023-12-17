package edu.hw10.task2;

import edu.hw10.task2.cache.DiskMethodCache;
import edu.hw10.task2.cache.MemoryMethodCache;
import edu.hw10.task2.cache.MethodCache;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CacheProxy<T> implements InvocationHandler {
    private T core;
    private final DiskMethodCache diskCache;
    private final MemoryMethodCache memoryCache;

    public CacheProxy(T core) {
        this.core = core;
        diskCache = new DiskMethodCache();
        memoryCache = new MemoryMethodCache();
    }

    public static <T> T create(T object, Class<?> clazz) {
        CacheProxy handler = new CacheProxy(object);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache annotation = method.getAnnotation(Cache.class);
        if (annotation == null) {
            return method.invoke(core, args);
        } else {
            MethodCache cache = annotation.persist() ? diskCache : memoryCache;
            if (!cache.inCache(core, method, args)) {
                Object result = method.invoke(core, args);
                cache.putInCache(core, method, args, result);
                return result;
            } else {
                return cache.retrieveFromCache(core, method, args);
            }
        }
    }
}
