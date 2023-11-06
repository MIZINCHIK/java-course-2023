package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import static edu.hw5.Task1.getAverageSessionTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("When session is of incorrect format its duration is considered to be equal to zero")
    void getAverageSessionTime_whenIncorrectFormat_thenZero() {
        List<String> sessions = new ArrayList<>();
        sessions.add("safdsfdsfsdf");
        assertThat(getAverageSessionTime(sessions)).isEqualTo(Duration.ZERO);
    }

    @Test
    @DisplayName("Spaces between dates don't affect session parsing")
    void getAverageSessionTime_whenRedundantSpaces_thenUsualParsing() {
        List<String> sessions = new ArrayList<>();
        sessions.add("     2022-03-12, 20:20     -  2022-03-12, 23:50   ");
        Duration expected = Duration.of(210, ChronoUnit.MINUTES);
        Duration real = getAverageSessionTime(sessions);
        assertThat(real).isEqualTo(expected);
    }

    @Test
    @DisplayName("getAverageSessionTime() return the average session time of correctly provided session intervals")
    void getAverageSessionTime_whenListCorrectFormat_thenAverageSessionTime() {
        List<String> sessions = new ArrayList<>();
        sessions.add("     2022-03-12, 20:20     -  2022-03-12, 23:50   ");
        sessions.add("2022-04-01, 21:30 - 2022-04-02, 01:20");
        Duration expected = Duration.of(220, ChronoUnit.MINUTES);
        Duration real = getAverageSessionTime(sessions);
        assertThat(real).isEqualTo(expected);
    }
}
