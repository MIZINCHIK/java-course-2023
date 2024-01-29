package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.cosh;
import static java.lang.Math.sin;
import static java.lang.Math.sinh;

public class Cosine implements Transformation {
    @Override
    public Point apply(Point point) {
        double piX = PI * point.x();
        double y = point.y();
        return new Point(cos(piX) * cosh(y),
            -sin(piX) * sinh(y));
    }
}
