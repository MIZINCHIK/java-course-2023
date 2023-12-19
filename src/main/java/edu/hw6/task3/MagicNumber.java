package edu.hw6.task3;

import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.Arrays;

public class MagicNumber {
    private MagicNumber() {
        throw new IllegalStateException();
    }

    public static AbstractFilter magicNumber(byte... firstBytes) {
        return entry -> {
            if (!Files.isRegularFile(entry) || Files.size(entry) < firstBytes.length) {
                return false;
            }
            byte[] firstBytesInFile = new byte[firstBytes.length];
            try (RandomAccessFile file = new RandomAccessFile(entry.toFile(), "r")) {
                file.readFully(firstBytesInFile);
            }
            return Arrays.equals(firstBytes, firstBytesInFile);
        };
    }
}
