package edu.hw9.task2;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

public class FileTreeSearch {
    private FileTreeSearch() {
        throw new IllegalStateException();
    }

    public static List<Path> searchLargeDirs(int leastAmountOfFilesInDir, Path startOfTheSearch) {
        List<Path> result = Collections.synchronizedList(new ArrayList<>());
        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            commonPool.invoke(new FilesInDirsRecursiveTask(startOfTheSearch, result, leastAmountOfFilesInDir));
        }
        return result;
    }

    public static List<Path> searchDirsByPredicate(Predicate<Path> predicate, Path startOfTheSearch) {
        List<Path> result = Collections.synchronizedList(new ArrayList<>());
        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            commonPool.invoke(new FilesPredicateRecursiveTask(startOfTheSearch, predicate, result));
        }
        return result;
    }
}
