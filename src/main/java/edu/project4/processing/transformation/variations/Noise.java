package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Noise implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double psi1 = psi();
        double psi2 = psi();
        return new Point(psi1 * x * cos(2 * PI * psi2),
            psi1 * y * sin(2 * PI * psi2));
    }
}
