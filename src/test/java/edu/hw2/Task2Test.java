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
    @DisplayName("LSP holds for area calculations")
    void lspHoldsArea() {
        Rectangle squareAsRectangle = new Square(4);
        Square squareAsSquare = new Square(4);
        assertThat(squareAsRectangle.area()).isEqualTo(squareAsSquare.area());
    }

    @Test
    @DisplayName("LSP holds for height setting")
    void lspHoldsSetHeight() {
        Rectangle squareAsRectangle = new Square(4);
        Square squareAsSquare = new Square(4);
        assertThat(squareAsRectangle.setHeight(5).getWidth()).isEqualTo(squareAsSquare.setHeight(5).getWidth());
    }

    @Test
    @DisplayName("LSP holds for width setting")
    void lspHoldsSetWidth() {
        Rectangle squareAsRectangle = new Square(4);
        Square squareAsSquare = new Square(4);
        assertThat(squareAsRectangle.setWidth(5).getHeight()).isEqualTo(squareAsSquare.setWidth(5).getHeight());
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
