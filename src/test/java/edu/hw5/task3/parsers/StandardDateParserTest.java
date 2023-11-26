package edu.hw5.task3.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StandardDateParserTest {
    @Test
    @DisplayName("Completely incorrect strings can't be parsed")
    void canParseDate_whenIncorrectString_thenFalse() {
        assertThat(new StandardDateParser().canParseDate("sfahsjfd")).isFalse();
    }

    @Test
    @DisplayName("Normal format can be parsed")
    void canParseDate_whenNormalFormat_thenTrue() {
        assertThat(new StandardDateParser().canParseDate("2020-10-10")).isTrue();
    }

    @Test
    @DisplayName("Short format can be parsed")
    void canParseDate_whenShortFormat_thenTrue() {
        assertThat(new StandardDateParser().canParseDate("2020-12-2")).isTrue();
    }

    @Test
    @DisplayName("Incorrect dates can't be parsed")
    void canParseDate_whenIncorrectDate_thenFalse() {
        assertThat(new StandardDateParser().canParseDate("2020-14-10")).isFalse();
    }

    @Test
    @DisplayName("Parsing normal format")
    void parseDate_whenNormalFormat_thenTrue() {
        assertThat(new StandardDateParser().parseDate("2020-10-10"))
            .isEqualTo(LocalDate.of(2020, Month.OCTOBER, 10));
    }

    @Test
    @DisplayName("Parsing short format")
    void parseDate_whenShortFormat_thenTrue() {
        assertThat(new StandardDateParser().parseDate("2020-12-2"))
            .isEqualTo(LocalDate.of(2020, Month.DECEMBER, 2));
    }
}
