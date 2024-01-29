package edu.hw9.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static edu.hw9.task3.ConcurrentDfs.findWay;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ConcurrentDfsTest {
    CellType[][] maze;

    @BeforeEach
    void createMaze() {
        maze = new CellType[11][11];
        maze[0] = new CellType[]{CellType.WAY, CellType.WAY, CellType.WAY, CellType.NO_WAY,
            CellType.WAY, CellType.WAY, CellType.WAY, CellType.WAY, CellType.WAY,
            CellType.WAY, CellType.WAY};
        maze[1] = new CellType[]{CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.NO_WAY,
            CellType.WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[2] = new CellType[]{CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.WAY,
            CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.WAY, CellType.WAY,
            CellType.WAY, CellType.WAY};
        maze[3] = new CellType[]{CellType.WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY,
            CellType.NO_WAY, CellType.NO_WAY, CellType.WAY, CellType.NO_WAY, CellType.NO_WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[4] = new CellType[]{CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.WAY,
            CellType.WAY, CellType.WAY, CellType.WAY, CellType.NO_WAY, CellType.WAY,
            CellType.WAY, CellType.WAY};
        maze[5] = new CellType[]{CellType.NO_WAY, CellType.NO_WAY, CellType.WAY, CellType.NO_WAY,
            CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[6] = new CellType[]{CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.WAY,
            CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.WAY, CellType.WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[7] = new CellType[]{CellType.WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY,
            CellType.WAY, CellType.NO_WAY, CellType.WAY, CellType.NO_WAY, CellType.WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[8] = new CellType[]{CellType.WAY, CellType.WAY, CellType.WAY, CellType.NO_WAY,
            CellType.WAY, CellType.WAY, CellType.WAY, CellType.NO_WAY, CellType.WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[9] = new CellType[]{CellType.NO_WAY, CellType.NO_WAY, CellType.WAY, CellType.NO_WAY,
            CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.NO_WAY, CellType.WAY,
            CellType.NO_WAY, CellType.WAY};
        maze[10] = new CellType[]{CellType.WAY, CellType.WAY, CellType.WAY, CellType.WAY,
            CellType.WAY, CellType.WAY, CellType.WAY, CellType.WAY, CellType.WAY,
            CellType.NO_WAY, CellType.WAY};
    }

    @Test
    @DisplayName("Searching a way in an incorrect maze or when path ends are incorrect")
    void findWay_whenIncorrectInput_thenEmptyList() {
        CellType[][] maze = new CellType[2][2];
        maze[0][0] = CellType.NO_WAY;
        maze[0][1] = CellType.WAY;
        maze[1][0] = CellType.WAY;
        maze[1][1] = CellType.NO_WAY;
        assertThat(findWay(maze, new Point(-1, 0), new Point(0, 1))).isEqualTo(List.of());
        assertThat(findWay(maze, new Point(0, 1), new Point(-1, 0))).isEqualTo(List.of());
        assertThat(findWay(new CellType[0][0], new Point(0, 1), new Point(1, 0))).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Searching a way in a maze from wall or to wall")
    void findWay_whenEndsInWalls_thenEmptyList() {
        assertIterableEquals(List.of(),
            findWay(maze, new Point(1, 1), new Point(0, 0)));
        assertIterableEquals(List.of(),
            findWay(maze, new Point(0, 0), new Point(1, 1)));
        CellType[][] maze = new CellType[2][2];
        maze[0][0] = CellType.NO_WAY;
        maze[0][1] = CellType.WAY;
        maze[1][0] = CellType.WAY;
        maze[1][1] = CellType.NO_WAY;
        assertThat(findWay(maze, new Point(0, 0), new Point(0, 1))).isEqualTo(List.of());
        assertThat(findWay(maze, new Point(0, 1), new Point(1, 1))).isEqualTo(List.of());
        assertThat(findWay(maze, new Point(0, 0), new Point(1, 1))).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Searching a way in a maze when there's no way")
    void findWay_whenNoWay_thenEmptyList() {
        maze[0][1] = CellType.NO_WAY;
        maze[1][0] = CellType.NO_WAY;
        assertIterableEquals(List.of(),
            findWay(maze, new Point(0, 0), new Point(0, 2)));

    }

    @Test
    @DisplayName("Searching a way in a maze when there is a way")
    void findWay_whenIsWay_thenCorrect() {
        var real = findWay(maze, new Point(0, 0), new Point(10, 10));
        var expected = new ArrayList<Point>();
        expected.add(new Point(0, 0));
        expected.add(new Point(0, 1));
        expected.add(new Point(0, 2));
        expected.add(new Point(1, 2));
        expected.add(new Point(2, 2));
        expected.add(new Point(2, 3));
        expected.add(new Point(2, 4));
        expected.add(new Point(1, 4));
        expected.add(new Point(0, 4));
        expected.add(new Point(0, 5));
        expected.add(new Point(0, 6));
        expected.add(new Point(0, 7));
        expected.add(new Point(0, 8));
        expected.add(new Point(0, 9));
        expected.add(new Point(0, 10));
        expected.add(new Point(1, 10));
        expected.add(new Point(2, 10));
        expected.add(new Point(3, 10));
        expected.add(new Point(4, 10));
        expected.add(new Point(5, 10));
        expected.add(new Point(6, 10));
        expected.add(new Point(7, 10));
        expected.add(new Point(8, 10));
        expected.add(new Point(9, 10));
        expected.add(new Point(10, 10));
        assertIterableEquals(expected, real);
    }
}
