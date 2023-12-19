package edu.hw8.task1;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Utils {
    public static String generateRandomString() {
        Random random = new Random();
        byte[] array = new byte[random.nextInt(1, 1000)];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    public static int getAvailablePort() throws IOException {
        try(ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
}
