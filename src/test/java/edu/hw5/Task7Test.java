package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task7.endsAsStarts;
import static edu.hw5.Task7.hasLength1To3;
import static edu.hw5.Task7.threeCharactersOrMore;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("All methods work just with the alphabet of 0 and 1")
    void all_whenIncorrectCharacters_thenFalse() {
        assertThat(threeCharactersOrMore("sa0fasd")).isFalse();
        assertThat(endsAsStarts("aa")).isFalse();
        assertThat(hasLength1To3("aa")).isFalse();
    }

    @Test
    @DisplayName("Strings less than 3 characters long don't match")
    void threeCharactersOrMore_whenLessThan3Characters_thenFalse() {
        assertThat(threeCharactersOrMore("00")).isFalse();
        assertThat(threeCharactersOrMore("01")).isFalse();
        assertThat(threeCharactersOrMore("10")).isFalse();
        assertThat(threeCharactersOrMore("11")).isFalse();
    }

    @Test
    @DisplayName("1 on third position")
    void threeCharactersOrMore_whenThirdNotZero_thenFalse() {
        assertThat(threeCharactersOrMore("0010011001")).isFalse();
        assertThat(threeCharactersOrMore("111")).isFalse();
        assertThat(threeCharactersOrMore("001")).isFalse();
    }

    @Test
    @DisplayName("Correct strings of 3 or more characters with 0 on third position")
    void threeCharactersOrMore_whenCorrectString_thenTrue() {
        assertThat(threeCharactersOrMore("1000011001")).isTrue();
        assertThat(threeCharactersOrMore("110")).isTrue();
        assertThat(threeCharactersOrMore("000000000")).isTrue();
    }

    @Test
    @DisplayName("Strings that don't end as they start")
    void endsAsStarts_whenIncorrectString_thenFalse() {
        assertThat(endsAsStarts("1000011000")).isFalse();
        assertThat(endsAsStarts("110")).isFalse();
        assertThat(endsAsStarts("000000001")).isFalse();
    }

    @Test
    @DisplayName("Strings that end as they start")
    void endsAsStarts_whenCorrectString_thenTrue() {
        assertThat(endsAsStarts("1000011001")).isTrue();
        assertThat(endsAsStarts("111")).isTrue();
        assertThat(endsAsStarts("000000000")).isTrue();
        assertThat(endsAsStarts("")).isTrue();
        assertThat(endsAsStarts("1")).isTrue();
        assertThat(endsAsStarts("0")).isTrue();
    }

    @Test
    @DisplayName("Strings that are shorter than 1 or longer than 3 symbols do not match")
    void hasLength1To3_whenIncorrectLength_thenFalse() {
        assertThat(hasLength1To3("1000011001")).isFalse();
        assertThat(hasLength1To3("")).isFalse();
    }

    @Test
    @DisplayName("Strings of length from 1 to 3 symbols inclusive do match")
    void hasLength1To3_whenCorrectLength_thenTrue() {
        assertThat(hasLength1To3("111")).isTrue();
        assertThat(hasLength1To3("1")).isTrue();
        assertThat(hasLength1To3("0")).isTrue();
        assertThat(hasLength1To3("10")).isTrue();
        assertThat(hasLength1To3("01")).isTrue();
    }
}
