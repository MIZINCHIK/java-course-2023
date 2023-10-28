package edu.hw3.task1;

import edu.hw3.task1.alphabet.Alphabet;

public class Atbash {
    private final Alphabet alphabet;

    public Atbash(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String encodeAtbash(String input) {
        return input
            .codePoints()
            .map(alphabet::mirrorSymbol)
            .collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
