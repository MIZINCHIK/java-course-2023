package edu.project2.maze;

import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;
import java.util.List;

public final class Maze {
    private static final String INCORRECT_PATH = "Provided path is incorrect";
    private final int height;
    private final int width;
    private final CellType[][] grid;

    public Maze(int height, int width) throws IllegalArgumentException {
        if (height < 1 || width < 1 || height % 2 == 0 || width % 2 == 0) {
            throw new IllegalArgumentException("Incorrect FlatMaze dimensions");
        }
        this.height = height;
        this.width = width;
        grid = new CellType[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public CellType getCell(Coordinate coordinate) {
        if (isCoordinateOffLimits(coordinate)) {
            return CellType.OFF_LIMITS;
        }
        int row = coordinate.row();
        int column = coordinate.column();
        if (grid[row][column] == null) {
            return CellType.NONE;
        }
        return grid[row][column];
    }

    public void setCellType(Coordinate coordinate, CellType type) {
        if (isCoordinateOffLimits(coordinate)) {
            throw new IllegalArgumentException("There is no cell with such coordiantes in the Maze");
        }
        grid[coordinate.row()][coordinate.column()] = type;
    }

    public void setRow(int rowIndex, CellType[] row) {
        if (isRowOffLimits(rowIndex)) {
            throw new IllegalArgumentException("The row is off the grid limits");
        }
        grid[rowIndex] = row;
    }

    private boolean isCoordinateOffLimits(Coordinate coordinate) {
        return isRowOffLimits(coordinate.row()) || isColumnOffLimits(coordinate.column());
    }

    private boolean isRowOffLimits(int row) {
        return row < 0 || row >= height;
    }

    private boolean isColumnOffLimits(int column) {
        return column < 0 || column >= width;
    }

    public void markPath(List<Coordinate> path) {
        for (Coordinate coordinate : path) {
            if (isCoordinateOffLimits(coordinate)
                || grid[coordinate.row()][coordinate.column()] != CellType.PASSAGE) {
                throw new IllegalArgumentException(INCORRECT_PATH);
            }
            grid[coordinate.row()][coordinate.column()] = CellType.PATH;
        }
    }

    public void cleanUp() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (grid[row][column] == CellType.PATH) {
                    grid[row][column] = CellType.PASSAGE;
                }
            }
        }
    }
}
