package edu.hw9.task3;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentDfs {
    private ConcurrentDfs() {
        throw new IllegalStateException();
    }

    public static List<Point> findWay(CellType[][] maze, Point start, Point end) {
        if (!isMazeCorrect(maze)
            || !start.inConstraints(maze[0].length, maze.length)
            || !end.inConstraints(maze[0].length, maze.length)
            || maze[start.x()][start.y()] == CellType.NO_WAY
            || maze[end.x()][end.y()] == CellType.NO_WAY) {
            return List.of();
        }
        Set<Point> visited = Collections.synchronizedSet(new HashSet<>());
        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            return commonPool.invoke(new DfsRecursiveTask(maze, start, visited, end));
        }
    }

    private static boolean isMazeCorrect(CellType[][] maze) {
        if (maze == null || maze.length == 0) {
            return false;
        }
        int length = maze[0].length;
        for (CellType[] row : maze) {
            if (row.length != length) {
                return false;
            }
        }
        return true;
    }
}
