package edu.hw1;

import org.apache.logging.log4j.LogManager;

public class Task0 {
    private Task0() {
        throw new IllegalStateException();
    }

    public static void helloWorld() {
        LogManager.getLogger().info("Привет, мир!");
    }
}
