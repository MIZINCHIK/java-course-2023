package edu.hw6.task3;

public class RegexMatcher {
    private RegexMatcher() {
        throw new IllegalStateException();
    }

    public static AbstractFilter regexContains(String regex) {
        return entry -> entry != null && entry.getFileName().toString().matches(regex);
    }
}
