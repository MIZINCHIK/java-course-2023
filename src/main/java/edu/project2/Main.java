package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.generators.EllerMazeGenerator;
import edu.project2.renderer.StringRenderer;
import edu.project2.renderer.TerminalStringRenderer;

public class Main {
    public static void main(String[] args) {
        var mazeGenerator = new EllerMazeGenerator();
        Maze maze = new Maze(51, 51);
        mazeGenerator.fillMaze(maze);
        StringRenderer renderer = new TerminalStringRenderer();
        System.out.println(renderer.render(maze));
    }
}
