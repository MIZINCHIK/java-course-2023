package edu.hw3.task1.alphabet;

public class Latin implements Alphabet {
    private static final char LEFT_LOWERCASE_BORDER = 'a';
    private static final char RIGHT_LOWERCASE_BORDER = 'z';
    private static final char LEFT_UPPERCASE_BORDER = 'A';
    private static final char RIGHT_UPPERCASE_BORDER = 'Z';

    @Override
    public boolean inAlphabet(int codepoint) {
        return (codepoint >= LEFT_LOWERCASE_BORDER && codepoint <= RIGHT_LOWERCASE_BORDER)
            || (codepoint >= LEFT_UPPERCASE_BORDER && codepoint <= RIGHT_UPPERCASE_BORDER);
    }

    @Override
    public int mirrorSymbol(int codepoint) {
        if (!inAlphabet(codepoint)) {
            return codepoint;
        }
        if (isLowerCase(codepoint)) {
            return RIGHT_LOWERCASE_BORDER - (codepoint - LEFT_LOWERCASE_BORDER);
        }
        return RIGHT_UPPERCASE_BORDER - (codepoint - LEFT_UPPERCASE_BORDER);
    }

    private boolean isLowerCase(int codepoint) {
        return codepoint >= LEFT_LOWERCASE_BORDER && codepoint <= RIGHT_LOWERCASE_BORDER;
    }
}
