package edu.hw1;

public class Task1 {
    private static final int MAX_SECONDS = 59;
    private static final int MIN_SECONDS = 0;
    private static final int SECONDS_IN_MINUTE = 60;

    private Task1() {
        throw new IllegalStateException();
    }

    public static long minutesToSeconds(String mmSS) {
        String[] separated = mmSS.split(":");
        if (separated.length != 2 || separated[1].length() != 2) {
            return -1;
        }
        long seconds = Long.parseLong(separated[1]);
        if (seconds > MAX_SECONDS || seconds < MIN_SECONDS) {
            return -1;
        }
        long minutes = Long.parseLong(separated[0]);
        boolean negative = minutes < 0;
        seconds += Math.abs(minutes) * SECONDS_IN_MINUTE;
        return negative ? -seconds : seconds;
    }
}
