package edu.project4.files;

import edu.project4.image.FractalImage;
import edu.project4.image.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {
        throw new IllegalStateException();
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage bufferedImage = new BufferedImage(
            image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        int width = image.width();
        int height = image.height();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Pixel pixel = image.getPixel(column, row);
                Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB());
                bufferedImage.setRGB(column, row, color.getRGB());
            }
        }
        try {
            ImageIO.write(bufferedImage, format.name(), filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
