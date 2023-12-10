package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Heart implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = r(x, y);
        double thetaR = r * theta(x, y);
        return new Point(
            r * sin(thetaR),
            -r * cos(thetaR)
        );
    }
}
