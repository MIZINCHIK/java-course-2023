package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyDictionary {
    private FrequencyDictionary() {
        throw new IllegalStateException();
    }

    public static <E> Map<E, Integer> getFrequencyDictionary(List<E> elements) {
        Map<E, Integer> result = new HashMap<>();
        for (E element : elements) {
            if (result.containsKey(element)) {
                result.put(element, result.get(element) + 1);
            } else {
                result.put(element, 1);
            }
        }
        return result;
    }
}
