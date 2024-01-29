package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MultithreadCounterTest {
    @Test
    @DisplayName("Negative amount of increments results in no iterations")
    void countToNumber_negativeIterations_zero() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(-1, 1)).isEqualTo(0);
    }

    @Test
    @DisplayName("Zero amount of threads results in no iterations")
    void countToNumber_zeroThreads_zero() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(1, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("Negative amount of threads results in no iterations")
    void countToNumber_negativeThreads_zero() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(1, -1)).isEqualTo(0);
    }

    @Test
    @DisplayName("Counting even times in a single thread is equal to adding")
    void countToNumber_singleThreadEven_correct() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(1_000_000, 1)).isEqualTo(1_000_000);
    }

    @Test
    @DisplayName("Counting odd times in a single thread is equal to adding")
    void countToNumber_singleThreadOdd_correct() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(1_000_001, 1)).isEqualTo(1_000_001);
    }

    @Test
    @DisplayName("Counting even times in multiple threads is equal to adding")
    void countToNumber_multipleThreadsEven_correct() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(1_000_000, 8)).isEqualTo(1_000_000);
    }

    @Test
    @DisplayName("Counting odd times in multiple threads is equal to adding")
    void countToNumber_multipleThreadsOdd_correct() {
        var counter = new MultithreadCounter();
        assertThat(counter.countToNumber(1_000_001, 8)).isEqualTo(1_000_001);
    }
}
