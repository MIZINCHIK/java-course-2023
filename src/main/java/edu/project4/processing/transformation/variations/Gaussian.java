package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Gaussian implements Transformation {
    @Override
    @SuppressWarnings("MagicNumber")
    public Point apply(Point point) {
        double psi1 = 0;
        for (int i = 0; i < 4; i++) {
            psi1 += psi();
        }
        psi1 -= 2;
        double psi2 = psi();
        return new Point(psi1 * cos(2 * PI * psi2),
            psi1 * sin(2 * PI * psi2));
    }
}
