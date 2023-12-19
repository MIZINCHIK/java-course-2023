package edu.hw8.task3;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.task3.PasswordBruteForce.bruteForcePasswordMultiThread;
import static edu.hw8.task3.PasswordBruteForce.bruteForcePasswordSingleThread;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordBruteForceTest {
    private final CharSequence alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    @DisplayName("Calculating Fibonacci numbers in a single thread")
    void bruteForcePasswordSingleThread_whenShortPasswords_thenCorrect() {
        Map<String, String> users = new HashMap<>();
        users.put("202cb962ac59075b964b07152d234b70", "1");
        users.put("25aa3ee1c93cad3f274567281066dc18", "2");
        users.put("1cd450a7ac32f4b9a2e3dbc52ab97f72", "3");
        users.put("215f643ee4511c7fb8516e800c050237", "4");
        users.put("81dc9bdb52d04dc20036dbd8313ed055", "5");
        Map<String, String> expected = new HashMap<>();
        expected.put("1", "123");
        expected.put("2", "AbC");
        expected.put("3", "tgf");
        expected.put("4", "0Pq");
        expected.put("5", "1234");
        assertThat(bruteForcePasswordSingleThread(users, alphabet)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Calculating Fibonacci numbers in multiple threads")
    void bruteForcePasswordMultiThread_whenShortPasswords_thenCorrect() {
        Map<String, String> users = new HashMap<>();
        users.put("81dc9bdb52d04dc20036dbd8313ed055", "1");
        users.put("4a9d3f74f99bdae9d4b577e792d8fd61", "2");
        users.put("163d52d19b64c18c4b04bc5d70c71b22", "3");
        users.put("337622638a11e72728c7f97ae45dc205", "4");
        Map<String, String> expected = new HashMap<>();
        expected.put("1", "1234");
        expected.put("2", "AbC4");
        expected.put("3", "tgf4");
        expected.put("4", "0Pq4");
        assertThat(bruteForcePasswordMultiThread(users, alphabet, 1)).isEqualTo(expected);
        assertThat(bruteForcePasswordMultiThread(users, alphabet, 2)).isEqualTo(expected);
        assertThat(bruteForcePasswordMultiThread(users, alphabet, 4)).isEqualTo(expected);
        assertThat(bruteForcePasswordMultiThread(users, alphabet, 6)).isEqualTo(expected);
        assertThat(bruteForcePasswordMultiThread(users, alphabet, 8)).isEqualTo(expected);
    }
}
