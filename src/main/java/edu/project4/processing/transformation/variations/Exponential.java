package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.sin;

public class Exponential implements Transformation {
    @Override
    public Point apply(Point point) {
        double coefficient = exp(point.x() - 1);
        double piY = PI * point.y();
        return new Point(coefficient * cos(piY), coefficient * sin(piY));
    }
}
