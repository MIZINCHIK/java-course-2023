package edu.project4.processing.transformation;

import edu.project4.shape.Point;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class ContractiveAffineTransformation implements Transformation {
    private static final int CHANNEL_LIMIT = 256;
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;
    private final Color color;

    public ContractiveAffineTransformation(
        double a,
        double b,
        double c,
        double d,
        double e,
        double f,
        Color color
    ) {
        if (coefficientsAreIncorrect(a, b, d, e)) {
            throw new IllegalArgumentException("Transformation is not contractive");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.color = color;
    }

    public int getRed() {
        return color.getRed();
    }

    public int getGreen() {
        return color.getGreen();
    }

    public int getBlue() {
        return color.getBlue();
    }

    public static ContractiveAffineTransformation create() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Color color =
            new Color(random.nextInt(CHANNEL_LIMIT), random.nextInt(CHANNEL_LIMIT), random.nextInt(CHANNEL_LIMIT));
        double c = random.nextDouble(-1, 1);
        double f = random.nextDouble(-1, 1);
        double a;
        double b;
        double d;
        double e;
        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
        } while (coefficientsAreIncorrect(a, b, d, e));
        return new ContractiveAffineTransformation(a, b, c, d, e, f, color);
    }

    private static boolean coefficientsAreIncorrect(double a, double b, double d, double e) {
        double firstSum = a * a + d * d;
        double secondSum = b * b + e * e;
        double subtraction = a * e - b * d;
        return firstSum >= 1 || secondSum >= 1 || firstSum + secondSum >= 1 + subtraction * subtraction;
    }

    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        return new Point(x * a + y * b + c, x * d + y * e + f);
    }
}
