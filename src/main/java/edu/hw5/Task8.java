package edu.hw5;

public class Task8 {
    private static final String ODD_LENGTH = "^([01][01])*[01]$";
    private static final String ZERO_ODD_ONE_EVEN_LENGTH = "^(0([01][01])*|1([01][01])*[01])$";
    private static final String ZEROES_3 = "^(1*(01*01*0)*)*$";
    private static final String NOT_11_111 = "^(?!11$|111$)[01]*$";
    private static final String ODD_1 = "^(|1([01]1)*[01]?)$";
    private static final String MIN2_0_MAX1_1 = "^(1?00+|01?0+|00+1?0*)$";
    private static final String NO_CONSECUTIVE_1 = "^((?!11)[01])*$";


    private Task8() {
        throw new IllegalStateException();
    }

    public static boolean isOddLength(String string) {
        return string.matches(ODD_LENGTH);
    }

    public static boolean startsZeroOddOneEvenLength(String string) {
        return string.matches(ZERO_ODD_ONE_EVEN_LENGTH);
    }

    public static boolean amountZeroesMultiple3(String string) {
        return string.matches(ZEROES_3);
    }

    public static boolean not11Or111(String string) {
        return string.matches(NOT_11_111);
    }

    public static boolean eachOddEqual1(String string) {
        return string.matches(ODD_1);
    }

    public static boolean containsMinimum2ZeroesMaximum1One(String string) {
        return string.matches(MIN2_0_MAX1_1);
    }

    public static boolean noConsecutiveOnes(String string) {
        return string.matches(NO_CONSECUTIVE_1);
    }
}
