package edu.project2.renderer;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TerminalStringRendererTest {
    @Test
    @DisplayName("Maze is rendered correctly")
    void render_maze_correctString() {
        Maze maze = new Maze(5, 5);
        maze.setRow(0,
            new CellType[]{CellType.PASSAGE, CellType.WALL,
                CellType.NONE, CellType.PATH, CellType.OFF_LIMITS});
        maze.setRow(1,
            new CellType[]{CellType.PASSAGE, CellType.WALL,
                CellType.NONE, CellType.PATH, CellType.OFF_LIMITS});
        maze.setRow(2,
            new CellType[]{CellType.PASSAGE, CellType.WALL,
                CellType.NONE, CellType.PATH, CellType.OFF_LIMITS});
        maze.setRow(3,
            new CellType[]{CellType.PASSAGE, CellType.WALL,
                CellType.NONE, CellType.PATH, CellType.OFF_LIMITS});
        maze.setRow(4,
            new CellType[]{CellType.PASSAGE, CellType.WALL,
                CellType.NONE, CellType.PATH, CellType.OFF_LIMITS});
        String start = "▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉";
        String expectedAtom = "▉▉▉   ▉▉▉∅∅∅...⚠⚠⚠▉▉▉" + System.lineSeparator();
        StringBuilder builder = new StringBuilder();
        builder.append(start);
        builder.append(System.lineSeparator());
        for (int i = 0; i < 5; i++) {
            builder.append(expectedAtom);
        }
        builder.append(start);
        assertThat(new TerminalStringRenderer().render(maze)).isEqualTo(builder.toString());
    }
}
