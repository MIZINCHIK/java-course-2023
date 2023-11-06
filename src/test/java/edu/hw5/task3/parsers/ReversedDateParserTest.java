package edu.hw5.task3.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReversedDateParserTest {
    @Test
    @DisplayName("Completely incorrect strings can't be parsed")
    void canParseDate_whenIncorrectString_thenFalse() {
        assertThat(new ReversedDateParser().canParseDate("sfahsjfd")).isFalse();
    }

    @Test
    @DisplayName("Normal format can be parsed")
    void canParseDate_whenNormalFormat_thenTrue() {
        assertThat(new ReversedDateParser().canParseDate("10/10/2020")).isTrue();
    }

    @Test
    @DisplayName("Short format can be parsed")
    void canParseDate_whenShortFormat_thenTrue() {
        assertThat(new ReversedDateParser().canParseDate("12/2/20")).isTrue();
    }

    @Test
    @DisplayName("Incorrect dates can't be parsed")
    void canParseDate_whenIncorrectDate_thenFalse() {
        assertThat(new ReversedDateParser().canParseDate("14/10/2020")).isFalse();
    }

    @Test
    @DisplayName("Parsing normal format")
    void parseDate_whenNormalFormat_thenTrue() {
        assertThat(new ReversedDateParser().parseDate("10/10/2020"))
            .isEqualTo(LocalDate.of(2020, Month.OCTOBER, 10));
    }

    @Test
    @DisplayName("Parsing short format")
    void parseDate_whenShortFormat_thenTrue() {
        assertThat(new ReversedDateParser().parseDate("12/2/20"))
            .isEqualTo(LocalDate.of(2020, Month.DECEMBER, 2));
    }
}
