package edu.project3.streams;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import static java.net.http.HttpClient.newHttpClient;

public class StringStreams {
    private StringStreams() {
        throw new IllegalStateException();
    }

    public static Stream<String> getResponseBodyLineByLineStream(URI uri) {
        try (var client = newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body().lines();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<String> getFileLineByLineStream(Path file) {
        try {
            return Files.lines(file);
        } catch (IOException e) {
            return Stream.of();
        }
    }
}
