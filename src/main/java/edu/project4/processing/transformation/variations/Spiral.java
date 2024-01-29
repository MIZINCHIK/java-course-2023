package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Spiral implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = r(x, y);
        double rInverse = 1 / r;
        double theta = theta(x, y);
        return new Point(rInverse * (cos(theta) + sin(r)), rInverse * (sin(theta) - cos(r)));
    }
}
