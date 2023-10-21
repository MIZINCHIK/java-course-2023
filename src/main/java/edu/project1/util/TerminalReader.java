package edu.project1.util;

import java.util.Scanner;

public class TerminalReader {
    private static Scanner scanner = new Scanner(System.in);

    private TerminalReader() {
        throw new IllegalStateException();
    }

    public static String readMessage() {
        return scanner.nextLine();
    }
}
