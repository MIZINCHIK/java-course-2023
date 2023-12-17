package edu.hw11.task1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelloBuddyTest {
    @Test
    @DisplayName("Creating a simple class with overrided toString() method")
    void toString_whenClassCreated_thenCorrect()
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String toStringResult = "Hello, ByteBuddy!";
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value(toStringResult))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
        assertThat(dynamicType.getConstructor().newInstance().toString()).isEqualTo(toStringResult);
    }
}
