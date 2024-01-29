package edu.hw9.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static edu.hw9.task2.FileTreeSearch.searchDirsByPredicate;
import static edu.hw9.task2.FileTreeSearch.searchLargeDirs;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileTreeSearchTest {
    Path resources =
        Path.of(System.getProperty("user.dir"))
            .resolve("src").resolve("test").resolve("resources").resolve("hw9");

    @BeforeEach
    public void createDir() throws IOException {
        Files.createDirectories(resources);
    }

    @AfterEach
    public void purgeDir() {
        purgeDir(resources.toFile());
    }

    private void purgeDir(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                purgeDir(file);
            }
        }
        directory.delete();
    }

    @Test
    @DisplayName("Searching directories with a certain amount of files starting from a file")
    void searchLargeDirs_whenStartFromFile_thenEmptyList() throws IOException {
        Path newFile = resources.resolve("file1.txt");
        Files.createFile(newFile);
        assertThat(searchLargeDirs(0, newFile).size()).isEqualTo(0);
    }

    @RepeatedTest(5)
    @DisplayName("Searching all the directories")
    void searchLargeDirs_whenAtLeast0Files_thenAllDirectories() throws IOException {
        int numberOfDirs = generateDirsRecursively(resources, 3);
        assertThat(searchLargeDirs(0, resources).size()).isEqualTo(numberOfDirs + 1);
    }

    @RepeatedTest(5)
    @DisplayName("Searching directories with a limit to amount of files")
    void searchLargeDirs_whenAtLeastNFiles_thenCorrect() throws IOException {
        AtomicInteger atomic = new AtomicInteger(0);
        int limit = ThreadLocalRandom.current().nextInt(10);
        generateDirsRecursivelyWithFiles(resources, 3, atomic, limit);
        assertThat(searchLargeDirs(limit, resources).size()).isEqualTo(atomic.get());
    }

    @RepeatedTest(5)
    @DisplayName("Searching for txt files")
    void searchDirsByPredicate_whenPredicateOnName_thenCorrect() throws IOException {
        Predicate<Path> predicate = path -> Files.isRegularFile(path)
            && path.getFileName().toFile().getName().matches(".*\\.txt");
        int result = generateDirsRecursivelyWithPatternFiles(resources, 7, predicate);
        assertThat(searchDirsByPredicate(predicate, resources).size()).isEqualTo(result);
    }

    private int generateDirsRecursivelyWithPatternFiles(Path startingPoint, int depth, Predicate<Path> predicate)
        throws IOException {
        int bound = 5;
        int counter = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(bound); i++) {
            String current = generateRandomString();
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    current += ".txt";
                    counter++;
                }
                Files.createFile(startingPoint.resolve(current));
            } else {
                Path nextPath = startingPoint.resolve(current);
                Files.createDirectories(nextPath);
                if (depth > 0) {
                    counter += generateDirsRecursivelyWithPatternFiles(nextPath, depth - 1, predicate);
                }
            }
        }
        return counter;
    }

    private int generateDirsRecursivelyWithFiles(Path startingPoint, int depth, AtomicInteger atomic, int limit)
        throws IOException {
        int bound = 10;
        int counter = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(bound); i++) {
            Path current = startingPoint.resolve(generateRandomString());
            if (random.nextBoolean()) {
                Files.createFile(current);
                counter++;
            } else {
                Files.createDirectories(current);
                if (depth > 0) {
                    counter += generateDirsRecursivelyWithFiles(current, depth - 1, atomic, limit);
                }
            }
        }
        if (counter >= limit) {
            atomic.incrementAndGet();
        }
        return counter;
    }

    private int generateDirsRecursively(Path startingPoint, int depth) throws IOException {
        int bound = 10;
        int counter = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(bound); i++) {
            Path current = startingPoint.resolve(generateRandomString());
            Files.createDirectories(current);
            counter++;
            if (depth > 0) {
                counter += generateDirsRecursively(current, depth - 1);
            }
        }
        return counter;
    }

    private String generateRandomString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        return generatedString;
    }
}
