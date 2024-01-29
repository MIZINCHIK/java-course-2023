package edu.project2.maze.generators;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class RecursiveBackTrackerGeneratorTest {
    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Cell types are correct")
    void generateMaze_evenDimensions_IllegalArgumentException() {
        int width = 100;
        int height = 100;
        assertThatThrownBy(() -> new RecursiveBackTrackerGenerator().generateMaze(height, width))
            .isInstanceOf(IllegalArgumentException.class)
            .message().isEqualTo("Incorrect FlatMaze dimensions");
    }

    @RepeatedTest(10)
    @DisplayName("Cell types are correct")
    void generateMaze_usual_onlyPassagesAndWalls() {
        int width = 99;
        int height = 99;
        var maze = new RecursiveBackTrackerGenerator().generateMaze(height, width);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                var cell = maze.getCell(new Coordinate(row, column));
                assertThat(cell == CellType.PASSAGE || cell == CellType.WALL).isTrue();
            }
        }
    }

    @RepeatedTest(10)
    @DisplayName("Can find way between two paths")
    void generateMaze_usual_noWallsOnEvenPositions() {
        int width = 99;
        int height = 99;
        var maze = new RecursiveBackTrackerGenerator().generateMaze(height, width);
        for (int row = 0; row < height; row += 2) {
            for (int column = 0; column < width; column += 2) {
                var cell = maze.getCell(new Coordinate(row, column));
                assertThat(cell == CellType.PASSAGE).isTrue();
            }
        }
    }

    @RepeatedTest(10)
    @DisplayName("Can find way between two paths")
    void generateMaze_usual_pathBetweenTwoPassagesExists() {
        int width = 99;
        int height = 99;
        var maze = new RecursiveBackTrackerGenerator().generateMaze(height, width);
        var coordinates = getRandomPassages(maze);
        if (coordinates.size() < 2) {
            LOGGER.info("Only 1 passage cell in generated maze :/");
            return;
        }
        assertThat(pathExists(maze, coordinates.get(0), coordinates.get(1))).isTrue();
    }

    private List<Coordinate> getRandomPassages(Maze maze) {
        int width = maze.getWidth();
        int height = maze.getHeight();
        var result = new LinkedList<Coordinate>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                var coordinate = new Coordinate(row, column);
                var cell = maze.getCell(coordinate);
                if (cell == CellType.PASSAGE) {
                    result.add(coordinate);
                }
            }
        }
        Collections.shuffle(result);
        return result.stream()
            .limit(2)
            .collect(Collectors.toList());
    }

    private boolean pathExists(Maze maze, Coordinate start, Coordinate end) {
        Set<Coordinate> visited = new HashSet<>();
        Set<Coordinate> frontier = new HashSet<>();
        visited.add(start);
        frontier.add(start);
        while(true) {
            var adjacent = new HashSet<Coordinate>();
            for (var point : frontier) {
                var newAdjacent = getAdjacent(maze, point, visited);
                adjacent.addAll(newAdjacent);
                visited.addAll(newAdjacent);
            }
            frontier = adjacent;
            if (adjacent.isEmpty()) {
                break;
            }
        }
        return visited.contains(end);
    }

    private Set<Coordinate> getAdjacent(Maze maze, Coordinate cell, Set<Coordinate> visited) {
        var adjacent = new ArrayList<Coordinate>();
        adjacent.add(new Coordinate(cell.row(), cell.column() + 1));
        adjacent.add(new Coordinate(cell.row(), cell.column() - 1));
        adjacent.add(new Coordinate(cell.row() + 1, cell.column()));
        adjacent.add(new Coordinate(cell.row() - 1, cell.column()));
        return adjacent.stream()
            .filter(point -> maze.getCell(point) == CellType.PASSAGE && !visited.contains(point))
            .collect(Collectors.toSet());
    }
}
