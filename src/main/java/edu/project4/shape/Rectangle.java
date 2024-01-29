package edu.project4.shape;

import java.util.Random;
import static java.lang.Math.abs;

public record Rectangle(double width, double height) {
    public boolean contains(Point point) {
        return 2 * abs(point.x()) <= width && 2 * abs(point.y()) < height;
    }

    public Point getRandomPoint(Random random) {
        return new Point(random.nextDouble(-width / 2, width / 2), random.nextDouble(-height / 2, height / 2));
    }
}
