package edu.project1.view;

import edu.project1.model.State;

public interface HangmanView {
    void showMessage(String message);

    void greet();

    void showRules(String safeWord);

    void showNewWordMask(String wordMask);

    void showCurrentWordMask(String wordMask);

    void showGiveUp();

    void showNoMoreWords();

    void showWin();

    void showDefeat();

    void showAnswer(String answer);

    void showWrongGuess();

    void showCorrectGuess();

    void showMistakesLeft(int turnsLeft);

    void showError();

    void showAllowedMistakesAmount(int maxMistakes);

    void showState(State result);
}
