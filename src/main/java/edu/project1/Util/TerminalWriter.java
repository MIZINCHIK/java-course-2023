package edu.project1.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TerminalWriter {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String GREETINGS = "Hi, Gamer! Let's see what word we got for ya.";
    private final static String RULES = "To guess input a single (sic!) letter."
        + " To give up use a safeword: ";
    private final static String WORD_CHOSEN = "Look at your new word mask";
    private final static String CURRENT_MASK = "Here's your progress";
    private final static String GIVE_UP = "k c u l8er";
    private final static String NO_MORE_WORDS = "You guessed 'em all champ!";
    private final static String WIN = "You did it bro!";
    private final static String DEFEAT = "Better luck next time!";
    private final static String ANSWER = "The word was: ";
    private final static String WRONG_GUESS = "Oopsie-daisy! No such letter!";
    private final static String CORRECT_GUESS = "Otrkoyte bukvu so and so";
    private final static String MISTAKES_LEFT = "You have this many mistakes left: ";
    private final static String ERROR = "Silly developer didn't foresee this";
    private final static String MAX_MISTAKES = "In game you are allowed to make this many mistakes: ";

    private TerminalWriter() {
        throw new IllegalStateException();
    }

    public static void printMessage(String message) {
        LOGGER.info(message);
    }

    public static void greet() {
        printMessage(GREETINGS);
    }

    public static void printRules(String safeWord) {
        printMessage(RULES + safeWord);
    }

    public static void printNewWordMask(String wordMask) {
        printMessage(WORD_CHOSEN);
        printMessage(wordMask);
    }

    public static void printCurrentWordMask(String wordMask) {
        printMessage(CURRENT_MASK);
        printMessage(wordMask);
    }

    public static void printGiveUp() {
        printMessage(GIVE_UP);
    }

    public static void printNoMoreWords() {
        printMessage(NO_MORE_WORDS);
    }

    public static void printWin() {
        printMessage(WIN);
    }

    public static void printDefeat() {
        printMessage(DEFEAT);
    }

    public static void printAnswer(String answer) {
        printMessage(ANSWER + answer);
    }

    public static void printWrongGuess() {
        printMessage(WRONG_GUESS);
    }

    public static void printCorrectGuess() {
        printMessage(CORRECT_GUESS);
    }

    public static void printMistakesLeft(int turnsLeft) {
        printMessage(MISTAKES_LEFT + turnsLeft);
    }

    public static void printError() {
        printMessage(ERROR);
    }

    public static void printAllowedMistakesAmount(int maxMistakes) {
        printMessage(MAX_MISTAKES + maxMistakes);
    }
}
