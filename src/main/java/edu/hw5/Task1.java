package edu.hw5;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Task1 {
    private static final String TIME_FORMAT = "yyyy-MM-dd, HH:mm";
    private static final String TIME_SEPARATOR = " - ";

    private Task1() {
        throw new IllegalStateException();
    }

    public static Duration getAverageSessionTime(List<String> sessions) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        return sessions.stream()
            .map(session -> getSessionDuration(session, dateFormat))
            .reduce(Duration::plus)
            .orElse(Duration.ZERO)
            .dividedBy(sessions.size());
    }

    private static Duration getSessionDuration(String session, SimpleDateFormat dateFormat) {
        String[] dates = session.split(TIME_SEPARATOR);
        if (dates.length != 2) {
            return Duration.ZERO;
        }
        Date startTime = dateFormat.parse(dates[0].trim(), new ParsePosition(0));
        Date endTime = dateFormat.parse(dates[1].trim(), new ParsePosition(0));
        if (startTime == null || endTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(startTime.toInstant(), endTime.toInstant());
    }
}
