package edu.project2.maze.cells;

import java.util.HashSet;
import java.util.Set;

public record Coordinate(int row, int column) {
    public Set<Coordinate> getNeighbours(int step) {
        var result = new HashSet<Coordinate>();
        result.add(new Coordinate(row(), column() + step));
        result.add(new Coordinate(row() + step, column()));
        result.add(new Coordinate(row(), column() - step));
        result.add(new Coordinate(row() - step, column()));
        return result;
    }
}
