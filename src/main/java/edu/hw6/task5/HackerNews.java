package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.net.http.HttpClient.newHttpClient;

public class HackerNews {
    private static final URI PREFIX = URI.create("https://hacker-news.firebaseio.com/v0/");
    private static final URI TOP_STORIES = PREFIX.resolve("topstories.json");
    private static final String ITEM = "item/";
    private static final String JSON = ".json";
    private static final Pattern PATTERN = Pattern.compile(".*\"title\":\"([^\"]*)\".*");
    private static final int MAX_WAIT_SECONDS = 10;

    private HackerNews() {
        throw new IllegalStateException();
    }

    public static long[] hackerNewsTopStories() {
        HttpRequest request = buildRequest(TOP_STORIES);
        try (var client = newHttpClient()) {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return convertStringToArray(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String news(long id) {
        URI news = PREFIX.resolve(ITEM).resolve(id + JSON);
        HttpRequest request = buildRequest(news);
        try (var client = newHttpClient()) {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Matcher matcher = PATTERN.matcher(response.body());
            if (matcher.find()) {
                return matcher.group(1);
            }
            return "";
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static long[] convertStringToArray(String longArray) {
        String[] splitted = longArray.substring(1, longArray.length() - 1).split(",");
        int arraySize = splitted.length;
        long[] result = new long[arraySize];
        for (int i = 0; i < arraySize; i++) {
            result[i] = Long.parseLong(splitted[i]);
        }
        return result;
    }

    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .timeout(Duration.of(MAX_WAIT_SECONDS, ChronoUnit.SECONDS))
            .build();
    }
}
