package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.SetDictionary;
import edu.project1.model.GuessResult;
import edu.project1.model.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Project1Test {
    @Test
    @DisplayName("Empty dictionary's getWord returns null")
    void emptyDictionaryGet() {
        Dictionary dictionary = new SetDictionary(new HashSet<>());
        assertThat(dictionary.getWord()).isEqualTo(null);
    }

    @Test
    @DisplayName("Empty dictionary's removeWord returns false")
    void emptyDictionaryRemove() {
        Dictionary dictionary = new SetDictionary(new HashSet<>());
        assertThat(dictionary.removeWord("")).isEqualTo(false);
    }

    @Test
    @DisplayName("SetDictionary's removeWord makes words inaccessible")
    void dictionaryRemovesWords() {
        String wordToRemove = "word";
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of(wordToRemove)));
        assertThat(dictionary.removeWord(wordToRemove)).isEqualTo(true);
        assertThat(dictionary.getWord()).isEqualTo(null);
    }

    @Test
    @DisplayName("Model doesn't start new game if the word is too short")
    void shortWordNoGame() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("")));
        Model model = new Model(dictionary, 1);
        assertThat(model.startNewGame()).isFalse();
    }

    @Test
    @DisplayName("Model doesn't start new game if the word is too long")
    void longWordNoGame() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")));
        Model model = new Model(dictionary, 1);
        assertThat(model.startNewGame()).isFalse();
    }

    @Test
    @DisplayName("Model doesn't start new game if the word contains prohibited characters")
    void incorrectCharsNoGame() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("A")));
        Model model = new Model(dictionary, 1);
        assertThat(model.startNewGame()).isFalse();
    }

    @Test
    @DisplayName("Number of mistakes more than allowed leads to a DEFEAT")
    void mistakesDefeat() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("aaa")));
        Model model = new Model(dictionary, 0);
        assertThat(model.startNewGame()).isTrue();
        assertThat(model.guess('c').result().name()).isEqualTo(GuessResult.DEFEAT.name());
    }

    @Test
    @DisplayName("Max number of mistakes must be positive")
    void negativeAmountMaxMistakes() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("aaa")));
        assertThatThrownBy(
            () -> new Model(dictionary, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Mistake that doesn't exceed the max number of those results in FAILED")
    void failed() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("aaa")));
        Model model = new Model(dictionary, 3);
        model.startNewGame();
        assertThat(model.guess('c').result().name()).isEqualTo(GuessResult.FAILED.name());
    }

    @Test
    @DisplayName("Correct guess results in guess")
    void guessed() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("aab")));
        Model model = new Model(dictionary, 3);
        model.startNewGame();
        assertThat(model.guess('a').result().name()).isEqualTo(GuessResult.GUESSED.name());
    }

    @Test
    @DisplayName("When the whole word is guessed it's a WIN")
    void win() {
        Dictionary dictionary = new SetDictionary(new HashSet<>(List.of("aaa")));
        Model model = new Model(dictionary, 3);
        model.startNewGame();
        assertThat(model.guess('a').result().name()).isEqualTo(GuessResult.WIN.name());
    }
}
