package edu.hw1;

import java.util.Arrays;

public class Task3 {
    private Task3() {
        throw new IllegalStateException();
    }

    public static boolean isNestable(int[] nested, int[] nest) {
        if (nest == null || nest.length == 0) {
            return false;
        }
        if (nested == null || nested.length == 0) {
            return true;
        }
        return Arrays.stream(nested).max().getAsInt() < Arrays.stream(nest).max().getAsInt()
            && Arrays.stream(nested).min().getAsInt() > Arrays.stream(nest).min().getAsInt();
    }
}
