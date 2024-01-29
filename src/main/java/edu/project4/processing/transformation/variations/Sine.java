package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.sin;

public class Sine implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(sin(point.x()), sin(point.y()));
    }
}
