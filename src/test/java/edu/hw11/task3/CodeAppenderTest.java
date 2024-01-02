package edu.hw11.task3;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CodeAppenderTest {
    enum Fibonacci implements StackManipulation {
        INSTANCE;

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 0);
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, 2);
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, 4);
            Label start = new Label();
            Label end = new Label();
            methodVisitor.visitLabel(start);
            methodVisitor.visitFrame(
                Opcodes.F_NEW,
                4,
                new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.LONG, Opcodes.LONG},
                0,
                null
            );
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, end);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 1);
            methodVisitor.visitVarInsn(Opcodes.LLOAD, 2);
            methodVisitor.visitVarInsn(Opcodes.LLOAD, 4);
            methodVisitor.visitInsn(Opcodes.DUP2_X2);
            methodVisitor.visitInsn(Opcodes.LADD);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, 4);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, 2);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, start);
            methodVisitor.visitLabel(end);
            methodVisitor.visitFrame(
                Opcodes.F_NEW,
                4,
                new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.LONG, Opcodes.LONG},
                0,
                null
            );
            methodVisitor.visitVarInsn(Opcodes.LLOAD, 2);
            return new Size(2, 8);
        }
    }

    enum FibonacciMethod implements ByteCodeAppender {
        INSTANCE;

        @Override
        public Size apply(
            MethodVisitor methodVisitor,
            Implementation.Context implementationContext,
            MethodDescription instrumentedMethod
        ) {
            if (!instrumentedMethod.getReturnType().asErasure().represents(long.class)) {
                throw new IllegalArgumentException(instrumentedMethod + " must return int");
            }
            StackManipulation.Size operandStackSize = new StackManipulation.Compound(
                Fibonacci.INSTANCE,
                MethodReturn.LONG
            ).apply(methodVisitor, implementationContext);
            return new Size(
                operandStackSize.getMaximalSize(),
                instrumentedMethod.getStackSize() + 8
            );
        }
    }

    enum FibonacciImplementation implements Implementation {

        INSTANCE;

        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return FibonacciMethod.INSTANCE;
        }
    }

    public abstract static class FibonacciAbstract {
        public abstract long fib(int n);
    }

    @Test
    @DisplayName("Modifying a method in another class")
    void sum_whenModified_thenDifferentResult()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        FibonacciAbstract fibonacci;
        try (var unloaded = new ByteBuddy()
            .with(TypeValidation.DISABLED)
            .subclass(FibonacciAbstract.class)
            .method(ElementMatchers.anyOf(FibonacciAbstract.class.getMethod("fib", int.class)))
            .intercept(FibonacciImplementation.INSTANCE)
            .make()) {
            fibonacci = unloaded
                .load(FibonacciAbstract.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
        }
        assertThat(fibonacci.fib(0)).isEqualTo(0);
        assertThat(fibonacci.fib(1)).isEqualTo(1);
        assertThat(fibonacci.fib(2)).isEqualTo(1);
        assertThat(fibonacci.fib(3)).isEqualTo(2);
        assertThat(fibonacci.fib(4)).isEqualTo(3);
        assertThat(fibonacci.fib(5)).isEqualTo(5);
        assertThat(fibonacci.fib(6)).isEqualTo(8);
        assertThat(fibonacci.fib(19)).isEqualTo(4181);
        assertThat(fibonacci.fib(92)).isEqualTo(7540113804746346429L);
    }
}
