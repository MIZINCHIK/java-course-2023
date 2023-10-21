package edu.hw2.task1;

public record Exponent(Expr base, Expr power) implements Expr {
    public Exponent {
        if (base == null || power == null) {
            throw new IllegalArgumentException("Null expressions can't be evaluated");
        }
    }

    public Exponent(double base, double power) {
        this(new Constant(base), new Constant(power));
    }

    public Exponent(double base, Expr power) {
        this(new Constant(base), power);
    }

    public Exponent(Expr base, double power) {
        this(base, new Constant(power));
    }

    @Override
    public double evaluate() {
        return Math.pow(base.evaluate(), power.evaluate());
    }
}
