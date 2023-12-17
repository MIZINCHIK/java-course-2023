package edu.project5;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import static java.lang.invoke.LambdaMetafactory.metafactory;

@State(Scope.Thread)
public class ReflectionBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(5)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }

    @FunctionalInterface
    public interface NameGetter {
        String name(Student student);
    }

    public record Student(String name, String surname) {}

    private Student student;
    private Method method;
    private MethodHandle handle;
    private NameGetter dynamicStudent;

    @Setup
    public void setup()
        throws Throwable {
        student = new Student("Alexander", "Biryukov");
        method = Student.class.getMethod("name");
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        handle = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));
        dynamicStudent = (NameGetter) metafactory(lookup, "name",
            MethodType.methodType(NameGetter.class),
            MethodType.methodType(String.class, Student.class),
            handle,
            MethodType.methodType(String.class, Student.class)
        ).getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        Object name = method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandles(Blackhole bh) throws Throwable {
        Object name = handle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactory(Blackhole bh) throws Throwable {
        String name = dynamicStudent.name(student);
        bh.consume(name);
    }
}
