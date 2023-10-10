package edu.hw1;

public class Task2 {
    public static final int BASE = 10;

    private Task2() {
        throw new IllegalStateException();
    }

    public static int countDigits(long number) {
        if (number == 0) {
            return 1;
        }
        long left = number;
        int result = 0;
        while (left != 0) {
            result++;
            left /= BASE;
        }
        return result;
    }
}
