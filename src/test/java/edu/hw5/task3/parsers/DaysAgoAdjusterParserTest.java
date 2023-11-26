package edu.hw5.task3.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DaysAgoAdjusterParserTest {
    @Test
    @DisplayName("Completely incorrect strings can't be parsed")
    void canParseDate_whenIncorrectString_thenFalse() {
        assertThat(new DaysAgoAdjusterParser().canParseDate("sfahsjfd")).isFalse();
    }

    @Test
    @DisplayName("1 day ago can be parsed")
    void canParseDate_when1DayAgo_thenTrue() {
        assertThat(new DaysAgoAdjusterParser().canParseDate("1 day ago")).isTrue();
    }

    @Test
    @DisplayName("Positive days ago can be parsed")
    void canParseDate_whenPositiveDaysAgo_thenTrue() {
        assertThat(new DaysAgoAdjusterParser().canParseDate("5 days ago")).isTrue();
    }

    @Test
    @DisplayName("Negative days ago can't be parsed")
    void canParseDate_whenNegativeDaysAgo_thenFalse() {
        assertThat(new DaysAgoAdjusterParser().canParseDate("-10 days ago")).isFalse();
    }

    @Test
    @DisplayName("Zero days ago can't be parsed")
    void canParseDate_whenZeroDaysAgo_thenFalse() {
        assertThat(new DaysAgoAdjusterParser().canParseDate("0 days ago")).isFalse();
    }

    @Test
    @DisplayName("Parsing 1 day ago")
    void parseDate_when1DayAgo_thenYesterday() {
        assertThat(new DaysAgoAdjusterParser().parseDate("1 days ago"))
            .isEqualTo(LocalDate.now().minusDays(1));
    }

    @Test
    @DisplayName("Parsing 5 days ago")
    void parseDate_when5DayAgo_then5DayAgo() {
        assertThat(new DaysAgoAdjusterParser().parseDate("5 days ago"))
            .isEqualTo(LocalDate.now().minusDays(5));
    }
}
