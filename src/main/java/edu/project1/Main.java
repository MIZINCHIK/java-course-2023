package edu.project1;

import edu.project1.Dictionary.Dictionary;
import edu.project1.Dictionary.SetDictionary;
import java.util.HashSet;
import java.util.List;

public final class Main {
    private final static int MAX_MISTAKES = 3;

    private Main() {
        throw new IllegalStateException();
    }

    public static void main(String[] args) {
        Dictionary dictionary =
            new SetDictionary(new HashSet<>(List.of("get")));
        Controller game = new Controller(dictionary, MAX_MISTAKES);
        game.runGame();
    }
}
