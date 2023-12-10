package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Swirl implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r2 = r2(x, y);
        return new Point(x * sin(r2) - y * cos(r2),
            x * cos(r2) + y * sin(r2));
    }
}
