package edu.hw2.Task1;

public record Negate(Expr expr) implements Expr {
    public Negate {
        if (expr == null) {
            throw new IllegalArgumentException("Null expressions can't be evaluated");
        }
    }

    public Negate(double val) {
        this(new Constant(val));
    }

    @Override
    public double evaluate() {
        return -expr.evaluate();
    }
}
