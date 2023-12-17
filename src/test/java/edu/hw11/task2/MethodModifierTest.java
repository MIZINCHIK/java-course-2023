package edu.hw11.task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MethodModifierTest {
    private static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }

    private static class ArithmeticUtilsInterceptor {
        public static int multiply(int a, int b) {
            return a * b;
        }
    }

    @Test
    @DisplayName("Modifying a method in another class")
    void sum_whenModified_thenDifferentResult() throws NoSuchMethodException {
        Random random = ThreadLocalRandom.current();
        ArithmeticUtils utils = new ArithmeticUtils();
        for (int i = 0; i < 1; i++) {
            int first = random.nextInt();
            int second = random.nextInt();
            assertThat(utils.sum(first, second)).isEqualTo(first + second);
        }
        ByteBuddyAgent.install();
        try (var unloaded = new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(ElementMatchers
                .anyOf(ArithmeticUtils.class.getMethod(
                    "sum", int.class, int.class
                )))
            .intercept(MethodDelegation.to(ArithmeticUtilsInterceptor.class))
            .make()) {
            unloaded.load(
                ArithmeticUtils.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );
        }
        ArithmeticUtils utilsRedefined = new ArithmeticUtils();
        for (int i = 0; i < 1; i++) {
            int first = random.nextInt();
            int second = random.nextInt();
            assertThat(utilsRedefined.sum(first, second)).isEqualTo(first * second);
        }
    }
}
