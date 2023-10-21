package edu.hw2.task3.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int EXCEPTION_TURN = 5;

    private static int counter = 0;

    @Override
    public void execute(String command) {
        if (isExceptionTurn()) {
            throw new ConnectionException("Execution failed");
        } else {
            LOGGER.info("Command " + command + " executed");
        }
    }

    @Override
    public void close() {
        LOGGER.info("Connection closed");
    }

    private void updateCounter(boolean exceptionTurn) {
        if (exceptionTurn) {
            counter = 0;
        } else {
            counter++;
        }
    }

    private boolean isExceptionTurn() {
        boolean result = counter == EXCEPTION_TURN;
        updateCounter(result);
        return result;
    }
}
