package edu.hw7.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static edu.hw7.task3.MapSetUtils.addToSetInMap;
import static edu.hw7.task3.MapSetUtils.removeFromSetInMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MapSetUtilsTest {
    @Test
    @DisplayName("Adding element to map when no respective Set exist")
    void addToSetInMap_noSetPrior_inSetAfter() {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        addToSetInMap(map, 1, 1);
        assertThat(map.get(1).contains(1)).isTrue();
    }

    @Test
    @DisplayName("Adding element to map when the set is there already")
    void addToSetInMap_isSetPrior_inSetAfter() {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        map.put(1, new HashSet<>(List.of(1)));
        addToSetInMap(map, 1, 1);
        assertThat(map.get(1).contains(1)).isTrue();
        addToSetInMap(map, 2, 1);
        assertThat(map.get(1).contains(2)).isTrue();
    }

    @Test
    @DisplayName("Removing an element that wasn't in map before")
    void removeFromSetInMap_notInMapBefore_false() {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        assertDoesNotThrow(() -> removeFromSetInMap(map, 1, 1));
        assertThat(map.get(1)).isNull();
    }

    @Test
    @DisplayName("Removing an element that was in map before")
    void removeFromSetInMap_inMapBefore_false() {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        map.put(1, new HashSet<>(List.of(1)));
        assertDoesNotThrow(() -> removeFromSetInMap(map, 1, 1));
        assertThat(map.get(1)).isNull();
    }
}
