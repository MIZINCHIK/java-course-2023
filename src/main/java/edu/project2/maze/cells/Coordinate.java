package edu.project2.maze.cells;

import java.util.ArrayList;
import java.util.List;

public record Coordinate(int row, int column) {
    public List<Coordinate> getNeighbours(int step) {
        var result = new ArrayList<Coordinate>();
        result.add(new Coordinate(row(), column() + step));
        result.add(new Coordinate(row() + step, column()));
        result.add(new Coordinate(row(), column() - step));
        result.add(new Coordinate(row() - step, column()));
        return result;
    }
}
