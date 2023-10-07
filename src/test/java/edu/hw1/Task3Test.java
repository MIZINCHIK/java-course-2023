package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task3.isNestable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("First case of a nested array")
    void nested1() {
        assertThat(isNestable(new int[]{1, 2, 3, 4}, new int[]{0, 6})).isTrue();
    }

    @Test
    @DisplayName("First case of a nested array")
    void nested2() {
        assertThat(isNestable(new int[]{3, 1}, new int[]{4, 0})).isTrue();
    }

    @Test
    @DisplayName("First case of a nested array")
    void notNested1() {
        assertThat(isNestable(new int[]{9, 9, 8}, new int[]{8, 9})).isFalse();
    }

    @Test
    @DisplayName("First case of a nested array")
    void notNested2() {
        assertThat(isNestable(new int[]{1, 2, 3, 4}, new int[]{2, 3})).isFalse();
    }
}
