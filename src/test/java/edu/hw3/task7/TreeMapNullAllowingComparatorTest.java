package edu.hw3.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.TreeMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TreeMapNullAllowingComparatorTest {
    @Test
    @DisplayName("Null keys are allowed in a TreeMap with this TreeMapNullAllowingComparator")
    void nullInTreeMap() {
        Map<String, String> tree = new TreeMap<>(new TreeMapNullAllowingComparator<>());
        tree.put(null, "test");

        assertThat(tree.containsKey(null)).isTrue();
    }
}
