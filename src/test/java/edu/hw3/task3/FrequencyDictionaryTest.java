package edu.hw3.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static edu.hw3.task3.FrequencyDictionary.getFrequencyDictionary;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FrequencyDictionaryTest {
    @Test
    @DisplayName("Frequency dictionary of an empty list produces an empty Map")
    void getFrequencyDictionary_emptyList_emptyMap() {
        assertThat(getFrequencyDictionary(new ArrayList<>())).isEqualTo(new TreeMap<>());
    }

    @Test
    @DisplayName("Frequency dictionary for a List of Strings")
    void getFrequencyDictionary_listOfStrings_frequencyDictionaryForStrings() {
        assertThat(getFrequencyDictionary(List.of("код", "код", "код", "bug")))
            .isEqualTo(Map.of("код", 3, "bug", 1));
    }

    @Test
    @DisplayName("Frequency dictionary for a List of Integers")
    void getFrequencyDictionary_listOfIntegers_frequencyDictionaryForIntegers() {
        assertThat(getFrequencyDictionary(List.of(1, 1, 2, 2)))
            .isEqualTo(Map.of(1, 2, 2, 2));
    }
}
