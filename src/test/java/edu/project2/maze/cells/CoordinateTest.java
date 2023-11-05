package edu.project2.maze.cells;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CoordinateTest {
    @Test
    @DisplayName("Neighbours of the point with a 0 step is the point itself")
    void getNeighbours_zeroStep_pointItself() {
        var point = new Coordinate(0, 0);
        var expected = new HashSet<Coordinate>();
        expected.add(point);
        var real = point.getNeighbours(0);
        assertThat(real.size()).isEqualTo(1);
        assertIterableEquals(expected, real);
    }

    @Test
    @DisplayName("Neighbours of the point with a positive step are 4 points in n steps away from it on each axis")
    void getNeighbours_positiveStep_4Points() {
        var point = new Coordinate(0, 0);
        var expected = new HashSet<Coordinate>();
        expected.add(new Coordinate(1, 0));
        expected.add(new Coordinate(0, 1));
        expected.add(new Coordinate(-1, 0));
        expected.add(new Coordinate(0, -1));
        var real = point.getNeighbours(1);
        assertThat(real.size()).isEqualTo(4);
        assertIterableEquals(expected, real);
    }

    @Test
    @DisplayName("Neighbours of the point with a N step are same as with -N step")
    void getNeighbours_negativeStep_sameAsOpposite() {
        var point = new Coordinate(0, 0);
        assertIterableEquals(point.getNeighbours(-4), point.getNeighbours(4));
    }
}
