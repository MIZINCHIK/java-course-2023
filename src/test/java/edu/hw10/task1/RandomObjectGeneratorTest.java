package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RandomObjectGeneratorTest {
    private static final int UPPER_BORDER = 15;
    private static final int LOWER_BORDER = 10;
    static class TestClass {
        private String name;
        private Integer number;
        private byte[] byteArray;

        public TestClass(@NotNull String name, @Min(LOWER_BORDER) @Max(UPPER_BORDER) int number, byte[] byteArray) {
            this.name = name;
            this.number = number;
            this.byteArray = byteArray;
        }

        public static TestClass create(String name, int number, byte[] byteArray) {
            return new TestClass(name, number, byteArray);
        }
    }

    public record TestRecord(String name, int number, byte[] byteArray) {
    }

    @Test
    @DisplayName("Creating primitives")
    void createObject_whenPrimitives_thenCorrect() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        assertDoesNotThrow(() -> {
            byte randomByte = generator.createObject(byte.class);
            short randomShort = generator.createObject(short.class);
            int randomInt = generator.createObject(int.class);
            long randomLong = generator.createObject(long.class);
            float randomFloat = generator.createObject(float.class);
            double randomDouble = generator.createObject(double.class);
            char randomChar = generator.createObject(char.class);
            boolean randomBoolean = generator.createObject(boolean.class);
        });
    }

    @RepeatedTest(10)
    @DisplayName("Creating non-primitive objects via constructors")
    void createObject_whenNotPrimitivesConstructed_thenCorrect() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        byte[] byteArray = generator.createObject(byte[].class);
        assertThat(byteArray != null).isTrue();
        String string = generator.createObject(String.class);
        assertThat(string != null).isTrue();
        TestClass testClass = generator.createObject(TestClass.class);
        assertThat(testClass != null).isTrue();
        TestRecord testRecord = generator.createObject(TestRecord.class);
        assertThat(testRecord != null).isTrue();
    }

    @RepeatedTest(10)
    @DisplayName("Creating non-primitive objects via factory methods")
    void createObject_whenNotPrimitivesFactoryCreated_thenCorrect() throws NoSuchMethodException {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        TestClass testClass = generator.createObject(TestClass.class, "create");
        assertThat(testClass != null).isTrue();
        testClass = generator.createObject(
            TestClass.class,
            TestClass.class.getMethod("create", String.class, int.class, byte[].class)
        );
        assertThat(testClass != null).isTrue();
    }

    @RepeatedTest(100)
    @DisplayName("Creating non-primitive objects via constructors with constrained parameters")
    void createObject_whenNotPrimitivesCreatedWithConstrains_thenCorrect() throws NoSuchMethodException {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        TestClass testClass = generator.createObject(TestClass.class);
        assertThat(testClass != null).isTrue();
        assertThat(testClass.number >= LOWER_BORDER).isTrue();
        assertThat(testClass.number <= UPPER_BORDER).isTrue();
        assertThat(testClass.name != null).isTrue();
    }
}
