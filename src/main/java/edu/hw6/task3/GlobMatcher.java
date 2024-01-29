package edu.hw6.task3;

public class GlobMatcher {
    private GlobMatcher() {
        throw new IllegalStateException();
    }

    public static AbstractFilter globMatches(String glob) {
        return entry -> entry != null
            && entry.getFileSystem().getPathMatcher("glob:" + glob).matches(entry.getFileName());
    }
}
