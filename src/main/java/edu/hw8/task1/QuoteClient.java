package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import static edu.hw8.task1.QuoteServer.BUFFER_CAPACITY;
import static edu.hw8.task1.QuoteServer.DEFAULT_CHARSET;

public class QuoteClient implements AutoCloseable {
    private SocketChannel client;

    public QuoteClient(String hostname, int port) {
        try {
            client = SocketChannel.open(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMessage(String message) {
        var buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        buffer.put(message.getBytes(DEFAULT_CHARSET));
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

    @Override
    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
