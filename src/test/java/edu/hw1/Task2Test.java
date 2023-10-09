package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task2.countDigits;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Case of counting digits in a 0")
    void zero() {
        assertThat(countDigits(0)).isEqualTo(1);
    }

    @Test
    @DisplayName("Case of counting digits in a single-digit number")
    void singleDigit() {
        assertThat(countDigits(5)).isEqualTo(1);
    }

    @Test
    @DisplayName("Case of counting digits in an Integer.MAX_VALUE")
    void intOverflow() {
        assertThat(countDigits(Integer.MAX_VALUE)).isEqualTo(10);
    }

    @Test
    @DisplayName("Case of counting digits in a Long.MAX_VALUE")
    void longMaxValue() {
        assertThat(countDigits(Long.MAX_VALUE)).isEqualTo(19);
    }
}
