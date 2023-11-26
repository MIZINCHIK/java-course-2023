package edu.hw7.task1;

import java.util.concurrent.atomic.AtomicLong;

public class MultithreadCounter {
    private final AtomicLong counter;

    public MultithreadCounter() {
        this.counter = new AtomicLong(0);
    }

    public long countToNumber(long iterations, int numberOfThreads) {
        if (iterations < 0 || numberOfThreads < 1) {
            return 0;
        }
        counter.set(0);
        Thread[] pool = new Thread[numberOfThreads];
        long singleIteration = iterations / numberOfThreads;
        for (int i = 1; i < numberOfThreads; i++) {
            pool[i] = new Thread(() -> incrementCounterNTimes(singleIteration));
        }
        long oddIteration = singleIteration + iterations % numberOfThreads;
        pool[0] = new Thread(() -> incrementCounterNTimes(oddIteration));
        for (Thread thread : pool) {
            thread.start();
        }
        for (Thread thread : pool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return counter.get();
    }

    private void incrementCounterNTimes(long times) {
        for (long i = 0; i < times; i++) {
            counter.incrementAndGet();
        }
    }
}
