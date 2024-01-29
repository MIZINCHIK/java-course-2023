package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Predicate;

public class FilesPredicateRecursiveTask extends RecursiveAction {
    private final Path path;
    private final Predicate<Path> predicate;
    private final List<Path> result;

    public FilesPredicateRecursiveTask(Path path, Predicate<Path> predicate, List<Path> result) {
        this.path = path;
        this.predicate = predicate;
        this.result = result;
    }

    @Override
    protected void compute() {
        if (Files.isDirectory(path)) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else if (Files.isRegularFile(path) && predicate.test(path)) {
            result.add(path);
        }
    }

    private List<FilesPredicateRecursiveTask> createSubtasks() {
        List<FilesPredicateRecursiveTask> dividedTasks = new ArrayList<>();
        try (var stream = Files.newDirectoryStream(path)) {
            for (Path subPath : stream) {
                dividedTasks.add(new FilesPredicateRecursiveTask(subPath, predicate, result));
            }
            return dividedTasks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
