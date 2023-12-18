package edu.project2.maze.generators;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

public class RecursiveBackTrackerGenerator implements MazeGenerator {
    private int width;
    private int height;
    private Maze maze;
    private Stack<Coordinate> visited;
    private Random random;

    private void initStructures(int height, int width) {
        this.maze = new Maze(height, width);
        this.width = width;
        this.height = height;
        visited = new Stack<>();
        random = new Random();
        for (int row = 0; row < height; row++) {
            maze.setRow(row, new CellType[width]);
        }
    }

    @Override
    public Maze generateMaze(int height, int width) {
        initStructures(height, width);
        setWalls();
        Coordinate startingPoint = chooseRandomStart();
        maze.setCellType(startingPoint, CellType.PASSAGE);
        visited.push(startingPoint);
        while (!visited.empty()) {
            Coordinate currentCell = visited.pop();
            List<Coordinate> unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
            if (unvisitedNeighbours.isEmpty()) {
                continue;
            }
            visited.push(currentCell);
            Coordinate nextCell = unvisitedNeighbours.get(0);
            maze.setCellType(nextCell, CellType.PASSAGE);
            removeWallBetween(currentCell, nextCell);
            visited.push(nextCell);
        }
        return maze;
    }

    private void setWalls() {
        for (int row = 1; row < height; row += 2) {
            for (int column = 0; column < width; column++) {
                maze.setCellType(new Coordinate(row, column), CellType.WALL);
            }
        }
        for (int column = 1; column < width; column += 2) {
            for (int row = 0; row < width; row++) {
                maze.setCellType(new Coordinate(row, column), CellType.WALL);
            }
        }
    }

    private Coordinate chooseRandomStart() {
        int randomColumn = (random.nextInt(Math.ceilDiv(width, 2))) * 2;
        int randomRow = (random.nextInt(Math.ceilDiv(height, 2))) * 2;
        return new Coordinate(randomRow, randomColumn);
    }

    private void removeWallBetween(Coordinate cellA, Coordinate cellB) {
        maze.setCellType(new Coordinate((cellA.row() + cellB.row()) / 2,
            (cellA.column() + cellB.column()) / 2), CellType.PASSAGE);
    }

    private List<Coordinate> getUnvisitedNeighbours(Coordinate point) {
        var result = point.getNeighbours(2).stream()
            .filter(neighbour -> maze.getCell(neighbour) == CellType.NONE)
            .collect(Collectors.toList());
        Collections.shuffle(result);
        return result;
    }
}
