package edu.hw7.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static edu.hw7.task2.MultithreadFactorial.calculateFactorial;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MultithreadFactorialTest {
    @Test
    @DisplayName("Negative amount of increments results in no iterations")
    void calculateFactorial_negativeNumber_illegalArgumentException() {
        assertThatThrownBy(() -> calculateFactorial(-1)).
            isInstanceOf(IllegalArgumentException.class).message().isEqualTo("Operation not defined for negative numbers");
    }

    @Test
    @DisplayName("Factorial of 0 is 1")
    void calculateFactorial_0_1() {
        assertThat(calculateFactorial(0)).isEqualTo(BigInteger.ONE);
    }

    @Test
    @DisplayName("Factorial of 1 is 1")
    void calculateFactorial_1_1() {
        assertThat(calculateFactorial(1)).isEqualTo(BigInteger.ONE);
    }

    @Test
    @DisplayName("Factorial of 10 is 3628800")
    void calculateFactorial_10_3628800() {
        assertThat(calculateFactorial(10)).isEqualTo(BigInteger.valueOf(3628800));
    }

    @Test
    @DisplayName("Factorial of 20 is 2432902008176640000")
    void calculateFactorial_20_2432902008176640000() {
        assertThat(calculateFactorial(20)).isEqualTo(new BigInteger("2432902008176640000"));
    }
}
