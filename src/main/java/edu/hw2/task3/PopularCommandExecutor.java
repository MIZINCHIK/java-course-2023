package edu.hw2.task3;

import edu.hw2.task3.connection.ConnectionException;
import edu.hw2.task3.connectionmanagement.ConnectionManager;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    public void tryExecute(String command) {
        for (int i = 0; i < maxAttempts; i++) {
            try (var connection = manager.getConnection();) {
                connection.execute(command);
                return;
            } catch (ConnectionException e) {
                if (i == maxAttempts - 1) {
                    throw new ConnectionException("Execution attempts limit exceeded", e);
                }
            }
        }
    }
}
