package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task5.getDescendant;
import static edu.hw1.Task5.isPalindromeDescendant;
import static edu.hw1.Task5.isPalindromeItself;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Task5Test {
    @Test
    @DisplayName("Number that is indeed a special palindrome")
    void numberTrue() {
        assertThat(isPalindromeDescendant(11211230)).isTrue();
    }

    @Test
    @DisplayName("Number that isn't a special palindrome")
    void numberFalse() {
        assertThat(isPalindromeDescendant(12)).isFalse();
    }

    @Test
    @DisplayName("Single-digit number")
    void numberSingleDigit() {
        assertThat(isPalindromeDescendant(1)).isFalse();
    }

    @Test
    @DisplayName("String that itself is a palindrome")
    void palindromeItself() {
        assertThat(isPalindromeItself("1")).isTrue();
    }

    @Test
    @DisplayName("Descendant of a string of an odd length")
    void oddLength() {
        assertThatThrownBy(() -> getDescendant("111")).isInstanceOf(IllegalArgumentException.class);
    }
}
