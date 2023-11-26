package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task5.isRussianLicencePlateValid;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    @DisplayName("When order is incorrect returns false")
    void isRussianLicencePlateValid_whenIncorrectOrder_thenFalse() {
        assertThat(isRussianLicencePlateValid("123АВЕ777")).isFalse();
    }

    @Test
    @DisplayName("When letters are incorrect returns false")
    void isRussianLicencePlateValid_whenIncorrectLetters_thenFalse() {
        assertThat(isRussianLicencePlateValid("А123ВГ77")).isFalse();
    }

    @Test
    @DisplayName("When letters are of incorrect amounts returns false")
    void isRussianLicencePlateValid_whenIncorrectLetterAmount_thenFalse() {
        assertThat(isRussianLicencePlateValid("А123В77")).isFalse();
        assertThat(isRussianLicencePlateValid("АА123ВЕ77")).isFalse();
    }

    @Test
    @DisplayName("When digits are of incorrect amounts returns false")
    void isRussianLicencePlateValid_whenIncorrectDigitAmount_thenFalse() {
        assertThat(isRussianLicencePlateValid("А12ВВ77")).isFalse();
        assertThat(isRussianLicencePlateValid("А1234ВЕ77")).isFalse();
    }

    @Test
    @DisplayName("Plates with registration code 000 are not allowed")
    void isRussianLicencePlateValid_whenIncorrectDigitValue_thenFalse() {
        assertThat(isRussianLicencePlateValid("А000ВВ77")).isFalse();
    }

    @Test
    @DisplayName("Average Russian license plate")
    void isRussianLicencePlateValid_whenCorrect_thenTrue() {
        assertThat(isRussianLicencePlateValid("А001ВВ77")).isTrue();
    }

    @Test
    @DisplayName("Some rare region codes")
    void isRussianLicencePlateValid_whenObscureAllowedRegionCode_thenTrue() {
        assertThat(isRussianLicencePlateValid("А001ВВ299")).isTrue();
        assertThat(isRussianLicencePlateValid("А001ВВ277")).isTrue();
        assertThat(isRussianLicencePlateValid("А001ВВ797")).isTrue();
        assertThat(isRussianLicencePlateValid("А001ВВ977")).isTrue();
        assertThat(isRussianLicencePlateValid("А001ВВ197")).isTrue();
    }

    @Test
    @DisplayName("Some region codes that don't exist")
    void isRussianLicencePlateValid_whenObscureNonexistentRegionCode_thenFalse() {
        assertThat(isRussianLicencePlateValid("А001ВВ00")).isFalse();
        assertThat(isRussianLicencePlateValid("А001ВВ20")).isFalse();
        assertThat(isRussianLicencePlateValid("А001ВВ100")).isFalse();
        assertThat(isRussianLicencePlateValid("А001ВВ101")).isFalse();
        assertThat(isRussianLicencePlateValid("А001ВВ775")).isFalse();
    }
}
