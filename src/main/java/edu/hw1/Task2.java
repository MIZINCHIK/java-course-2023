package edu.hw1;

public class Task2 {
    private Task2() {
        throw new IllegalStateException();
    }

    public static int countDigits(double number) {
        int result = 1;
        while (number > 10) {
            result++;
            number /= 10;
        }
        return result;
    }
}
