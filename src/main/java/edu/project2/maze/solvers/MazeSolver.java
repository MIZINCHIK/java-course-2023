package edu.project2.maze.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.Coordinate;
import java.util.List;

public interface MazeSolver {
    List<Coordinate> getPath(Maze maze, Coordinate start, Coordinate end);
}
