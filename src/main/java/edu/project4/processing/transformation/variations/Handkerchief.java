package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Handkerchief implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = r(x, y);
        double theta = theta(x, y);
        return new Point(r * sin(theta + r), r * cos(theta - r));
    }
}
