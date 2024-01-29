package edu.project4.image;

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

    public Pixel getPixel(int x, int y) {
        return data[x][y];
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        getPixel(x, y).modifyColor(r, g, b);
    }
}
