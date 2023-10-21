package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.model.Model;
import edu.project1.model.State;
import edu.project1.view.HangmanView;
import edu.project1.view.TerminalWriter;
import static edu.project1.model.GuessResult.DEFEAT;
import static edu.project1.model.GuessResult.WIN;
import static edu.project1.util.TerminalReader.readMessage;

public class Controller {
    private static final String SAFE_WORD = "give up";

    private final Model model;
    private final int maxMistakes;
    private final HangmanView view;

    public Controller(Dictionary dictionary, int maxMistakes) {
        this.maxMistakes = maxMistakes;
        this.model = new Model(dictionary, maxMistakes);
        this.view = new TerminalWriter();
    }

    public void runGame() {
        view.greet();
        view.showRules(SAFE_WORD);
        view.showAllowedMistakesAmount(maxMistakes);
        while (true) {
            if (!model.startNewGame()) {
                view.showNoMoreWords();
                return;
            }
            view.showNewWordMask(model.getUserWordMask());
            runGuessCycle();
        }
    }

    private void runGuessCycle() {
        while (true) {
            String userInput = readMessage();
            if (userInputIsSafeWord(userInput)) {
                view.showGiveUp();
                System.exit(0);
            }
            if (!userInputIsCorrect(userInput)) {
                view.showRules(SAFE_WORD);
                continue;
            }
            State state = model.guess(userInput.charAt(0));
            view.showState(state);
            if (state.result() == WIN || state.result() == DEFEAT) {
                break;
            }
        }
    }

    private boolean userInputIsCorrect(String userInput) {
        return userInput.length() == 1;
    }

    private boolean userInputIsSafeWord(String userInput) {
        return userInput.equals(SAFE_WORD);
    }
}
