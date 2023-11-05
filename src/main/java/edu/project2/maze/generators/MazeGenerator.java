package edu.project2.maze.generators;

import edu.project2.maze.Maze;

public interface MazeGenerator {
    Maze generateMaze(int height, int width);
}
