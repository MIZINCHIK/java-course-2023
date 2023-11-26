package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import static edu.hw5.Task2.getAllFridaysThe13th;
import static edu.hw5.Task2.nextFridayThe13th;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class Task2Test {
    @Test
    @DisplayName("Fridays the 13th of the year 1925")
    void getAllFridaysThe13th_when1925_thenListOfRelevantDates() {
        var expected = new ArrayList<LocalDate>();
        expected.add(LocalDate.of(1925, Month.FEBRUARY, 13));
        expected.add(LocalDate.of(1925, Month.MARCH, 13));
        expected.add(LocalDate.of(1925, Month.NOVEMBER, 13));
        assertIterableEquals(expected, getAllFridaysThe13th(1925));
    }

    @Test
    @DisplayName("Fridays the 13th of the year 2024")
    void getAllFridaysThe13th_when2024_thenListOfRelevantDates() {
        var expected = new ArrayList<LocalDate>();
        expected.add(LocalDate.of(2024, Month.SEPTEMBER, 13));
        expected.add(LocalDate.of(2024, Month.DECEMBER, 13));
        assertIterableEquals(expected, getAllFridaysThe13th(2024));
    }

    @Test
    @DisplayName("Next Friday the 13th after 06.11.2023 is 13.09.2024")
    void nextFridayThe13th_when06112023_then13092024() {
        assertThat(nextFridayThe13th(LocalDate.of(2023, Month.NOVEMBER, 6)))
            .isEqualTo(LocalDate.of(2024, Month.SEPTEMBER, 13));
    }

    @Test
    @DisplayName("Next Friday the 13th after 13.09.2024 is 13.12.2024")
    void nextFridayThe13th_when13092024_then13122024() {
        assertThat(nextFridayThe13th(LocalDate.of(2024, Month.SEPTEMBER, 13)))
            .isEqualTo(LocalDate.of(2024, Month.DECEMBER, 13));
    }
}
