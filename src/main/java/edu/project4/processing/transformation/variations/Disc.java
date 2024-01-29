package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Disc implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double atanCoefficient = 1 / PI * theta(x, y);
        double piR = PI * r(x, y);
        return new Point(atanCoefficient * sin(piR),
            atanCoefficient * cos(piR));
    }
}
