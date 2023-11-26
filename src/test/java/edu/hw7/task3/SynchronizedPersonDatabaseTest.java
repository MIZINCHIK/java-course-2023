package edu.hw7.task3;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SynchronizedPersonDatabaseTest {
    @Test
    @DisplayName("Adding and finding in a single thread")
    void addAndFind_singleThreadAddingAndFinding_personsCanBeFound() {
        var database = new SynchronizedPersonDatabase();
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
        var database = new SynchronizedPersonDatabase();
        assertThat(database.delete(1)).isFalse();
    }

    @Test
    @DisplayName("Deleting a Person that is in a database")
    void deleteAndFind_thereIsPersonBefore_thereIsNoPersonAfter() {
        var database = new SynchronizedPersonDatabase();
        database.add(new Person(1, "Paul", "World", "111"));
        assertThat(database.delete(1)).isTrue();
        assertThat(database.findByName("Paul")).hasSameElementsAs(List.of());
    }

    @Test
    @DisplayName("Adding and finding in multiple threads")
    void addAndFind_concurrentAdditionAndFinding_personAddedEverywhereAtomically() throws InterruptedException {
        var database = new SynchronizedPersonDatabase();
        var consumers = new Thread[20];
        var suppliers = new Thread[4];
        for (int i = 0; i < 4; i++) {
            suppliers[i] = new Thread(() -> doSupply(database));
            suppliers[i].start();
            consumers[i] = new Thread(() -> assertConsume(database));
            consumers[i].start();
        }
        for (int i = 4; i < 20; i++) {
            consumers[i] = new Thread(() -> assertConsume(database));
            consumers[i].start();
        }
        for (Thread consumer : consumers) {
            consumer.join();
        }
        for (Thread supplier : suppliers) {
            supplier.join();
        }
    }

    private void doSupply(SynchronizedPersonDatabase database) {
        for (int i = 0; i < 10000; i++) {
            try {
                database.add(new Person(i, "Paul", "World", "111"));
            } catch (IllegalStateException ignored) {
            }
        }
    }

    private void assertConsume(SynchronizedPersonDatabase database) {
        for (int i = 0; i < 1000; i++) {
            assertThat(database.findByPhone("111").size()
                <= database.findByAddress("World").size()).isTrue();
            assertThat(database.findByPhone("111").size()
                <= database.findByName("Paul").size()).isTrue();
        }
    }
}
