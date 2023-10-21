package edu.hw2.task3.Connection;

public interface Connection extends AutoCloseable {
    void execute(String command);

    void close();
}
