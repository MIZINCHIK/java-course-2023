package edu.project4.shape;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public record Point(double x, double y) {
    public static Point rotate(Point point, double angle) {
        double x = point.x();
        double y = point.y();
        return new Point(x * cos(angle) - y * sin(angle), x * sin(angle) + y * cos(angle));
    }
}
