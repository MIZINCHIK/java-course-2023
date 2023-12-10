package edu.hw9.task3;

public record Point(int x, int y) {
    public boolean inConstraints(int width, int height) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }
}
