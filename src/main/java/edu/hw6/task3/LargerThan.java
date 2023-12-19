package edu.hw6.task3;

import java.nio.file.Files;

public class LargerThan {
    private LargerThan() {
        throw new IllegalStateException();
    }

    public static AbstractFilter largerThan(long bytesAmount) {
        return entry -> entry != null && Files.size(entry) > bytesAmount;
    }
}
