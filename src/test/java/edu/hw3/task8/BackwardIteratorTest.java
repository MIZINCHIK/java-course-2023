package edu.hw3.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class BackwardIteratorTest {
    @Test
    @DisplayName("Null keys are allowed in a TreeMap with this TreeMapNullAllowingComparator")
    void nullInTreeMap() {
        List<Integer> input = List.of(1, 2, 3);
        BackwardIterator<Integer> iterator = new BackwardIterator<>(input);
        List<Integer> expected = input.reversed();
        List<Integer> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        assertIterableEquals(expected, actual);
        assertThatThrownBy(() -> iterator.next()).isInstanceOf(NoSuchElementException.class);
    }
}
