package edu.hw8.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordBruteForce {
    private PasswordBruteForce() {
        throw new IllegalStateException();
    }

    public static Map<String, String> bruteForcePasswordMultiThread(
        Map<String, String> users,
        CharSequence alphabet,
        int numberOfThreads
    ) {
        Map<String, String> concurrentUsers = new ConcurrentHashMap<>(users);
        Map<String, String> result = new ConcurrentHashMap<>();
        try (ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int i = 0; i < numberOfThreads; i++) {
                int index = i;
                CompletableFuture.runAsync(
                    () -> result.putAll(bruteForcePasswordSingleThreadStep(concurrentUsers, alphabet, numberOfThreads, index)),
                    pool
                );
            }
        }
        return result;
    }

    public static Map<String, String> bruteForcePasswordSingleThread(Map<String, String> users, CharSequence alphabet) {
        return bruteForcePasswordSingleThreadStep(users, alphabet, 1, 0);
    }

    private static Map<String, String> bruteForcePasswordSingleThreadStep(
        Map<String, String> users,
        CharSequence alphabet,
        int step,
        int start
    ) {
        Map<String, String> result = new HashMap<>();
        long index = start;
        Set<String> keySet = users.keySet();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while (!users.isEmpty()) {
                String nextPassword = getPasswordByNumber(index, alphabet);
                index += step;
                md5.update(nextPassword.getBytes());
                BigInteger auxiliaryNumber = new BigInteger(1, md5.digest());
                String hash = auxiliaryNumber.toString(16);
                if (keySet.contains(hash)) {
                    result.put(users.get(hash), nextPassword);
                    users.remove(hash);
                }
                md5.reset();
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPasswordByNumber(long number, CharSequence alphabet) {
        int base = alphabet.length();
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            result.append(alphabet.charAt((int) (number % base)));
            number /= base;
        }
        return result.toString();
    }

}
