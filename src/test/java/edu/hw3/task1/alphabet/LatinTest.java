package edu.hw3.task1.alphabet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LatinTest {
    private final Latin latin = new Latin();

    @Test
    @DisplayName("inAlphabet returns false for characters not in the Latin alphabet")
    void inAlphabet_nonLatinCharacter_false() {
        assertThat(latin.inAlphabet('1')).isFalse();
    }

    @Test
    @DisplayName("inAlphabet returns true for lowercase characters in the Latin alphabet")
    void inAlphabet_latinCharacterLowerCase_true() {
        assertThat(latin.inAlphabet('a')).isTrue();
    }

    @Test
    @DisplayName("inAlphabet returns true for uppercase characters in the Latin alphabet")
    void inAlphabet_latinCharacterUpperCase_true() {
        assertThat(latin.inAlphabet('A')).isTrue();
    }

    @Test
    @DisplayName("Mirroring uppercase beginning of the alphabet")
    void mirrorSymbol_inAlphabetUpperCaseLeftBorder_mirrored() {
        assertThat(latin.mirrorSymbol('A')).isEqualTo('Z');
    }

    @Test
    @DisplayName("Mirroring lowercase beginning of the alphabet")
    void mirrorSymbol_inAlphabetLowerCaseLeftBorder_mirrored() {
        assertThat(latin.mirrorSymbol('a')).isEqualTo('z');
    }

    @Test
    @DisplayName("Mirroring uppercase ending of the alphabet")
    void mirrorSymbol_inAlphabetUpperCaseRightBorder_mirrored() {
        assertThat(latin.mirrorSymbol('Z')).isEqualTo('A');
    }

    @Test
    @DisplayName("Mirroring lowercase ending of the alphabet")
    void mirrorSymbol_inAlphabetLowerCaseRightBorder_mirrored() {
        assertThat(latin.mirrorSymbol('z')).isEqualTo('a');
    }

    @Test
    @DisplayName("Mirroring uppercase inside character of the alphabet")
    void mirrorSymbol_inAlphabetUpperCaseInside_mirrored() {
        assertThat(latin.mirrorSymbol('N')).isEqualTo('M');
    }

    @Test
    @DisplayName("Mirroring lowercase inside character of the alphabet")
    void mirrorSymbol_inAlphabetLowerCaseInside_mirrored() {
        assertThat(latin.mirrorSymbol('m')).isEqualTo('n');
    }

    @Test
    @DisplayName("Mirroring characters not in the alphabet")
    void mirrorSymbol_notInAlphabet_unchanged() {
        assertThat(latin.mirrorSymbol('1')).isEqualTo('1');
    }
}
