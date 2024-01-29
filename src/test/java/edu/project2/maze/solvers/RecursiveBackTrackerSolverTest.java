package edu.project2.maze.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class RecursiveBackTrackerSolverTest {
    Maze maze;

    @BeforeEach
    void createMaze() {
        maze = new Maze(11, 11);
        maze.setRow(0,
            new CellType[]{CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL,
                CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE,
                CellType.PASSAGE, CellType.PASSAGE});
        maze.setRow(1, new CellType[]{CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.WALL,
            CellType.PASSAGE, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(2, new CellType[]{CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE,
            CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE,
            CellType.PASSAGE, CellType.PASSAGE});
        maze.setRow(3, new CellType[]{CellType.PASSAGE, CellType.WALL, CellType.WALL, CellType.WALL,
            CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.WALL, CellType.WALL,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(4, new CellType[]{CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE,
            CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE,
            CellType.PASSAGE, CellType.PASSAGE});
        maze.setRow(5, new CellType[]{CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.WALL,
            CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(6, new CellType[]{CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE,
            CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(7, new CellType[]{CellType.PASSAGE, CellType.WALL, CellType.WALL, CellType.WALL,
            CellType.PASSAGE, CellType.WALL, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(8, new CellType[]{CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL,
            CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.WALL, CellType.PASSAGE,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(9, new CellType[]{CellType.WALL, CellType.WALL, CellType.PASSAGE, CellType.WALL,
            CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.PASSAGE,
            CellType.WALL, CellType.PASSAGE});
        maze.setRow(10, new CellType[]{CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE,
            CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE, CellType.PASSAGE,
            CellType.WALL, CellType.PASSAGE});
    }

    @Test
    @DisplayName("No path from/to a wall")
    void getPath_endsInWalls_emptyList() {
        assertIterableEquals(new ArrayList<>(),
            new RecursiveBackTrackerSolver().
                getPath(maze, new Coordinate(1, 1), new Coordinate(0, 0))
        );
        assertIterableEquals(new ArrayList<>(),
            new RecursiveBackTrackerSolver().
                getPath(maze, new Coordinate(0, 0), new Coordinate(1, 1))
        );
    }

    @Test
    @DisplayName("When no path between the points getPath returns and empty list")
    void getPath_isolatedEnds_emptyList() {
        maze.setCellType(new Coordinate(0, 1), CellType.WALL);
        maze.setCellType(new Coordinate(1, 0), CellType.WALL);
        assertIterableEquals(new ArrayList<>(),
            new RecursiveBackTrackerSolver().
                getPath(maze, new Coordinate(0, 0), new Coordinate(0, 2))
        );
    }

    @Test
    @DisplayName("Finding a path in an ideal maze (as they're meant to be in the project)")
    void getPath_idealMaze_normalPath() {
        var real = new RecursiveBackTrackerSolver()
            .getPath(maze,new Coordinate(0, 0),
                new Coordinate(10, 10));
        var expected = new ArrayList<Coordinate>();
        expected.add(new Coordinate(0, 0));
        expected.add(new Coordinate(0, 1));
        expected.add(new Coordinate(0, 2));
        expected.add(new Coordinate(1, 2));
        expected.add(new Coordinate(2, 2));
        expected.add(new Coordinate(2, 3));
        expected.add(new Coordinate(2, 4));
        expected.add(new Coordinate(1, 4));
        expected.add(new Coordinate(0, 4));
        expected.add(new Coordinate(0, 5));
        expected.add(new Coordinate(0, 6));
        expected.add(new Coordinate(0, 7));
        expected.add(new Coordinate(0, 8));
        expected.add(new Coordinate(0, 9));
        expected.add(new Coordinate(0, 10));
        expected.add(new Coordinate(1, 10));
        expected.add(new Coordinate(2, 10));
        expected.add(new Coordinate(3, 10));
        expected.add(new Coordinate(4, 10));
        expected.add(new Coordinate(5, 10));
        expected.add(new Coordinate(6, 10));
        expected.add(new Coordinate(7, 10));
        expected.add(new Coordinate(8, 10));
        expected.add(new Coordinate(9, 10));
        expected.add(new Coordinate(10, 10));
        assertIterableEquals(expected, real);
    }
}
