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
        assertThat(countDigits(2147483647)).isEqualTo(10);
    }

    @Test
    @DisplayName("Case of counting digits in a number exceeding Long.MAX_VALUE")
    void longOverflow() {
        assertThat(countDigits(1e100)).isEqualTo(101);
    }
}
