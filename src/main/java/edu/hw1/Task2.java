package edu.hw1;

public class Task2 {
    public static final int BASE = 10;

    private Task2() {
        throw new IllegalStateException();
    }

    public static int countDigits(double number) {
        double left = number;
        int result = 1;
        while (left > BASE) {
            result++;
            left /= BASE;
        }
        return result;
    }
}
