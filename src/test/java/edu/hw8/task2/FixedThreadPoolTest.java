package edu.hw8.task2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLongArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class FixedThreadPoolTest {
    private static final int NUMBER_OF_SAMPLES = 10_000;

    @Test
    @DisplayName("Calculating Fibonacci numbers in a single thread")
    void start_when1ThreadCalculatesFibonacci_thenCorrect() {
        Random random = new Random();
        int boundInLong = 93;
        int[] samples = new int[NUMBER_OF_SAMPLES];
        AtomicLongArray results = new AtomicLongArray(new long[NUMBER_OF_SAMPLES]);
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            samples[i] = random.nextInt(boundInLong);
        }
        try (ThreadPool pool = FixedThreadPool.create(1)) {
            pool.start();
            for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
                int index = i;
                pool.execute(() -> calculateNthFibonacci(samples[index], index, results));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            assertThat(results.get(i)).isEqualTo(calculateNthFibonacci(samples[i]));
        }
    }

    @RepeatedTest(5)
    @DisplayName("Calculating Fibonacci numbers in 4 threads thread")
    void start_whenMultipleThreadsCalculateFibonacci_thenCorrect() {
        Random random = new Random();
        int numberOfThreads = random.nextInt(2, 16);
        int boundInLong = 93;
        int[] samples = new int[NUMBER_OF_SAMPLES];
        AtomicLongArray results = new AtomicLongArray(new long[NUMBER_OF_SAMPLES]);
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            samples[i] = random.nextInt(boundInLong);
        }
        try (ThreadPool pool = FixedThreadPool.create(numberOfThreads)) {
            pool.start();
            for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
                int index = i;
                pool.execute(() -> calculateNthFibonacci(samples[index], index, results));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            assertThat(results.get(i)).isEqualTo(calculateNthFibonacci(samples[i]));
        }
    }

    @Test
    @DisplayName("Calculating Fibonacci numbers in 4 threads thread")
    void close_whenClosingRunningThread_thenNoMoreTasksMayBeExecuted() throws Exception {
        ThreadPool pool = FixedThreadPool.create(1);
        pool.close();
        assertThatThrownBy(() ->
            pool.execute(() -> System.err.println("Shouldn't be printed")))
            .isInstanceOf(IllegalStateException.class);
    }

    private static void calculateNthFibonacci(int number, int index, AtomicLongArray results) {
        if (number <= 1) {
            results.set(index, number);
            return;
        }
        long previous = 0;
        long current = 1;
        int currentIndex = 1;
        while (currentIndex++ < number) {
            long temporary = previous;
            previous = current;
            current += temporary;
        }
        results.set(index, current);
    }

    private static long calculateNthFibonacci(int number) {
        if (number <= 1) {
            return number;
        }
        long previous = 0;
        long current = 1;
        int currentIndex = 1;
        while (currentIndex++ < number) {
            long temporary = previous;
            previous = current;
            current += temporary;
        }
        return current;
    }
}
