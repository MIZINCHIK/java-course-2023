package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task7.MSB;
import static edu.hw1.Task7.rotateLeft;
import static edu.hw1.Task7.rotateRight;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Rotate 8 by 1 to the right")
    void right8_4() {
        assertThat(rotateRight(8, 1)).isEqualTo(4);
    }

    @Test
    @DisplayName("Rotate 16 by 4 to the left")
    void left16_4() {
        assertThat(rotateLeft(16, 1)).isEqualTo(1);
    }

    @Test
    @DisplayName("Rotate 17 by 2 to the left")
    void left17_2() {
        assertThat(rotateLeft(17, 2)).isEqualTo(6);
    }

    @Test
    @DisplayName("Rotate Integers most significant bit by number of bits in an Integer to the left")
    void leftMSB() {
        assertThat(rotateLeft(MSB, Integer.SIZE)).isEqualTo(MSB);
    }

    @Test
    @DisplayName("Rotate Integers most significant bit by number of bits in an Integer to the right")
    void rightMSB() {
        assertThat(rotateRight(MSB, Integer.SIZE)).isEqualTo(MSB);
    }

    @Test
    @DisplayName("Rotate 17 by 5 to the right (by its whole length)")
    void right17_5() {
        assertThat(rotateRight(17, 5)).isEqualTo(17);
    }

    @Test
    @DisplayName("Rotate 17 by 5 to the left (by its whole length)")
    void left17_5() {
        assertThat(rotateLeft(17, 5)).isEqualTo(17);
    }
}
