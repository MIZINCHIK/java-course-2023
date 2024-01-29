package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class RandomObjectGenerator {
    private static final double NULL_CHANCE = 0.125;
    private static final int MAX_BYTE_ARRAY_SIZE = 100;
    private static final int MIN_BYTE_ARRAY_SIZE = 0;
    private static final String ILLEGAL_ACCESS = "Failed to access object's creation method";
    private static final String INVOKED_METHOD_EXCEPTION = "Invoked creation method threw an exception";
    private static final String NO_AVAILABLE_CONSTRUCTORS = "No available public constructors";
    private static final String NO_METHOD = "Provided method doesn't exist or is private";
    private static final String INCORRECT_METHOD_TYPE =
        "The provided method doesn't create an object of the sought type";
    private static final String INCORRECT_PARAMETER_TYPE =
        "Incorrect parameter type for its annotation";
    private final Random random;

    public RandomObjectGenerator() {
        this.random = ThreadLocalRandom.current();
    }

    public <T> T createObject(Class<T> objectClass) {
        return createObject(objectClass, false, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private <T> T createObject(Class<T> objectClass, boolean mayBeNull, double min, double max) {
        T result;
        if (mayBeNull && !objectClass.isPrimitive() && random.nextDouble() < NULL_CHANCE) {
            result = null;
        } else if (objectClass.equals(byte.class)) {
            result = (T) ((Byte) ((byte) random.nextInt(
                (int) Math.max(Byte.MIN_VALUE, min),
                (int) Math.min(Byte.MAX_VALUE, max)
            )));
        } else if (objectClass.equals(short.class)) {
            result = (T) ((Short) ((short) random.nextInt(
                (int) Math.max(Short.MIN_VALUE, min),
                (int) Math.min(Short.MAX_VALUE, max)
            )));
        } else if (objectClass.equals(int.class)) {
            result = (T) ((Integer) random.nextInt(
                (int) Math.max(Integer.MIN_VALUE, min),
                (int) Math.min(Integer.MAX_VALUE, max)
            ));
        } else if (objectClass.equals(long.class)) {
            result = (T) ((Long) random.nextLong(
                (long) Math.max(Long.MIN_VALUE, min),
                (long) Math.min(Long.MAX_VALUE, max)
            ));
        } else if (objectClass.equals(float.class)) {
            result = (T) ((Float) (random.nextFloat(
                (float) Math.max(Float.MIN_VALUE, min),
                (float) Math.min(Float.MAX_VALUE, max)
            )));
        } else if (objectClass.equals(double.class)) {
            result = (T) ((Double) (random.nextDouble(min, max)));
        } else if (objectClass.equals(char.class)) {
            result = (T) ((Character) ((char) random.nextInt(
                (int) Math.max(Character.MIN_VALUE, min),
                (int) Math.min(Character.MAX_VALUE, max)
            )));
        } else if (objectClass.equals(boolean.class)) {
            result = (T) ((Boolean) random.nextBoolean());
        } else if (objectClass.equals(byte[].class)) {
            result = (T) createRandomByteArray();
        } else if (objectClass.equals(String.class)) {
            result = (T) new String(createRandomByteArray());
        } else {
            result = createConstructedObject(objectClass);
        }
        return result;
    }

    private <T> T createConstructedObject(Class<T> objectClass) {
        var constructor = Arrays.stream(objectClass.getConstructors())
            .findAny()
            .orElseThrow(() -> new RuntimeException(NO_AVAILABLE_CONSTRUCTORS));
        return createObject(() -> constructor.newInstance(
            createRandomParameters(
                constructor.getParameterTypes(),
                constructor.getParameterAnnotations()
            )), objectClass);
    }

    public <T> T createObject(Class<T> objectClass, String methodName) {
        return createObject(objectClass, Arrays.stream(objectClass.getMethods())
            .filter(method -> method.getName().equals(methodName))
            .findAny()
            .orElseThrow(() -> new RuntimeException(NO_METHOD)));
    }

    public <T> T createObject(Class<T> objectClass, Method method) {
        return createObject(() -> method.invoke(objectClass, createRandomParameters(
            method.getParameterTypes(),
            method.getParameterAnnotations()
        )), objectClass);
    }

    private <T> T createObject(Callable<Object> creator, Class<?> objectClass) {
        try {
            var result = creator.call();
            if (objectClass.isInstance(result)) {
                return (T) result;
            } else {
                throw new RuntimeException(INCORRECT_METHOD_TYPE);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(ILLEGAL_ACCESS);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOKED_METHOD_EXCEPTION, e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Object[] createRandomParameters(Class<?>[] parameterTypes, Annotation[][] annotations) {
        int numberOfParameters = parameterTypes.length;
        Object[] result = new Object[numberOfParameters];
        for (int i = 0; i < numberOfParameters; i++) {
            var clazz = parameterTypes[i];
            var currentAnnotations = annotations[i];
            double min = -Double.MAX_VALUE;
            double max = Double.MAX_VALUE;
            boolean mayBeNull = true;
            for (var annotation : currentAnnotations) {
                var type = annotation.annotationType();
                if (type.equals(Min.class) || type.equals(Max.class)) {
                    if (cantBeConstrained(clazz)) {
                        throw new RuntimeException(INCORRECT_PARAMETER_TYPE);
                    }
                }
                if (type.equals(Min.class)) {
                    min = ((Min) annotation).value();
                } else if (type.equals(Max.class)) {
                    max = ((Max) annotation).value();
                } else if (type.equals(NotNull.class)) {
                    mayBeNull = false;
                }
            }
            result[i] = createObject(clazz, mayBeNull, min, max);
        }
        return result;
    }

    private byte[] createRandomByteArray() {
        byte[] byteArray = new byte[random.nextInt(MIN_BYTE_ARRAY_SIZE, MAX_BYTE_ARRAY_SIZE)];
        random.nextBytes(byteArray);
        return byteArray;
    }

    private boolean cantBeConstrained(Class<?> clazz) {
        return !List.of(byte.class, short.class, int.class, long.class, float.class, double.class, char.class)
            .contains(clazz);
    }
}
