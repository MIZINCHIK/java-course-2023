package edu.hw1;

public class Task4 {
    private Task4() {
        throw new IllegalStateException();
    }


    public static String fixString(String broken) {
        char[] characters = broken.toCharArray();
        int strLen = characters.length;
        for (int i = 1; i < strLen; i += 2) {
            char tmp = characters[i];
            characters[i] = characters[i - 1];
            characters[i - 1] = tmp;
        }
        return new String(characters);
    }
}
