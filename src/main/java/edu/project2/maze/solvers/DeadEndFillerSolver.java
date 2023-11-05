package edu.project2.maze.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeadEndFillerSolver implements MazeSolver {
    private Maze maze;
    private int height;
    private int width;
    private Coordinate start;
    private Coordinate end;
    private Set<Coordinate> noWay;

    @Override
    public List<Coordinate> getPath(Maze maze, Coordinate start, Coordinate end) {
        initStructures(maze, start, end);
        for (int row = 0; row < height; row += 2) {
            for (int column = 0; column < width; column += 2) {
                fillDeadEnds(new Coordinate(row, column));
            }
        }
        return fillPath();
    }

    private void initStructures(Maze maze, Coordinate start, Coordinate end) {
        this.height = maze.getHeight();
        this.width = maze.getWidth();
        this.maze = maze;
        this.start = start;
        this.end = end;
        noWay = new HashSet<>();
    }

    private List<Coordinate> getAvailableNeighbours(Coordinate cell) {
        return cell.getNeighbours(1).stream()
            .filter(neighbour -> maze.getCell(neighbour) == CellType.PASSAGE
            && !noWay.contains(neighbour))
            .collect(Collectors.toList());
    }

    private void fillDeadEnds(Coordinate cell) {
        Coordinate currentCell = cell;
        List<Coordinate> neighbours = getAvailableNeighbours(currentCell);
        while (!currentCell.equals(end) && !currentCell.equals(start) && neighbours.size() <= 1) {
            noWay.add(currentCell);
            if (neighbours.isEmpty()) {
                return;
            }
            currentCell = neighbours.get(0);
            neighbours = getAvailableNeighbours(currentCell);
        }
    }

    private List<Coordinate> fillPath() {
        List<Coordinate> result = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Coordinate point = new Coordinate(row, column);
                if (maze.getCell(point) == CellType.PASSAGE && !noWay.contains(point)) {
                    result.add(point);
                }
            }
        }
        return result;
    }
}
