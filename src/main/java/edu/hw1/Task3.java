package edu.hw1;

import java.util.Arrays;

public class Task3 {
    private Task3() {
        throw new IllegalStateException();
    }

    public static boolean isNestable(int[] nested, int[] nest) {
        return Arrays.stream(nested).max().getAsInt() < Arrays.stream(nest).max().getAsInt()
            && Arrays.stream(nested).min().getAsInt() > Arrays.stream(nest).min().getAsInt();
    }
}
