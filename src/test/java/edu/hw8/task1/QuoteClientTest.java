package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.task1.Utils.generateRandomString;
import static edu.hw8.task1.Utils.getAvailablePort;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuoteClientTest {
    ServerSocketChannel serverSocket;
    Selector selector;
    Map<String, String> quotes;
    ExecutorService pool;

    @Test
    @DisplayName("General test of the quote server")
    void general_whenClientsMakingRequestsConcurrently_thenCorrect() throws IOException {
        quotes = new ConcurrentHashMap<>();
        int port = getAvailablePort();
        start(port);
        for (int i = 0; i < 1000; i++) {
            pool.submit(() -> doClientWork(port));
        }
        selector.wakeup();
        pool.shutdown();
        serverSocket.close();
    }

    void doClientWork(int port) {
        var client = new QuoteClient("localhost", port);
        String key = generateRandomString();
        String value = generateRandomString();
        quotes.put(key, value);
        for (int i = 0; i < 10; i++) {
            assertThat(client.sendMessage(key)).isEqualTo(value);
        }
        client.close();
    }

    private void start(int port) throws IOException {
        pool = Executors.newCachedThreadPool();
        selector = Selector.open();
        pool.submit(() -> startServer(port, selector, pool, quotes));
    }

    private void startServer(int port, Selector selector, ExecutorService pool, Map<String, String> quotes) {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        SocketChannel client = serverSocket.accept();
                        pool.submit(() -> respondToClient(client, quotes));
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void respondToClient(SocketChannel client, Map<String, String> quotes) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        while (true) {
            try {
                int r = client.read(buffer);
                buffer.flip();
                String key = new String(buffer.array(),
                    buffer.arrayOffset() + buffer.position(),
                    buffer.remaining(), StandardCharsets.UTF_8
                );
                if (r == -1) {
                    client.close();
                    break;
                }
                client.write(ByteBuffer
                    .wrap(quotes.getOrDefault(
                        key,
                        ""
                    ).getBytes(StandardCharsets.UTF_8)));
                buffer.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
