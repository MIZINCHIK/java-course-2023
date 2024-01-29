package edu.hw9.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DfsRecursiveTask extends RecursiveTask<List<Point>> {
    private final CellType[][] maze;
    private final Point currentCoordinate;
    private final Set<Point> visited;
    private final Point goal;

    public DfsRecursiveTask(CellType[][] maze, Point currentCoordinate, Set<Point> visited, Point goal) {
        this.maze = maze;
        this.currentCoordinate = currentCoordinate;
        this.visited = visited;
        this.goal = goal;
    }

    @Override
    protected List<Point> compute() {
        visited.add(currentCoordinate);
        if (currentCoordinate.equals(goal)) {
            var result = new ArrayList<Point>();
            result.addFirst(currentCoordinate);
            return result;
        } else {
            var intermediate = ForkJoinTask.invokeAll(createSubtasks())
                .stream()
                .map(ForkJoinTask::join)
                .filter(result -> !result.isEmpty())
                .findFirst();
            if (intermediate.isEmpty()) {
                return List.of();
            }
            var result = intermediate.get();
            result.addFirst(currentCoordinate);
            return result;
        }
    }

    private List<DfsRecursiveTask> createSubtasks() {
        return getViableNeighbours().stream()
            .map(neighbour -> new DfsRecursiveTask(maze, neighbour, visited, goal))
            .toList();
    }

    private List<Point> getViableNeighbours() {
        return Stream.of(
                new Point(currentCoordinate.x() + 1, currentCoordinate.y()),
                new Point(currentCoordinate.x(), currentCoordinate.y() + 1),
                new Point(currentCoordinate.x() - 1, currentCoordinate.y()),
                new Point(currentCoordinate.x(), currentCoordinate.y() - 1)
            )
            .filter(point -> !visited.contains(point)
                && point.inConstraints(maze[0].length, maze.length)
                && maze[point.x()][point.y()] == CellType.WAY)
            .collect(Collectors.toList());
    }
}
