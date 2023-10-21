package edu.hw2;

import edu.hw2.task3.connection.ConnectionException;
import edu.hw2.task3.connectionmanagement.FaultyConnectionManager;
import edu.hw2.task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Task3Test {
    @Test
    @DisplayName("Executor throws exception if the limit is exceeded")
    void exceptionExecutor() {
        var connectionManager = new FaultyConnectionManager();
        var executor = new PopularCommandExecutor(connectionManager, 1);
        for (int i = 0; i < 5; i++) {
            executor.tryExecute("command");
        }
        assertThatThrownBy(() -> executor.tryExecute("command")).isInstanceOf(ConnectionException.class)
            .hasCauseInstanceOf(ConnectionException.class);
    }
}
