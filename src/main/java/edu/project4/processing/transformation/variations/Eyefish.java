package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;

public class Eyefish implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double coefficient = 2 / (r(x, y) + 1);
        return new Point(coefficient * x, coefficient * y);
    }
}
