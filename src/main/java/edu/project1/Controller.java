package edu.project1;

import edu.project1.Dictionary.Dictionary;
import edu.project1.Model.Model;
import edu.project1.Model.State;
import static edu.project1.Util.TerminalReader.readMessage;
import static edu.project1.Util.TerminalWriter.greet;
import static edu.project1.Util.TerminalWriter.printAllowedMistakesAmount;
import static edu.project1.Util.TerminalWriter.printAnswer;
import static edu.project1.Util.TerminalWriter.printCorrectGuess;
import static edu.project1.Util.TerminalWriter.printCurrentWordMask;
import static edu.project1.Util.TerminalWriter.printDefeat;
import static edu.project1.Util.TerminalWriter.printError;
import static edu.project1.Util.TerminalWriter.printGiveUp;
import static edu.project1.Util.TerminalWriter.printMistakesLeft;
import static edu.project1.Util.TerminalWriter.printNewWordMask;
import static edu.project1.Util.TerminalWriter.printNoMoreWords;
import static edu.project1.Util.TerminalWriter.printRules;
import static edu.project1.Util.TerminalWriter.printWin;
import static edu.project1.Util.TerminalWriter.printWrongGuess;

public class Controller {
    private final static String SAFE_WORD = "give up";
    private final Model model;
    private final int maxMistakes;

    public Controller(Dictionary dictionary, int maxMistakes) {
        this.maxMistakes = maxMistakes;
        this.model = new Model(dictionary, maxMistakes);
    }

    public void runGame() {
        greet();
        printRules(SAFE_WORD);
        printAllowedMistakesAmount(maxMistakes);
        while (true) {
            if (!model.startNewGame()) {
                printNoMoreWords();
                return;
            }
            printNewWordMask(model.getUserWordMask());
            runGuessCycle();
        }
    }

    private void runGuessCycle() {
        guessCycle:
        while (true) {
            String userInput = readMessage();
            if (userInputIsSafeWord(userInput)) {
                printGiveUp();
                System.exit(0);
            }
            if (!userInputIsCorrect(userInput)) {
                printRules(SAFE_WORD);
                continue;
            }
            State state = model.guess(userInput.charAt(0));
            switch (state.result()) {
                case WIN -> printWin();
                case DEFEAT -> printDefeat();
                case FAILED -> printWrongGuess();
                case GUESSED -> printCorrectGuess();
                default -> printError();
            }
            switch (state.result()) {
                case WIN, DEFEAT -> {
                    printAnswer(model.getCurrentWord());
                    break guessCycle;
                }
                case FAILED, GUESSED -> {
                    printCurrentWordMask(model.getUserWordMask());
                    printMistakesLeft(model.getMistakesLeft());
                }
                default -> {
                    break guessCycle;
                }
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
