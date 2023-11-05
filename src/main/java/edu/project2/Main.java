package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.Coordinate;
import edu.project2.maze.generators.EllerGenerator;
import edu.project2.maze.generators.RecursiveBackTrackerGenerator;
import edu.project2.maze.solvers.DeadEndFillerSolver;
import edu.project2.maze.solvers.RecursiveBackTrackerSolver;
import edu.project2.renderer.StringRenderer;
import edu.project2.renderer.TerminalStringRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static int EXAMPLE_SIZE = 51;

    private Main() {
        throw new IllegalStateException();
    }

    public static void main(String[] args) {
        StringRenderer renderer = new TerminalStringRenderer();
        var ellerMazeGenerator = new EllerGenerator();
        var dfsMazeGenerator = new RecursiveBackTrackerGenerator();

        var dfsSolver = new RecursiveBackTrackerSolver();
        var deadEndSolver = new DeadEndFillerSolver();

        Maze maze = ellerMazeGenerator.generateMaze(EXAMPLE_SIZE, EXAMPLE_SIZE);
        maze.markPath(dfsSolver.getPath(maze,
            new Coordinate(0, 0),
            new Coordinate(EXAMPLE_SIZE - 1, EXAMPLE_SIZE - 1)));
        LOGGER.info(System.lineSeparator() + renderer.render(maze));

        maze = dfsMazeGenerator.generateMaze(EXAMPLE_SIZE, EXAMPLE_SIZE);
        maze.markPath(deadEndSolver.getPath(maze,
            new Coordinate(0, 0),
            new Coordinate(EXAMPLE_SIZE - 1, EXAMPLE_SIZE - 1)));
        LOGGER.info(System.lineSeparator() + renderer.render(maze));
    }
}
