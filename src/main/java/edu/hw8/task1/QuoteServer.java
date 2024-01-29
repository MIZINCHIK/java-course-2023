package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuoteServer implements AutoCloseable {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String NO_SUCH_QUOTE = "No such quote";
    public static final String STOP_WORD = "Ate him alive";
    public static final String SERVER_HOST = "localhost";
    public static final int BUFFER_CAPACITY = 2048;
    private final Map<String, String> quotes;
    private final ExecutorService threadPool;
    private Selector selector;
    private ServerSocketChannel serverSocket;

    public QuoteServer(Map<String, String> quotes) {
        this.quotes = new ConcurrentHashMap<>(quotes);
        threadPool = Executors.newCachedThreadPool();
    }

    public QuoteServer() {
        this(new HashMap<>());
    }

    public void addQuote(String key, String quote) {
        quotes.put(key, quote);
    }

    public void start(int port) {
        Executors.newCachedThreadPool();
        threadPool.submit(() -> startServer(port));
    }

    private void startServer(int port) {
        try {
            serverSocket = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocket.bind(new InetSocketAddress(SERVER_HOST, port));
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
                        threadPool.submit(() -> respondToClient(client));
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void respondToClient(SocketChannel client) {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        while (true) {
            try {
                int r = client.read(buffer);
                buffer.flip();
                String key = new String(buffer.array(),
                    buffer.arrayOffset() + buffer.position(),
                    buffer.remaining(), DEFAULT_CHARSET
                );
                if (r == -1 || key.equals(STOP_WORD)) {
                    client.close();
                    break;
                }
                client.write(ByteBuffer
                    .wrap(quotes.getOrDefault(
                        key,
                        NO_SUCH_QUOTE
                    ).getBytes(DEFAULT_CHARSET)));
                buffer.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        selector.wakeup();
        selector.close();
        serverSocket.close();
        threadPool.shutdown();
    }
}
