package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task6.isSubSequence;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    @DisplayName("String that is not a subsequence")
    void isSubSequence_whenNotSubSequence_thenFalse() {
        assertThat(isSubSequence("abb", "abcd")).isFalse();
    }

    @Test
    @DisplayName("String is substring it is subsequence")
    void isSubSequence_whenSubString_thenTrue() {
        assertThat(isSubSequence("abc", "abcd")).isTrue();
    }

    @Test
    @DisplayName("String is subsequence but not substring true is returned")
    void isSubSequence_whenSubSequence_thenTrue() {
        assertThat(isSubSequence("abd", "abcd")).isTrue();
    }

    @Test
    @DisplayName("String that is a subsequence of a larger string")
    void isSubSequence_whenSubSequenceInTheMiddle_thenTrue() {
        assertThat(isSubSequence("abc", "achfdbaabgabacaabg")).isTrue();
    }

    @Test
    @DisplayName("String that is not a subsequence of a larger string")
    void isSubSequence_whenNotSubSequenceInTheMiddle_thenTrue() {
        assertThat(isSubSequence("abc", "achfdbaabgabaabg")).isFalse();
    }
}
