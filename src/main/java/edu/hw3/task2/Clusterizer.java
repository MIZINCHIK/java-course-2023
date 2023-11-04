package edu.hw3.task2;

import java.util.ArrayList;
import java.util.List;

public class Clusterizer {
    private static final String CORRECT_REGEX = "[\\(\\)]*";

    private Clusterizer() {
        throw new IllegalStateException();
    }

    public static List<String> clusterize(String braces) {
        if (!isCorrectInput(braces)) {
            throw new NullPointerException("No string of braces provided!");
        }
        List<String> result = new ArrayList<>();
        int stack = 0;
        char[] bracesCharacters = braces.toCharArray();
        StringBuilder balancedClusterBuilder = new StringBuilder();
        for (char character : bracesCharacters) {
            balancedClusterBuilder.append(character);
            if (character == '(') {
                stack++;
            } else if (character == ')') {
                switch (stack) {
                    case 1 -> {
                        result.add(balancedClusterBuilder.toString());
                        balancedClusterBuilder.setLength(0);
                        stack--;
                    }
                    case 0 -> {
                        return null;
                    }
                    default -> stack--;
                }
            }
        }
        if (balancedClusterBuilder.isEmpty()) {
            return result;
        }
        return null;
    }

    private static boolean isCorrectInput(String input) {
        return input.matches(CORRECT_REGEX);
    }
}
