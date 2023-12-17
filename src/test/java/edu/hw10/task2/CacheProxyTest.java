package edu.hw10.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CacheProxyTest {
    private static interface TestInterface {
        @Cache(persist = true)
        Integer getOneHundred() throws InterruptedException;
        @Cache
        Integer getTwoHundred() throws InterruptedException;
    }

    private static class TestClass implements TestInterface {
        @Override
        public Integer getOneHundred() throws InterruptedException {
            Thread.sleep(1000);
            return 100;
        }

        @Override
        public Integer getTwoHundred() throws InterruptedException {
            Thread.sleep(1000);
            return 200;
        }
    }

    @Test
    @DisplayName("On-disk cached method")
    void invoke_whenMethodCalledOnProxyPersist_thenResultSameAsWithOrigin() throws InterruptedException {
        TestInterface proxy = CacheProxy.create(new TestClass(), TestClass.class);
        assertThat(proxy.getOneHundred()).isEqualTo(new TestClass().getOneHundred());
    }

    @Test
    @DisplayName("In-memory cached method")
    void invoke_whenMethodCalledOnProxyMemory_thenResultSameAsWithOrigin() throws InterruptedException {
        TestInterface proxy = CacheProxy.create(new TestClass(), TestClass.class);
        assertThat(proxy.getTwoHundred()).isEqualTo(new TestClass().getTwoHundred());
    }

    @RepeatedTest(5)
    @DisplayName("Speed comparison for persistent caching")
    void invoke_whenCachingOnDisk_thenFaster() throws InterruptedException {
        TestInterface proxy = CacheProxy.create(new TestClass(), TestClass.class);
        long start = System.nanoTime();
        proxy.getOneHundred();
        long end = System.nanoTime();
        long start2 = System.nanoTime();
        proxy.getOneHundred();
        long end2 = System.nanoTime();
        assertThat((end2 - start2) * 10 < end - start).isTrue();
    }

    @RepeatedTest(5)
    @DisplayName("Speed comparison for memory caching")
    void invoke_whenCachingInMemory_thenFaster() throws InterruptedException {
        TestInterface proxy = CacheProxy.create(new TestClass(), TestClass.class);
        long start = System.nanoTime();
        proxy.getTwoHundred();
        long end = System.nanoTime();
        long start2 = System.nanoTime();
        proxy.getTwoHundred();
        long end2 = System.nanoTime();
        assertThat((end2 - start2) * 10 < end - start).isTrue();
    }
}
