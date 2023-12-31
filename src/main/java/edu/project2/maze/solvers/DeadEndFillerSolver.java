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
        if (maze.getCell(start) == CellType.WALL || maze.getCell(end) == CellType.WALL) {
            return new ArrayList<>();
        }
        initStructures(maze, start, end);
        for (int row = 0; row < height; row += 2) {
            for (int column = 0; column < width; column += 2) {
                fillDeadEnds(new Coordinate(row, column));
            }
        }
        if (getAvailableNeighbours(start).isEmpty()
        || getAvailableNeighbours(end).isEmpty()) {
            return new ArrayList<>();
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
        Coordinate currentCell = start;
        while (!currentCell.equals(end)) {
            List<Coordinate> availableNeighbours = getAvailableNeighbours(currentCell);
            if (availableNeighbours.isEmpty()) {
                return new ArrayList<>();
            }
            result.add(currentCell);
            noWay.add(currentCell);
            currentCell = availableNeighbours.get(0);
        }
        result.add(currentCell);
        return result;
    }
}
