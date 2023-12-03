package edu.hw8.task2;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private Thread[] pool;
    private Queue<Integer> freeIndices;
    private boolean closed;
    private boolean startRightAway;

    private FixedThreadPool() {
    }

    public static FixedThreadPool create(int numberOfThreads) {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException();
        }
        var pool = new FixedThreadPool();
        pool.pool = new Thread[numberOfThreads];
        Queue<Integer> freeIndices = new ArrayBlockingQueue<>(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            freeIndices.add(i);
        }
        pool.freeIndices = freeIndices;
        return pool;
    }

    @Override
    public void start() {
        for (var thread : pool) {
            if (thread != null) {
                thread.start();
            }
        }
        startRightAway = true;
    }

    @Override
    public void execute(Runnable runnable) {
        if (closed) {
            throw new IllegalStateException();
        }
        Integer freeIndex = null;
        while (freeIndex == null) {
            freeIndex = freeIndices.poll();
        }
        int index = freeIndex;
        pool[index] = new Thread(() -> notifyWhenThreadDone(runnable, index));
        if (startRightAway) {
            pool[freeIndex].start();
        }
    }

    @Override
    public void close() throws Exception {
        for (var thread : pool) {
            if (thread != null) {
                thread.join();
            }
        }
        closed = true;
    }

    private void notifyWhenThreadDone(Runnable task, int index) {
        try {
            task.run();
        } finally {
            freeIndices.add(index);
        }
    }
}
