package edu.hw1;

public class Task2 {
    private Task2() {
        throw new IllegalStateException();
    }

    public static int countDigits(long number) {
        int result = 0;
        while (number > 0) {
            result++;
            number /= 10;
        }
        return result;
    }
}
