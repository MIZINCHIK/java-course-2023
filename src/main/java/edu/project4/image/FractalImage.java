package edu.project4.image;

import edu.project4.shape.Point;
import edu.project4.shape.Rectangle;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        var data = new Pixel[width][height];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                data[column][row] = new Pixel();
            }
        }
        return new FractalImage(data, width, height);
    }

    public boolean inBound(int x, int y) {
        return x < width && y < height && x >= 0 && y >= 0;
    }

    public Pixel getPixel(int x, int y) {
        return data[x][y];
    }

    public void setPixel(Point point, Rectangle world, int r, int g, int b) {
        int x = (int) (point.x() / world.width() * width + width / 2);
        int y = (int) (point.y() / world.height() * height + height / 2);
        if (!inBound(x, y)) {
            return;
        }
        getPixel(x, y).modifyColor(r, g, b);
    }
}
