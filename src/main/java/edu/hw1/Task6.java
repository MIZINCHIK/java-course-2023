package edu.hw1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static edu.hw1.Task2.BASE;

public class Task6 {
    private static final int UPPER_LIMIT = 9999;
    private static final int LOWER_LIMIT = 1000;
    private static final int KAPREKAR = 6174;

    private Task6() {
        throw new IllegalStateException();
    }

    public static int countK(int number) {
        if (number == KAPREKAR) {
            return 0;
        }
        return countK(sortDigits(number, false) - sortDigits(number, true)) + 1;
    }

    private static int sortDigits(int number, boolean ascending) {
        List<Integer> digits = new ArrayList<>();
        int initial = number;
        while (initial > 0) {
            digits.add(initial % BASE);
            initial /= BASE;
        }
        while (digits.size() < 4) {
            digits.add(0);
        }
        if (ascending) {
            Collections.sort(digits);
        } else {
            digits.sort(Collections.reverseOrder());
        }
        int result = 0;
        for (int digit : digits) {
            result *= BASE;
            result += digit;
        }
        return result;
    }
}
