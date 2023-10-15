package edu.hw2.Task1;

public record Addition(Expr termA, Expr termB) implements Expr {
    public Addition {
        if (termA == null || termB == null) {
            throw new IllegalArgumentException("Null expressions can't be evaluated");
        }
    }

    public Addition(double termA, double termB) {
        this(new Constant(termA), new Constant(termB));
    }

    public Addition(double termA, Expr termB) {
        this(new Constant(termA), termB);
    }

    public Addition(Expr termA, double termB) {
        this(termA, new Constant(termB));
    }

    @Override
    public double evaluate() {
        return termA.evaluate() + termB.evaluate();
    }
}
