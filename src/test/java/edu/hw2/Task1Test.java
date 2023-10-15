package edu.hw2;

import edu.hw2.Task1.Addition;
import edu.hw2.Task1.Constant;
import edu.hw2.Task1.Exponent;
import edu.hw2.Task1.Multiplication;
import edu.hw2.Task1.Negate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Task1Test {
    @Test
    @DisplayName("Evaluation of a constant results in the value of its initial construction argument")
    void constant() {
        var number = 1e100;
        var constant = new Constant(number);
        assertThat(constant.evaluate()).isEqualTo(number);
    }

    @Test
    @DisplayName("Negation negates constant values")
    void negation() {
        var number = 1e100;
        var constant = new Constant(number);
        assertThat(new Negate(constant).evaluate()).isEqualTo(-number);
    }

    @Test
    @DisplayName("Double negation doesn't change the evaluation result")
    void doubleNegation() {
        var constant = new Constant(1000);
        assertThat(new Negate(new Negate(constant)).evaluate()).isEqualTo(constant.evaluate());
    }

    @Test
    @DisplayName("Exponent performs exponentiation")
    void exponentiation() {
        var expression = new Exponent(10, 100);
        assertThat(expression.evaluate()).isEqualTo(1e100);
    }

    @Test
    @DisplayName("Exponent gives a correct results in case of a negative base and an integer power")
    void exponentiationNegativeInteger() {
        var expression = new Exponent(-2, 2);
        assertThat(expression.evaluate()).isEqualTo(4);
    }

    @Test
    @DisplayName("Exponent gives NaN (not a number) in case of a negative base and a fraction power")
    void exponentiationNegativeFraction() {
        var expression = new Exponent(-2, 2.1);
        assertThat(expression.evaluate()).isNaN();
    }

    @Test
    @DisplayName("Addition works as arithmetic addition")
    void addition() {
        var expression = new Addition(new Negate(2), 1000);
        assertThat(expression.evaluate()).isEqualTo(998);
    }

    @Test
    @DisplayName("Multiplication works as arithmetic multiplication")
    void multiplication() {
        var expression = new Multiplication(new Negate(2), 1000);
        assertThat(expression.evaluate()).isEqualTo(-2000);
    }

    @Test
    @DisplayName("Well-rounded expression.")
    void reference() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));
        assertThat(res.evaluate()).isEqualTo(37);
    }

    @Test
    @DisplayName("Constructing Expressions from primitives is allowed")
    void variousConstructors() {
        var two = new Constant(2);
        var four = new Constant(4);
        var sum = new Addition(new Addition(2, four), new Addition(two, 4));
        var mult =
            new Multiplication(new Multiplication(sum, new Negate(1).evaluate()).evaluate(), new Multiplication(1, 1));
        var exp = new Exponent(mult.evaluate(), two);
        var res = new Addition(exp.evaluate(), 1);
        assertThat(res.evaluate()).isEqualTo(145);
    }

    @Test
    @DisplayName("Null arguments in Negate constructor are not allowed")
    void nullArgumentNegate() {
        assertThatThrownBy(() -> new Negate(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Null arguments in Exponent constructor are not allowed")
    void nullArgumentExponent() {
        assertThatThrownBy(() -> new Exponent(null, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Exponent(1, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Null arguments in Addition constructor are not allowed")
    void nullArgumentAddition() {
        assertThatThrownBy(() -> new Addition(null, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Addition(1, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Null arguments in Multiplication constructor are not allowed")
    void nullArgumentMultiplication() {
        assertThatThrownBy(() -> new Multiplication(null, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Multiplication(1, null)).isInstanceOf(IllegalArgumentException.class);
    }
}
