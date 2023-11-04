package edu.hw2.task1;

public record Multiplication(Expr termA, Expr termB) implements Expr {
    public Multiplication {
        if (termA == null || termB == null) {
            throw new IllegalArgumentException("Null expressions can't be evaluated");
        }
    }

    public Multiplication(double termA, double termB) {
        this(new Constant(termA), new Constant(termB));
    }

    public Multiplication(double termA, Expr termB) {
        this(new Constant(termA), termB);
    }

    public Multiplication(Expr termA, double termB) {
        this(termA, new Constant(termB));
    }

    @Override
    public double evaluate() {
        return termA.evaluate() * termB.evaluate();
    }
}
