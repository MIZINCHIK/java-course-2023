package edu.hw6.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DiskMapTest {
    private static final String RESOURCES_FROM_ROOT = "src/test/resources/edu/hw6/task1";
    private static final Path resources = Path.of(System.getProperty("user.dir"))
        .resolve(RESOURCES_FROM_ROOT);

    @Test
    @DisplayName("Normal use of constructor with a possibly non-existent storage")
    void constructor_correctPath_subDirectoryCreated() throws IOException {
        Path path = resources
            .resolve("empty");
        new DiskMap(path);
        assertThat(Files.exists(path)).isTrue();
    }

    @Test
    @DisplayName("Constructor for a non-existent directory")
    void constructor_incorrectPath_illegalArgumentException() throws IOException {
        Path path = resources
            .resolve("sadsadadsad");
        assertThatThrownBy(() -> new DiskMap(path))
            .isInstanceOf(IllegalArgumentException.class)
            .message().isEqualTo("Incorrect path");
    }

    @Test
    @DisplayName("Normal use of constructor with a possibly non-existent directory")
    void constructor_nonEmptyStorage_valuesLoaded() throws IOException {
        Path path = resources
            .resolve("prepared");
        var diskMap = new DiskMap(path);
        var expected = new HashMap<String, String>();
        expected.put("one", "one" + System.lineSeparator());
        expected.put("sadhjsadhja.kvsadhjsajdhsa.kv", "ewfdhjewfhjewhfjwef" + System.lineSeparator() +
            "wefwefjkewfew" + System.lineSeparator() +
            "fewfewfuiwefew" + System.lineSeparator() +
            "fwefewfe" + System.lineSeparator());
        assertThat(diskMap).isEqualTo(expected);
    }

    @Test
    @DisplayName("Clearing storage")
    void clear_correctPath_emptyStorage() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        diskMap.put("key1", "val1");
        diskMap.put("key2", "val2");
        diskMap.clear();
        diskMap = new DiskMap(path);
        var expected = new HashMap<String, String>();
        assertThat(diskMap).isEqualTo(expected);
    }

    @Test
    @DisplayName("Removing elements from storage")
    void remove_correctPath_success() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        diskMap.put("key1", "val1");
        diskMap.put("key2", "val2");
        diskMap.remove("key1");
        diskMap = new DiskMap(path);
        var expected = new HashMap<String, String>();
        expected.put("key", "val");
        expected.put("key2", "val2");
        assertThat(diskMap).isEqualTo(expected);
        diskMap.clear();
    }

    @Test
    @DisplayName("Removing elements from storage")
    void remove_fileRemovedIndependently_keyValueConsistencyException() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        diskMap.put("key1", "val1");
        diskMap.put("key2", "val2");
        Files.delete(path.resolve(DiskMap.SUB_DIRECTORY_NAME + "/key1.kv"));
        assertThatThrownBy(() -> diskMap.remove("key1"))
            .isInstanceOf(KeyValueConsistencyException.class)
            .message().isEqualTo("Existing key was deleted prior");
    }

    @Test
    @DisplayName("Putting a value that's not created yet creates a new file")
    void put_doesntExist_fileCreated() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        var expected = new HashMap<String, String>();
        expected.put("key", "val");
        assertThat(diskMap).isEqualTo(expected);
        diskMap.clear();
    }

    @Test
    @DisplayName("Putting a value that's already in a storage overwrites a file")
    void put_exists_fileOverwritten() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        diskMap.put("key", "val1");
        var expected = new HashMap<String, String>();
        expected.put("key", "val1");
        assertThat(diskMap).isEqualTo(expected);
        diskMap.clear();
    }

    @Test
    @DisplayName("When the storage directory is purged KeyValueConsistencyException is thrown on put")
    void put_directoryDeleted_keyValueConsistencyException() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        try (var walk = Files.walk(path.resolve(DiskMap.SUB_DIRECTORY_NAME))) {
            walk.sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
        assertThatThrownBy(() -> diskMap.put("key", "val"))
            .isInstanceOf(KeyValueConsistencyException.class)
            .message().isEqualTo("Something happened to storage directory");
    }

    @Test
    @DisplayName("Putting all entries from another map")
    void putAll_intersectingMap_success() throws IOException {
        Path path = resources
            .resolve("restore_my_state");
        var diskMap = new DiskMap(path);
        diskMap.put("key", "val");
        diskMap.put("key1", "val1");
        diskMap.put("key2", "val2");
        var adding = new HashMap<String, String>();
        adding.put("key2", "val22323");
        adding.put("key3", "val3");
        diskMap.putAll(adding);
        diskMap = new DiskMap(path);
        var expected = new HashMap<String, String>();
        expected.put("key", "val");
        expected.put("key1", "val1");
        expected.put("key2", "val22323");
        expected.put("key3", "val3");
        assertThat(diskMap).isEqualTo(expected);
        diskMap.clear();
    }
}
