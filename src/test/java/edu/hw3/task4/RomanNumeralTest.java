package edu.hw3.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.task4.RomanNumeral.convertToRoman;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RomanNumeralTest {
    @Test
    @DisplayName("Zero can't be represented as a Roman numeral")
    void convertToRoman_zero_null() {
        assertThat(convertToRoman(0)).isNull();
    }

    @Test
    @DisplayName("Negative numbers can't be represented as a Roman numeral")
    void convertToRoman_negative_null() {
        assertThat(convertToRoman(-100)).isNull();
    }

    @Test
    @DisplayName("Numbers larger than 3999 can't be represented as a Roman numeral")
    void convertToRoman_tooLarge_null() {
        assertThat(convertToRoman(-100)).isNull();
    }

    @Test
    @DisplayName("The smallest Roman numeral")
    void convertToRoman_leftBound_I() {
        assertThat(convertToRoman(1)).isEqualTo("I");
    }

    @Test
    @DisplayName("The largest Roman numeral")
    void convertToRoman_rightBound_I() {
        assertThat(convertToRoman(3999)).isEqualTo("MMMCMXCIX");
    }

    @Test
    @DisplayName("Converting arabic 16 to Roman numerals")
    void convertToRoman_16_XVI() {
        assertThat(convertToRoman(16)).isEqualTo("XVI");
    }
}
