package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task3.isNestable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Empty array is never able to nest another one")
    void emptyNest() {
        assertThat(isNestable(new int[]{1, 2, 3, 4}, new int[]{})).isFalse();
    }

    @Test
    @DisplayName("Empty array is always nestable (except for the case above)")
    void emptyNestable() {
        assertThat(isNestable(new int[]{}, new int[]{0, 6})).isTrue();
    }

    @Test
    @DisplayName("Null array is never able to nest another one")
    void nullNest() {
        assertThat(isNestable(new int[]{1, 2, 3, 4}, null)).isFalse();
    }

    @Test
    @DisplayName("Null is always nestable")
    void emptyNull() {
        assertThat(isNestable(null, new int[]{0, 6})).isTrue();
    }

    @Test
    @DisplayName("First case of a nestable array")
    void nestable1() {
        assertThat(isNestable(new int[]{1, 2, 3, 4}, new int[]{0, 6})).isTrue();
    }

    @Test
    @DisplayName("Second case of a nestable array")
    void nestable2() {
        assertThat(isNestable(new int[]{3, 1}, new int[]{4, 0})).isTrue();
    }

    @Test
    @DisplayName("First case of a non-nestable array")
    void notNestable1() {
        assertThat(isNestable(new int[]{9, 9, 8}, new int[]{8, 9})).isFalse();
    }

    @Test
    @DisplayName("Second case of a non-nestable array")
    void notNestable2() {
        assertThat(isNestable(new int[]{1, 2, 3, 4}, new int[]{2, 3})).isFalse();
    }
}
