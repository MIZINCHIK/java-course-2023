package edu.hw10.task2.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class MemoryMethodCache implements MethodCache {
    private final Map<Object, Map<Method, Map<List<Object>, Object>>> cache;

    public MemoryMethodCache() {
        cache = new HashMap<>();
    }

    @Override
    public boolean inCache(@NotNull Object object, @NotNull Method method, Object[] args) {
        return cache.containsKey(object) && cache.get(object).containsKey(method)
            && cache.get(object).get(method).containsKey(transformArgs(args));
    }

    @Override
    public Object retrieveFromCache(@NotNull Object object, @NotNull Method method, Object[] args) {
        return cache.get(object).get(method).get(transformArgs(args));
    }

    @Override
    public void putInCache(@NotNull Object object, @NotNull Method method, Object[] args, Object result) {
        if (!cache.containsKey(object)) {
            HashMap<Method, Map<List<Object>, Object>> methodsResults = new HashMap<>();
            cache.put(object, methodsResults);
        }
        var methods = cache.get(object);
        if (!methods.containsKey(method)) {
            methods.put(method, new HashMap<>());
        }
        var results = methods.get(method);
        results.put(transformArgs(args), result);
    }

    private List<Object> transformArgs(Object[] args) {
        return args == null ? null : Arrays.asList(args);
    }
}
