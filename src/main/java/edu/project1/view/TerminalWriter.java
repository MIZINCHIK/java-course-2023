package edu.project1.view;

import edu.project1.model.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TerminalWriter implements HangmanView {
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

    public void showMessage(String message) {
        LOGGER.info(message);
    }

    public void greet() {
        showMessage(GREETINGS);
    }

    public void showRules(String safeWord) {
        showMessage(RULES + safeWord);
    }

    public void showNewWordMask(String wordMask) {
        showMessage(WORD_CHOSEN);
        showMessage(wordMask);
    }

    public void showCurrentWordMask(String wordMask) {
        showMessage(CURRENT_MASK);
        showMessage(wordMask);
    }

    public void showGiveUp() {
        showMessage(GIVE_UP);
    }

    public void showNoMoreWords() {
        showMessage(NO_MORE_WORDS);
    }

    public void showWin() {
        showMessage(WIN);
    }

    public void showDefeat() {
        showMessage(DEFEAT);
    }

    public void showAnswer(String answer) {
        showMessage(ANSWER + answer);
    }

    public void showWrongGuess() {
        showMessage(WRONG_GUESS);
    }

    public void showCorrectGuess() {
        showMessage(CORRECT_GUESS);
    }

    public void showMistakesLeft(int turnsLeft) {
        showMessage(MISTAKES_LEFT + turnsLeft);
    }

    public void showError() {
        showMessage(ERROR);
    }

    public void showAllowedMistakesAmount(int maxMistakes) {
        showMessage(MAX_MISTAKES + maxMistakes);
    }

    @Override
    public void showState(State result) {
        switch (result.result()) {
            case WIN -> showWin();
            case DEFEAT -> showDefeat();
            case FAILED -> showWrongGuess();
            case GUESSED -> showCorrectGuess();
            default -> showError();
        }
        switch (result.result()) {
            case WIN, DEFEAT -> showAnswer(result.currentWord());
            case FAILED, GUESSED -> {
                showCurrentWordMask(result.wordMask());
                showMistakesLeft(result.maxMistakesLeft());
            }
            default -> showError();
        }
    }
}
