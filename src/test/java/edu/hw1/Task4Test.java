package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task4.fixString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Fixing a string of digits")
    void numbers() {
        assertThat(fixString("123456")).isEqualTo("214365");
    }

    @Test
    @DisplayName("Fixing a phrase")
    void phrase() {
        assertThat(fixString("hTsii  s aimex dpus rtni.g")).isEqualTo("This is a mixed up string.");
    }

    @Test
    @DisplayName("Fixing a string of odd length")
    void odd() {
        assertThat(fixString("badce")).isEqualTo("abcde");
    }
}
