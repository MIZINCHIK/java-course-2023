package edu.hw2.task3.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();
    private static int counter = 0;
    private final static int EXCEPTION_TURN = 5;

    @Override
    public void execute(String command) {
        boolean exception = isExceptionTurn();
        updateCounter();
        if (exception) {
            throw new ConnectionException("Execution failed");
        } else {
            LOGGER.info("Command " + command + " executed");
        }
    }

    @Override
    public void close() {
        LOGGER.info("Connection closed");
    }

    private void updateCounter() {
        if (isExceptionTurn()) {
            counter = 0;
        } else {
            counter++;
        }
    }

    private boolean isExceptionTurn() {
        return counter == EXCEPTION_TURN;
    }
}
