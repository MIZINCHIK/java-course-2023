package edu.hw5;

public class Task6 {
    private Task6() {
        throw new IllegalStateException();
    }

    public static boolean isSubSequence(String candidate, String largerString) {
        return largerString.matches(getApplicableRegex(candidate));
    }

    private static String getApplicableRegex(String candidate) {
        return "^.*" + candidate.replaceAll(".", "$0.*") + "$";
    }
}
