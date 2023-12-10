package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;

public class Horseshoe implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double rInverse = 1 / r(x, y);
        return new Point(rInverse * (x - y) * (x + y), rInverse * 2 * x * y);
    }
}
