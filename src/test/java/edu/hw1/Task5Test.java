package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Number that is indeed a special palindrome")
    void numberTrue() {
        assertThat(Task5.isPalindromeDescendant(11211230)).isTrue();
    }

    @Test
    @DisplayName("Number that isn't a special palindrome")
    void numberFalse() {
        assertThat(Task5.isPalindromeDescendant(12)).isFalse();
    }

    @Test
    @DisplayName("Single-digit number is a special palindrome")
    void numberSingleDigit() {
        assertThat(Task5.isPalindromeDescendant(1)).isTrue();
    }
}
