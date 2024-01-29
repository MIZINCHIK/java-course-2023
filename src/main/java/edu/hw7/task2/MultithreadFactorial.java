package edu.hw7.task2;

import java.math.BigInteger;
import java.util.stream.Stream;

public class MultithreadFactorial {
    public static final String NEGATIVE_ARGUMENT = "Operation not defined for negative numbers";

    private MultithreadFactorial() {
        throw new IllegalStateException();
    }

    public static BigInteger calculateFactorial(int number) throws IllegalArgumentException {
        try {
            return Stream.iterate(BigInteger.ONE, BigInteger.ONE::add)
                .limit(number)
                .parallel()
                .reduce(BigInteger::multiply)
                .orElse(BigInteger.ONE);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NEGATIVE_ARGUMENT, e);
        }
    }
}
