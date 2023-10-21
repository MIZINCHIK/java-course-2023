package edu.hw2;

import edu.hw2.task2.Rectangle;
import edu.hw2.task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("To maintain square invariant of width and height equality its constructor omits extra parameters")
    void lessParameters() {
        var square = new Square(4);
        assertThat(square.getHeight()).isEqualTo(square.getWidth()).isEqualTo(4);
    }

    @Test
    @DisplayName("LSP holds")
    void LSP() {
        Rectangle squareAsRectangle = new Square(4);
        Square squareAsSquare = new Square(4);
        assertThat(squareAsRectangle.area()).isEqualTo(squareAsSquare.area());
    }

    @Test
    @DisplayName("Rectangle and Square are immutable")
    void immutability() {
        var square = new Square(4);
        var rectangle = square.setHeight(5);
        assertThat(square.area()).isEqualTo(16);
        assertThat(rectangle.area()).isEqualTo(20);
        assertThat(rectangle.setWidth(5).area()).isEqualTo(25);
    }
}
