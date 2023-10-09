package edu.hw1;

public class Task5 {
    private Task5() {
        throw new IllegalStateException();
    }

    public static boolean isSpecialPalindrome(long number) {
        String numberAsString = Long.toString(number);
        return isPalindromeItself(numberAsString) || hasPalindromeDescendant(numberAsString);
    }

    private static boolean hasPalindromeDescendant(String number) {
        int numLen = number.length();
        if (numLen < 2) {
            return false;
        }
        boolean itself = isPalindromeItself(number);
        if (numLen % 2 != 0) {
            return itself;
        }
        return itself || hasPalindromeDescendant(getDescendant(number));
    }

    private static boolean isPalindromeItself(String number) {
        return number.contentEquals(new StringBuilder(number).reverse());
    }

    private static String getDescendant(String number) {
        char[] characters = number.toCharArray();
        int numLen = characters.length;
        if (numLen % 2 != 0) {
            throw new IllegalArgumentException("Odd number of chars not supported!");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < numLen; i += 2) {
            builder.append(characters[i] - '0' + characters[i - 1] - '0');
        }
        return builder.toString();
    }
}
