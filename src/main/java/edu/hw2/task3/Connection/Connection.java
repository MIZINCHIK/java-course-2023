package edu.hw2.Task3.Connection;

public interface Connection extends AutoCloseable {
    void execute(String command);

    void close();
}
