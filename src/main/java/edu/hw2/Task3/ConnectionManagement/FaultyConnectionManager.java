package edu.hw2.Task3.ConnectionManagement;

import edu.hw2.Task3.Connection.Connection;
import edu.hw2.Task3.Connection.FaultyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Connection getConnection() {
        LOGGER.info("Faulty connection established");
        return new FaultyConnection();
    }
}
