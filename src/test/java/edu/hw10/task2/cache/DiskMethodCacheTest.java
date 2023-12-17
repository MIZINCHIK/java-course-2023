package edu.hw10.task2.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DiskMethodCacheTest {
    private static class TestClass {
        public String method1(String one, int two) {
            return "";
        }

        public int method2() {
            return 0;
        }

        public void method3() {
        }
    }

    @Test
    @DisplayName("Method result isn't in cache")
    void inCache_whenNotAddedBefore_thenFalse() throws NoSuchMethodException {
        MethodCache cache = new DiskMethodCache();
        TestClass testClass = new TestClass();
        assertThat(cache.inCache(
            testClass,
            testClass.getClass().getMethod("method1", String.class, int.class),
            new Object[] {null, 0}
        )).isFalse();
        assertThat(cache.inCache(
            testClass,
            testClass.getClass().getMethod("method2"),
            new Object[] {}
        )).isFalse();
        assertThat(cache.inCache(
            testClass,
            testClass.getClass().getMethod("method3"),
            null
        )).isFalse();
    }

    @Test
    @DisplayName("Method result is in cache")
    void inCache_whenAddedBefore_thenTrue() throws NoSuchMethodException {
        MethodCache cache = new DiskMethodCache();
        TestClass testClass = new TestClass();
        Method method = testClass.getClass().getMethod("method1", String.class, int.class);
        Object[] arguments = new Object[] {null, 0};
        cache.putInCache(testClass, method, arguments, null);
        assertThat(cache.inCache(testClass, method, arguments)).isTrue();
        method = testClass.getClass().getMethod("method2");
        arguments = new Object[] {};
        cache.putInCache(testClass, method, arguments, null);
        assertThat(cache.inCache(testClass, method, arguments)).isTrue();
        method = testClass.getClass().getMethod("method3");
        cache.putInCache(testClass, method, arguments, null);
        assertThat(cache.inCache(testClass, method, arguments)).isTrue();
    }

    @Test
    @DisplayName("Method result isn't in cache")
    void retrieveFromCache_whenDifferentTypes_thenCorrect() throws NoSuchMethodException {
        MethodCache cache = new DiskMethodCache();
        TestClass testClass = new TestClass();
        Method method = testClass.getClass().getMethod("method1", String.class, int.class);
        Object[] arguments = new Object[] {null, 0};
        cache.putInCache(testClass, method, arguments, "Howdy");
        assertThat(cache.retrieveFromCache(testClass, method, arguments)).isEqualTo("Howdy");
        method = testClass.getClass().getMethod("method2");
        arguments = new Object[] {};
        cache.putInCache(testClass, method, arguments, 123);
        assertThat(cache.retrieveFromCache(testClass, method, arguments)).isEqualTo(123);
        method = testClass.getClass().getMethod("method3");
        cache.putInCache(testClass, method, arguments, null);
        assertThat(cache.retrieveFromCache(testClass, method, arguments)).isEqualTo(null);
    }
}
