package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task6.countK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    @DisplayName("Number of iterations for the Kaprekar number itself")
    void numberKaprekar() {
        assertThat(countK(6174)).isEqualTo(0);
    }

    @Test
    @DisplayName("Number of iterations for 6621")
    void number6621() {
        assertThat(countK(6621)).isEqualTo(5);
    }

    @Test
    @DisplayName("Number of iterations for 6554")
    void number6554() {
        assertThat(countK(6554)).isEqualTo(4);
    }

    @Test
    @DisplayName("Number of iterations for 1234")
    void number1234() {
        assertThat(countK(1234)).isEqualTo(3);
    }

    @Test
    @DisplayName("Number of iterations for 3524")
    void number3524() {
        assertThat(countK(3524)).isEqualTo(3);
    }
}
