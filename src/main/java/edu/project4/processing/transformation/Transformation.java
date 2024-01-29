package edu.project4.processing.transformation;

import edu.project4.shape.Point;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.sqrt;

public interface Transformation extends Function<Point, Point> {
    default double theta(double x, double y) {
        return atan(x / y);
    }

    default double phi(double x, double y) {
        return atan(y / x);
    }

    default double r(double x, double y) {
        return sqrt(x * x + y * y);
    }

    default double r2(double x, double y) {
        return x * x + y * y;
    }

    default double omega() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextBoolean() ? PI : 0;
    }

    default double psi() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextDouble();
    }
}
