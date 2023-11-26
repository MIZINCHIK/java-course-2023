package edu.hw7.task3;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UnsynchronizedPersonDatabaseTest {
    @Test
    @DisplayName("Adding and finding in a single thread")
    void addAndFind_singleThreadAddingAndFinding_personsCanBeFound() {
        var database = new UnsynchronizedPersonDatabase();
        assertThat(database.findByAddress("World")).hasSameElementsAs(List.of());
        database.add(new Person(1, "Paul", "World", "111"));
        database.add(new Person(2, "Paul", "World", "111"));
        database.add(new Person(3, "Paul", "World", "111"));
        database.add(new Person(4, "Paul", "World", "111"));
        database.add(new Person(5, "Paul", "World", "111"));
        database.add(new Person(6, "Rita", "NewWorld", "112"));
        var expected = List.of(
            new Person(1, "Paul", "World", "111"),
            new Person(2, "Paul", "World", "111"),
            new Person(3, "Paul", "World", "111"),
            new Person(4, "Paul", "World", "111"),
            new Person(5, "Paul", "World", "111")
        );
        assertThat(database.findByAddress("World")).hasSameElementsAs(expected);
        assertThat(database.findByName("Paul")).hasSameElementsAs(expected);
        assertThat(database.findByPhone("111")).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("Delete a Person that's not in a database")
    void delete_noSuchElement_false() {
        var database = new UnsynchronizedPersonDatabase();
        AssertionsForClassTypes.assertThat(database.delete(1)).isFalse();
    }

    @Test
    @DisplayName("Deleting a Person that is in a database")
    void deleteAndFind_thereIsPersonBefore_thereIsNoPersonAfter() {
        var database = new UnsynchronizedPersonDatabase();
        database.add(new Person(1, "Paul", "World", "111"));
        AssertionsForClassTypes.assertThat(database.delete(1)).isTrue();
        assertThat(database.findByName("Paul")).hasSameElementsAs(List.of());
    }
}
