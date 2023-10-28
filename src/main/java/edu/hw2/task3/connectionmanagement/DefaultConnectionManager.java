package edu.hw2.task3.connectionmanagement;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Random random = new Random();

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

    private boolean isFaultyConnectionTurn() {
        return random.nextBoolean();
    }
}
