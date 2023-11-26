package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task4.isPasswordCorrect;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("When password doesn't contain any required symbols false is returned")
    void isPasswordCorrect_whenIncorrect_thenFalse() {
        assertThat(isPasswordCorrect("sfdsfjncejewfehs")).isFalse();
    }

    @Test
    @DisplayName("When password contains a required symbol in the middle true is returned")
    void isPasswordCorrect_whenContainsRequiredMiddle_thenTrue() {
        assertThat(isPasswordCorrect("dsfdsfsdf~SFSDFFWEDWDsdw")).isTrue();
    }

    @Test
    @DisplayName("When password contains merely a required symbol true is returned")
    void isPasswordCorrect_whenContainsOnlyRequired_thenTrue() {
        assertThat(isPasswordCorrect("$")).isTrue();
    }
}
