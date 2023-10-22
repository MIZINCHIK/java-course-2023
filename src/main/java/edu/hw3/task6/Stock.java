package edu.hw3.task6;

import org.jetbrains.annotations.NotNull;

public record Stock(double priceInDollars, String issuer) implements Comparable<Stock> {
    @Override
    public int compareTo(@NotNull Stock o) {
        return Double.compare(o.priceInDollars, priceInDollars);
    }
}
