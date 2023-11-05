package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.generators.EllerMazeGenerator;
import edu.project2.maze.generators.RecursiveBackTrackerGenerator;
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
        var ellerMazeGenerator = new EllerMazeGenerator();
        Maze maze = new Maze(EXAMPLE_SIZE, EXAMPLE_SIZE);
        ellerMazeGenerator.fillMaze(maze);
        StringRenderer renderer = new TerminalStringRenderer();
        LOGGER.info(System.lineSeparator() + renderer.render(maze));
        var dfsMazeGenerator = new RecursiveBackTrackerGenerator();
        dfsMazeGenerator.fillMaze(maze);
        LOGGER.info(System.lineSeparator() + renderer.render(maze));
    }
}
