package edu.hw1;

public class Task1 {
    private static final int MAX_SECONDS = 59;
    private static final int MIN_SECONDS = 0;
    private static final int SECONDS_IN_MINUTE = 60;

    private Task1() {
        throw new IllegalStateException();
    }

    public static long minutesToSeconds(String mmSS) {
        if (!mmSS.matches("-?[0-9]*:[0-9]{2}")) {
            return -1;
        }
        String[] separated = mmSS.split(":");
        long seconds = Long.parseLong(separated[1]);
        long minutes;
        try {
            minutes = Long.parseLong(separated[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
        boolean negative = minutes < 0;
        long edgeValue = negative ? Long.MIN_VALUE : Long.MAX_VALUE;
        if (seconds > MAX_SECONDS || seconds < MIN_SECONDS
            || (minutes != 0 && (edgeValue / minutes < SECONDS_IN_MINUTE
            || Math.abs(edgeValue - minutes * SECONDS_IN_MINUTE) < seconds))) {
            return -1;
        }
        seconds = -seconds - Math.abs(minutes) * SECONDS_IN_MINUTE;
        return negative ? seconds : -seconds;
    }
}
