package edu.hw2.task3.connection;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Random random = new Random();

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

    private boolean isExceptionTurn() {
        return random.nextBoolean();
    }
}
