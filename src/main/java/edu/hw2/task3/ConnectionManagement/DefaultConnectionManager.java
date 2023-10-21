package edu.hw2.task3.ConnectionManagement;

import edu.hw2.task3.Connection.Connection;
import edu.hw2.task3.Connection.FaultyConnection;
import edu.hw2.task3.Connection.StableConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static int counter = 0;
    private final static int FAULTY_CONNECTION_TURN = 5;

    @Override
    public Connection getConnection() {
        boolean faulty = isFaultyConnectionTurn();
        updateCounter();
        if (faulty) {
            LOGGER.info("Faulty connection established");
            return new FaultyConnection();
        } else {
            LOGGER.info("Stable connection established");
            return new StableConnection();
        }
    }

    private void updateCounter() {
        if (isFaultyConnectionTurn()) {
            counter = 0;
        } else {
            counter++;
        }
    }

    private boolean isFaultyConnectionTurn() {
        return counter == FAULTY_CONNECTION_TURN;
    }
}
