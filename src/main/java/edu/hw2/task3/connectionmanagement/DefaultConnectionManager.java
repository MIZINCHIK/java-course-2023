package edu.hw2.task3.connectionmanagement;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int FAULTY_CONNECTION_TURN = 5;

    private int counter = 0;

    @Override
    public Connection getConnection() {
        if (isFaultyConnectionTurn()) {
            LOGGER.info("Faulty connection established");
            return new FaultyConnection();
        } else {
            LOGGER.info("Stable connection established");
            return new StableConnection();
        }
    }

    private void updateCounter(boolean faultyConnectionTurn) {
        if (faultyConnectionTurn) {
            counter = 0;
        } else {
            counter++;
        }
    }

    private boolean isFaultyConnectionTurn() {
        boolean result = counter == FAULTY_CONNECTION_TURN;
        updateCounter(result);
        return result;
    }
}
