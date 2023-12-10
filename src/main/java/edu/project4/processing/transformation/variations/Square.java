package edu.project4.processing.transformation.variations;

import edu.project4.processing.transformation.Transformation;
import edu.project4.shape.Point;

public class Square implements Transformation {
    @Override
    @SuppressWarnings("MagicNumber")
    public Point apply(Point point) {
        return new Point(psi() - 0.5, psi() - 0.5);
    }
}
