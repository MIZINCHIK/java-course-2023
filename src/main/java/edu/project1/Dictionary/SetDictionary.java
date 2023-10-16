package edu.project1.Dictionary;

import org.jetbrains.annotations.NotNull;
import java.util.Random;
import java.util.Set;

public class SetDictionary implements Dictionary {
    private final Set<String> dictionary;
    private final Random random = new Random();

    public SetDictionary(@NotNull Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String getWord() {
        if (dictionary.isEmpty()) {
            return null;
        }
        return dictionary.stream().skip(random.nextInt(dictionary.size())).findFirst().orElse(null);
    }

    @Override
    public boolean removeWord(String word) {
        return dictionary.remove(word);
    }
}
