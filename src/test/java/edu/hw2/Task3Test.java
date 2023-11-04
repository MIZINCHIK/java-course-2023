package edu.hw2;

import edu.hw2.task3.PopularCommandExecutor;
import edu.hw2.task3.connection.ConnectionException;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connectionmanagement.DefaultConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Task3Test {
    @Test
    @DisplayName("Executor throws ConnectionException when the faulty connection exceeds maxAttempts")
    void connectionException() {
        var manager = Mockito.mock(DefaultConnectionManager.class);
        var connection = Mockito.mock(FaultyConnection.class);
        var executor = new PopularCommandExecutor(manager, 1);
        String command = "Command";
        Mockito.when(manager.getConnection()).thenReturn(connection);
        Mockito.doThrow(new ConnectionException("Execution failed")).when(connection).execute(command);
        assertThatThrownBy(() -> executor.tryExecute(command))
            .isInstanceOf(ConnectionException.class)
            .message()
            .isEqualTo("Execution attempts limit exceeded");
    }
}
