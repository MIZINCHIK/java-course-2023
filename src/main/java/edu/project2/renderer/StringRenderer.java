package edu.project2.renderer;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;

public interface StringRenderer {
    String render(Maze maze);

    String render(CellType type);
}
