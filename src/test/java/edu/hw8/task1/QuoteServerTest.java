package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.task1.QuoteServer.BUFFER_CAPACITY;
import static edu.hw8.task1.QuoteServer.DEFAULT_CHARSET;
import static edu.hw8.task1.QuoteServer.SERVER_HOST;
import static edu.hw8.task1.Utils.generateRandomString;
import static edu.hw8.task1.Utils.getAvailablePort;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuoteServerTest {
    @Test
    @DisplayName("General test of the quote server")
    void general_whenServerRunning_thenResponsesGot() {
        try(var server = new QuoteServer()) {
            Map<String, String> quotes = new HashMap<>();
            for (int i = 0; i < 1000; i++) {
                var key = generateRandomString();
                var value = generateRandomString();
                quotes.put(key, value);
                server.addQuote(key, value);
            }
            int port = getAvailablePort();
            server.start(port);
            var client = SocketChannel.open(new InetSocketAddress(SERVER_HOST, port));
            for (var entry : quotes.entrySet()) {
                assertThat(sendRequest(client, entry.getKey())).isEqualTo(entry.getValue());
            }
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String sendRequest(SocketChannel client, String key) {
        var buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        buffer.put(key.getBytes(DEFAULT_CHARSET));
        buffer.flip();
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buffer.flip();
        String response = new String(buffer.array(),
            buffer.arrayOffset() + buffer.position(),
            buffer.remaining());
        buffer.clear();
        return response;
    }
}
