package edu.hw5;

public class Task7 {
    private static final String THREE_OR_MORE = "^[01][01]0[01]*$";
    private static final String ENDS_AS_STARTS = "^(([01])[01]*\\2||[01])$";
    private static final String LENGTH_1_TO_3 = "^[01]{1,3}$";

    private Task7() {
        throw new IllegalStateException();
    }

    public static boolean threeCharactersOrMore(String string) {
        return string.matches(THREE_OR_MORE);
    }

    public static boolean endsAsStarts(String string) {
        return string.matches(ENDS_AS_STARTS);
    }

    public static boolean hasLength1To3(String string) {
        return string.matches(LENGTH_1_TO_3);
    }
}
