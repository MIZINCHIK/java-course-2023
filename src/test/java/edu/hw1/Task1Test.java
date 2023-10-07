package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task1.minutesToSeconds;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Seconds exceeding the limit value of 59")
    void secondsOverflow() {
        long seconds = minutesToSeconds("00:60");
        assertThat(seconds)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Exactly 59 seconds")
    void secondsEdge() {
        long seconds = minutesToSeconds("00:59");
        assertThat(seconds)
            .isEqualTo(59);
    }

    @Test
    @DisplayName("Negative amount of seconds")
    void secondsNegative() {
        long seconds = minutesToSeconds("00:-01");
        assertThat(seconds)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Seconds given in one digit")
    void secondsSingleDigit() {
        long seconds = minutesToSeconds("00:1");
        assertThat(seconds)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Seconds given in three digits")
    void secondsThreeDigits() {
        long seconds = minutesToSeconds("00:001");
        assertThat(seconds)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("Negative amount of minutes with correct format for seconds")
    void resultNegative() {
        long seconds = minutesToSeconds("-100:00");
        assertThat(seconds)
            .isEqualTo(-6000);
    }

    @Test
    @DisplayName("Result overflowing int")
    void resultLarge() {
        long seconds = minutesToSeconds("2147483647:00");
        assertThat(seconds)
            .isEqualTo(2147483647L * 60);
    }

    @Test
    @DisplayName("Result overflowing int")
    void resultZero() {
        long seconds = minutesToSeconds("00:00");
        assertThat(seconds)
            .isEqualTo(0);
    }
}
