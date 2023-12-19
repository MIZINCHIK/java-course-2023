package edu.hw6.task1;

public class KeyValueConsistencyException extends RuntimeException {
    public KeyValueConsistencyException(String message, Exception cause) {
        super(message, cause);
    }

    public KeyValueConsistencyException(String message) {
        super(message);
    }
}
