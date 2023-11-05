package edu.project2.renderer;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;

public class TerminalStringRenderer implements StringRenderer {
    private static final String WALL_SYMBOL = "▉▉▉";
    private static final String PASSAGE_SYMBOL = "   ";
    private static final String PATH_SYMBOL = ".";
    private static final String OFF_LIMITS_SYMBOL = "⚠";
    private static final String NONE_SYMBOL = "∅";

    @Override
    public String render(Maze maze) {
        StringBuilder stringBuilder = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        int height = maze.getHeight();
        int width = maze.getWidth();
        String border = WALL_SYMBOL.repeat(Math.max(0, width + 2));
        stringBuilder.append(border);
        stringBuilder.append(lineSeparator);
        for (int row = 0; row < height; row++) {
            stringBuilder.append(WALL_SYMBOL);
            for (int column = 0; column < width; column++) {
                stringBuilder.append(render(maze.getCell(new Coordinate(row, column))));
            }
            stringBuilder.append(WALL_SYMBOL);
            stringBuilder.append(lineSeparator);
        }
        stringBuilder.append(border);
        return stringBuilder.toString();
    }

    @Override
    public String render(CellType type) {
        return switch(type) {
            case WALL -> WALL_SYMBOL;
            case PASSAGE -> PASSAGE_SYMBOL;
            case PATH -> PATH_SYMBOL;
            case OFF_LIMITS -> OFF_LIMITS_SYMBOL;
            case NONE -> NONE_SYMBOL;
        };
    }
}
