package edu.hw5;

public class Task5 {
    private static final String ALLOWED_LETTERS = "АВЕКМНОРСТУХ";
    private static final String ALLOWED_REGION_CODES = "([02][1-9]|[13-9][0-9]|10[23]" +
        "|11[1368]|12[1-6]|13[0468]|14[27]|15[0245689]|16[134]|17[23478]|18[016]|19[036-9]" +
        "|2(25|77|99)|7(02|16|25|5[04]|6[13]|7[47]|9[079])|977)";
    private static final String ALLOWED_REGISTRATION_CODES = "((?!000)\\d){3}";
    private static final String PLATE_REQUIREMENTS = "^[" + ALLOWED_LETTERS + "]"
        + ALLOWED_REGISTRATION_CODES + "[" + ALLOWED_LETTERS + "]{2}"
        + ALLOWED_REGION_CODES + "$";

    private Task5() {
        throw new IllegalStateException();
    }

    public static boolean isRussianLicencePlateValid(String plate) {
        return plate.matches(PLATE_REQUIREMENTS);
    }
}
