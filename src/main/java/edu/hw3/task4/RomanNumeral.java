package edu.hw3.task4;

public class RomanNumeral {
    private static final RomanNumeralLetter[] LETTERS = RomanNumeralLetter.values();
    private static final int RIGHT_BOUND_INCLUSIVE = 3999;
    private static final int LEFT_BOUND_INCLUSIVE = 1;

    private RomanNumeral() {
        throw new IllegalStateException();
    }

    public static String convertToRoman(int arabicNumeral) {
        if (arabicNumeral > RIGHT_BOUND_INCLUSIVE || arabicNumeral < LEFT_BOUND_INCLUSIVE) {
            return null;
        }
        int rest = arabicNumeral;
        StringBuilder romanBuilder = new StringBuilder();
        for (RomanNumeralLetter letter : LETTERS) {
            int currentNumeral = letter.getValue();
            while (rest >= currentNumeral) {
                romanBuilder.append(letter.name());
                rest -= currentNumeral;
            }
            if (rest == 0) {
                break;
            }
        }
        return romanBuilder.toString();
    }
}
