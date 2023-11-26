package edu.hw7.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw7.task4.PiMonteCarloCalculator.calculatePi;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PiMonteCarloTest {
    @Test
    @DisplayName("Calculating Pi 10_000_000 iterations 4 threads")
    void calculatePi_illegalArguments_illegalArgumentException() {
        assertThatThrownBy(() -> calculatePi(0, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .message().isEqualTo("Number of points and threads must be no less than 1");
        assertThatThrownBy(() -> calculatePi(1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .message().isEqualTo("Number of points and threads must be no less than 1");
        assertThatThrownBy(() -> calculatePi(-1, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .message().isEqualTo("Number of points and threads must be no less than 1");
        assertThatThrownBy(() -> calculatePi(1, -1))
            .isInstanceOf(IllegalArgumentException.class)
            .message().isEqualTo("Number of points and threads must be no less than 1");
    }

    @Test
    @DisplayName("Calculating Pi 10_000_000 iterations 1 thread")
    void calculatePi_singleThread10000000Points_somewhatNear() {
        assertThat(Math.abs(Math.PI - calculatePi(10_000_000, 1))
            < 0.01).isTrue();
    }

    @Test
    @DisplayName("Calculating Pi 1_000_000_000 iterations 4 threads")
    void calculatePi_multiThread10000000Points_somewhatNear() {
        assertThat(Math.abs(Math.PI - calculatePi(10_000_000, 8))
            < 0.01).isTrue();
    }
}
