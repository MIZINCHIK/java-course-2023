package edu.hw1;

public class Task1 {
    public static long minutesToSeconds(String mmSS) {
        String[] separated = mmSS.split(":");
        if (separated.length != 2 || separated[1].length() != 2) {
            return -1;
        }
        long seconds = Long.parseLong(separated[1]);
        if (seconds >= 60 || seconds < 0) {
            return -1;
        }
        long minutes = Long.parseLong(separated[0]);
        boolean negative = minutes < 0;
        seconds += Math.abs(minutes) * 60;
        return negative ? -seconds : seconds;
    }
}
