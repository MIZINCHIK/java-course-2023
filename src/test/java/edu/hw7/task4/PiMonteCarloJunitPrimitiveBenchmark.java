package edu.hw7.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import static edu.hw7.task4.PiMonteCarloCalculator.calculatePi;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PiMonteCarloJunitPrimitiveBenchmark {
    @Test
    @Order(1)
    @DisplayName("Calculating Pi 10_000_000 iterations 1 thread")
    void calculatePi_singleThread10000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(10_000_000, 1)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(2)
    @DisplayName("Calculating Pi 10_000_000 iterations 2 threads")
    void calculatePi_doubleThread10000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(10_000_000, 2)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(3)
    @DisplayName("Calculating Pi 10_000_000 iterations 4 threads")
    void calculatePi_quadrupleThread10000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(10_000_000, 4)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(4)
    @DisplayName("Calculating Pi 10_000_000 iterations 8 threads")
    void calculatePi_octupleThread10000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(10_000_000, 8)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(5)
    @DisplayName("Calculating Pi 100_000_000 iterations 1 thread")
    void calculatePi_singleThread100000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(100_000_000, 1)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(6)
    @DisplayName("Calculating Pi 100_000_000 iterations 2 threads")
    void calculatePi_doubleThread100000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(100_000_000, 2)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(7)
    @DisplayName("Calculating Pi 100_000_000 iterations 4 threads")
    void calculatePi_quadrupleThread100000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(100_000_000, 4)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(8)
    @DisplayName("Calculating Pi 100_000_000 iterations 8 threads")
    void calculatePi_octupleThread100000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(100_000_000, 8)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(9)
    @DisplayName("Calculating Pi 1_000_000_000 iterations 1 thread")
    void calculatePi_singleThread1000000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(1_000_000_000, 1)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(10)
    @DisplayName("Calculating Pi 1_000_000_000 iterations 2 threads")
    void calculatePi_doubleThread1000000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(1_000_000_000, 2)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(11)
    @DisplayName("Calculating Pi 1_000_000_000 iterations 4 threads")
    void calculatePi_quadrupleThread1000000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(1_000_000_000, 4)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }

    @Test
    @Order(12)
    @DisplayName("Calculating Pi 1_000_000_000 iterations 8 threads")
    void calculatePi_octupleThread1000000000Points_somewhatNear(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double start = System.nanoTime();
        System.out.print("Absolute error: ");
        System.out.println(Math.abs(Math.PI - calculatePi(1_000_000_000, 8)));
        double end = System.nanoTime();
        System.out.print("Elapsed time in nanoseconds: ");
        System.out.println(end - start);
        System.out.println();
    }
}
