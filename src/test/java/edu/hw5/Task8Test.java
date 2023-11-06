package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task8.amountZeroesMultiple3;
import static edu.hw5.Task8.containsMinimum2ZeroesMaximum1One;
import static edu.hw5.Task8.eachOddEqual1;
import static edu.hw5.Task8.isOddLength;
import static edu.hw5.Task8.noConsecutiveOnes;
import static edu.hw5.Task8.not11Or111;
import static edu.hw5.Task8.startsZeroOddOneEvenLength;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Test {
    @Test
    @DisplayName("String of even length doesn't match")
    void isOddLength_whenIsEvenLength_thenFalse() {
        assertThat(isOddLength("11")).isFalse();
        assertThat(isOddLength("01")).isFalse();
        assertThat(isOddLength("1001001010010101")).isFalse();
        assertThat(isOddLength("1000")).isFalse();
        assertThat(isOddLength("00000000")).isFalse();
    }

    @Test
    @DisplayName("String of odd length does match")
    void isOddLength_whenIsOddLength_thenTrue() {
        assertThat(isOddLength("110")).isTrue();
        assertThat(isOddLength("011")).isTrue();
        assertThat(isOddLength("100100101001010")).isTrue();
        assertThat(isOddLength("100")).isTrue();
        assertThat(isOddLength("0000000")).isTrue();
    }

    @Test
    @DisplayName("String starting with 0 of even length doesn't match")
    void startsZeroOddOneEvenLength_whenZeroEvenLength_thenFalse() {
        assertThat(startsZeroOddOneEvenLength("0000")).isFalse();
        assertThat(startsZeroOddOneEvenLength("0110")).isFalse();
        assertThat(startsZeroOddOneEvenLength("00100101001010")).isFalse();
        assertThat(startsZeroOddOneEvenLength("00")).isFalse();
        assertThat(startsZeroOddOneEvenLength("001010")).isFalse();
    }

    @Test
    @DisplayName("String starting with 1 of odd length doesn't match")
    void startsZeroOddOneEvenLength_whenOneOddLength_thenFalse() {
        assertThat(startsZeroOddOneEvenLength("100")).isFalse();
        assertThat(startsZeroOddOneEvenLength("110")).isFalse();
        assertThat(startsZeroOddOneEvenLength("1000101001010")).isFalse();
        assertThat(startsZeroOddOneEvenLength("11111")).isFalse();
    }

    @Test
    @DisplayName("String starting with 0 of odd length does match")
    void startsZeroOddOneEvenLength_whenZeroOddLength_thenTrue() {
        assertThat(startsZeroOddOneEvenLength("000")).isTrue();
        assertThat(startsZeroOddOneEvenLength("011")).isTrue();
        assertThat(startsZeroOddOneEvenLength("0010010100101")).isTrue();
        assertThat(startsZeroOddOneEvenLength("0")).isTrue();
        assertThat(startsZeroOddOneEvenLength("00101")).isTrue();
    }

    @Test
    @DisplayName("String starting with 1 of even length does match")
    void startsZeroOddOneEvenLength_whenOneEvenLength_thenTrue() {
        assertThat(startsZeroOddOneEvenLength("10")).isTrue();
        assertThat(startsZeroOddOneEvenLength("11")).isTrue();
        assertThat(startsZeroOddOneEvenLength("100010100101")).isTrue();
        assertThat(startsZeroOddOneEvenLength("1111")).isTrue();
    }

    @Test
    @DisplayName("String with amount of 0's that isn't multiple of 3 doesn't match")
    void amountZeroesMultiple3_whenIsntMultiple3_thenFalse() {
        assertThat(amountZeroesMultiple3("110")).isFalse();
        assertThat(amountZeroesMultiple3("01")).isFalse();
        assertThat(amountZeroesMultiple3("100100101001011")).isFalse();
        assertThat(amountZeroesMultiple3("100")).isFalse();
        assertThat(amountZeroesMultiple3("00000000")).isFalse();
    }

    @Test
    @DisplayName("String with amount of 0's that is multiple of 3 does match")
    void amountZeroesMultiple3_whenIsMultiple3_thenTrue() {
        assertThat(amountZeroesMultiple3("11")).isTrue();
        assertThat(amountZeroesMultiple3("0001")).isTrue();
        assertThat(amountZeroesMultiple3("1001001010010110")).isTrue();
        assertThat(amountZeroesMultiple3("0100")).isTrue();
        assertThat(amountZeroesMultiple3("000000000")).isTrue();
    }

    @Test
    @DisplayName("Neither 11 nor 111 match")
    void not11Or111_when11Or111_thenFalse() {
        assertThat(not11Or111("11")).isFalse();
        assertThat(not11Or111("111")).isFalse();
    }

    @Test
    @DisplayName("All the strings besides 11 or 111 match")
    void not11Or111_whenNot11Or111_thenFalse() {
        assertThat(not11Or111("111111111")).isTrue();
        assertThat(not11Or111("11100010101010")).isTrue();
        assertThat(not11Or111("101010101111")).isTrue();
        assertThat(not11Or111("0000111")).isTrue();
        assertThat(not11Or111("00101011")).isTrue();
        assertThat(not11Or111("011")).isTrue();
    }

    @Test
    @DisplayName("When there are 0's on odd positions the string doesn't match")
    void eachOddEqual1_whenAre0OnOdd_thenFalse() {
        assertThat(eachOddEqual1("01")).isFalse();
        assertThat(eachOddEqual1("110")).isFalse();
        assertThat(eachOddEqual1("110")).isFalse();
        assertThat(eachOddEqual1("00000000000")).isFalse();
    }

    @Test
    @DisplayName("When there are only 1's on odd positions the string does match")
    void eachOddEqual1_whenNo0OnOdd_thenTrue() {
        assertThat(eachOddEqual1("")).isTrue();
        assertThat(eachOddEqual1("1111111111")).isTrue();
        assertThat(eachOddEqual1("10101010")).isTrue();
        assertThat(eachOddEqual1("1")).isTrue();
    }

    @Test
    @DisplayName("String with less than 2 zeroes or more than 1 one doesn't match")
    void containsMinimum2ZeroesMaximum1One_whenLessZeroesOrMoreOnes_thenFalse() {
        assertThat(containsMinimum2ZeroesMaximum1One("01")).isFalse();
        assertThat(containsMinimum2ZeroesMaximum1One("110")).isFalse();
        assertThat(containsMinimum2ZeroesMaximum1One("1000000000010")).isFalse();
        assertThat(containsMinimum2ZeroesMaximum1One("11")).isFalse();
    }

    @Test
    @DisplayName("When there are only 1's on odd positions the string does match")
    void containsMinimum2ZeroesMaximum1One_whenMatch_thenTrue() {
        assertThat(containsMinimum2ZeroesMaximum1One("100")).isTrue();
        assertThat(containsMinimum2ZeroesMaximum1One("00000010")).isTrue();
        assertThat(containsMinimum2ZeroesMaximum1One("00")).isTrue();
        assertThat(containsMinimum2ZeroesMaximum1One("00000000000100000000000000")).isTrue();
    }

    @Test
    @DisplayName("String with consecutive 1's doesn't match")
    void noConsecutiveOnes_whenAreConsecutiveOnes_thenFalse() {
        assertThat(noConsecutiveOnes("11")).isFalse();
        assertThat(noConsecutiveOnes("01010000011")).isFalse();
        assertThat(noConsecutiveOnes("00000000000011111111111111100000000000")).isFalse();
        assertThat(noConsecutiveOnes("0100101010101010101010111010101001010101110010101010")).isFalse();
    }

    @Test
    @DisplayName("String with no consecutive 1's does match")
    void noConsecutiveOnes_whenNoConsecutiveOnes_thenTrue() {
        assertThat(noConsecutiveOnes("01010101010100101010101010101")).isTrue();
        assertThat(noConsecutiveOnes("")).isTrue();
        assertThat(noConsecutiveOnes("010101010101000000000001010000100001010100101010")).isTrue();
        assertThat(noConsecutiveOnes("1")).isTrue();
    }
}
