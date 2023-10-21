package edu.hw2.task3.Connection;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String message, Exception cause) {
        super(message, cause);
    }

    public ConnectionException(String message) {
        super(message);
    }
}
