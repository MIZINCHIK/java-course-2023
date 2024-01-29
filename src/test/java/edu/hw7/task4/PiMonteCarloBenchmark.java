package edu.hw7.task4;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import static edu.hw7.task4.PiMonteCarloCalculator.calculatePi;

public class PiMonteCarloBenchmark {
    public static void main(String[] args) throws Exception {
        Main.main(args);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_singleThread10000000Points_somewhatNear() {
        calculatePi(10_000_000, 1);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_doubleThread10000000Points_somewhatNear() {
        calculatePi(10_000_000, 2);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_quadrupleThread10000000Points_somewhatNear() {
        calculatePi(10_000_000, 4);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_octupleThread10000000Points_somewhatNear() {
        calculatePi(10_000_000, 8);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_singleThread100000000Points_somewhatNear() {
        calculatePi(100_000_000, 1);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_doubleThread100000000Points_somewhatNear() {
        calculatePi(100_000_000, 2);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_quadrupleThread100000000Points_somewhatNear() {
        calculatePi(100_000_000, 4);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_octupleThread100000000Points_somewhatNear() {
        calculatePi(100_000_000, 8);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_singleThread1000000000Points_somewhatNear() {
        calculatePi(1_000_000_000, 1);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_doubleThread1000000000Points_somewhatNear() {
        calculatePi(1_000_000_000, 2);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_quadrupleThread1000000000Points_somewhatNear() {
        calculatePi(1_000_000_000, 4);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void calculatePi_octupleThread1000000000Points_somewhatNear() {
        calculatePi(1_000_000_000, 8);
    }
}
