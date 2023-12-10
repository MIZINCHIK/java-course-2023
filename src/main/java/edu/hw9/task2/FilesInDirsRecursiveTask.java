package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FilesInDirsRecursiveTask extends RecursiveTask<Integer> {
    private final Path path;
    private final List<Path> directories;
    private final int leastAmount;

    public FilesInDirsRecursiveTask(Path path, List<Path> directories, int leastAmount) {
        this.path = path;
        this.directories = directories;
        this.leastAmount = leastAmount;
    }

    @Override
    protected Integer compute() {
        if (Files.isDirectory(path)) {
            int result = ForkJoinTask.invokeAll(createSubtasks())
                .stream()
                .mapToInt(ForkJoinTask::join)
                .sum();
            if (result >= leastAmount) {
                directories.add(path);
            }
            return result;
        } else if (Files.isRegularFile(path)) {
            return 1;
        } else {
            return 0;
        }
    }

    private List<FilesInDirsRecursiveTask> createSubtasks() {
        List<FilesInDirsRecursiveTask> dividedTasks = new ArrayList<>();
        try (var stream = Files.newDirectoryStream(path)) {
            for (Path subPath : stream) {
                dividedTasks.add(new FilesInDirsRecursiveTask(subPath, directories, leastAmount));
            }
            return dividedTasks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
