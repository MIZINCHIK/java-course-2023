package edu.project1.model;

import edu.project1.dictionary.Dictionary;
import org.jetbrains.annotations.NotNull;

public class Model {
    private final Dictionary dictionary;
    private final int maxMistakes;
    private int mistakes;
    private char[] currentWord;
    private char[] userWordMask;
    private char[] modelWordMask;
    private static final int MAX_WORD_LENGTH = 15;
    private static final int MIN_WORD_LENGTH = 3;
    private static final String CORRECT_REGEX = "[a-z]{" + MIN_WORD_LENGTH + "," + MAX_WORD_LENGTH + "}";

    public Model(@NotNull Dictionary dictionary, int maxMistakes) {
        if (maxMistakes < 0) {
            throw new IllegalArgumentException();
        }
        this.dictionary = dictionary;
        this.maxMistakes = maxMistakes;
    }

    @NotNull
    public State guess(char input) {
        boolean correctGuess = false;
        boolean lettersLeft = false;
        for (int i = 0; i < currentWord.length; i++) {
            if (modelWordMask[i] == input) {
                correctGuess = true;
                userWordMask[i] = input;
                modelWordMask[i] = '*';
            } else if (userWordMask[i] == input) {
                correctGuess = true;
            } else if (modelWordMask[i] != '*') {
                lettersLeft = true;
            }
        }
        if (!correctGuess) {
            mistakes++;
        }
        State result = new State(new String(userWordMask), correctGuess
            ? (lettersLeft ? GuessResult.GUESSED : GuessResult.WIN)
            : ((getMistakesLeft() > 0) ? GuessResult.FAILED : GuessResult.DEFEAT)
        );
        if (result.result() == GuessResult.WIN) {
            dictionary.removeWord(result.wordMask());
        }
        return result;
    }

    public boolean startNewGame() {
        mistakes = 0;
        String currentWordCandidate;
        do {
            currentWordCandidate = dictionary.getWord();
            if (currentWordCandidate == null) {
                return false;
            }
            if (!currentWordCandidate.matches(CORRECT_REGEX)) {
                dictionary.removeWord(currentWordCandidate);
            }
        } while (!currentWordCandidate.matches(CORRECT_REGEX));
        currentWord = currentWordCandidate.toCharArray();
        modelWordMask = currentWordCandidate.toCharArray();
        userWordMask = "*".repeat(currentWord.length).toCharArray();
        return true;
    }

    public String getUserWordMask() {
        return new String(userWordMask);
    }

    public String getCurrentWord() {
        return new String(currentWord);
    }

    public int getMistakesLeft() {
        return maxMistakes - mistakes;
    }
}
