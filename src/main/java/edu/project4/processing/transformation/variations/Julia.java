package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Julia implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double rSqrt = sqrt(r(x, y));
        double omega = omega();
        double thetaHalf = theta(x, y) / 2;
        return new Point(rSqrt * cos(thetaHalf + omega), rSqrt * sin(thetaHalf + omega));
    }
}
