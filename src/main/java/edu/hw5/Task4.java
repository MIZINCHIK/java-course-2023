package edu.hw5;

public class Task4 {
    private static final String PASSWORD_REQUIREMENTS = "^.*[~!@#\\$%\\^&\\*\\|].*$";

    private Task4() {
        throw new IllegalStateException();
    }

    public static boolean isPasswordCorrect(String password) {
        return password.matches(PASSWORD_REQUIREMENTS);
    }
}
