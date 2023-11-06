package edu.hw5.task3.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NaturalAdjusterParserTest {
    @Test
    @DisplayName("Completely incorrect strings can't be parsed")
    void canParseDate_whenIncorrectString_thenFalse() {
        assertThat(new NaturalAdjusterParser().canParseDate("sfahsjfd")).isFalse();
    }

    @Test
    @DisplayName("yesterday can be parsed")
    void canParseDate_whenYesterday_thenTrue() {
        assertThat(new NaturalAdjusterParser().canParseDate("yesterday")).isTrue();
    }

    @Test
    @DisplayName("today can be parsed")
    void canParseDate_whenToday_thenTrue() {
        assertThat(new NaturalAdjusterParser().canParseDate("today")).isTrue();
    }

    @Test
    @DisplayName("tomorrow can be parsed")
    void canParseDate_whenTomorrow_thenTrue() {
        assertThat(new NaturalAdjusterParser().canParseDate("tomorrow")).isTrue();
    }

    @Test
    @DisplayName("Parsing yesterday")
    void parseDate_whenYesterday_thenYesterday() {
        assertThat(new NaturalAdjusterParser().parseDate("yesterday"))
            .isEqualTo(LocalDate.now().minusDays(1));
    }

    @Test
    @DisplayName("Parsing today")
    void parseDate_whenToday_thenToday() {
        assertThat(new NaturalAdjusterParser().parseDate("today"))
            .isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Parsing tomorrow")
    void parseDate_whenTomorrow_thenTomorrow() {
        assertThat(new NaturalAdjusterParser().parseDate("tomorrow"))
            .isEqualTo(LocalDate.now().plusDays(1));
    }
}
