package edu.hw4;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class AnimalUtil {
    private AnimalUtil() {
        throw new IllegalStateException();
    }

    public static List<Animal> sortHeightAscending(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .collect(Collectors.toList());
    }

    public static List<Animal> getKTopWeighing(List<Animal> animals, long k) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    public static Map<Type, Integer> getEachTypeAmount(List<Animal> animals) {
        Map<Type, Integer> result = animals.stream()
            .collect(Collectors.groupingBy(Animal::type,
                Collectors.summingInt(x -> 1)));
        EnumSet.allOf(Type.class)
            .forEach(key -> result.computeIfAbsent(key, x -> 0));
        return result;
    }

    public static Animal getLongestNameAnimal(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(x -> x.name().length()))
            .orElse(null);
    }

    public static Sex getMostAnimalsSex(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    public static Map<Type, Animal> getEachTypeHeaviestAnimal(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(Animal::type, x -> x,
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))));
    }

    public static Animal getKthOldestAnimal(List<Animal> animals, long k) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    public static Optional<Animal> getHeaviestAnimalUnderKcmHeight(List<Animal> animals, int k) {
        return animals.stream()
            .filter(x -> x.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static Integer countPaws(List<Animal> animals) {
        return animals.stream()
            .map(Animal::paws)
            .reduce(0, Integer::sum);
    }

    public static List<Animal> getAnimalsAgeNotEqualPaws(List<Animal> animals) {
        return animals.stream()
            .filter(x -> x.age() != x.paws())
            .collect(Collectors.toList());
    }

    @SuppressWarnings("MagicNumber")
    public static List<Animal> getAnimalsCanBiteHigher100cm(List<Animal> animals) {
        return animals.stream()
            .filter(x -> (x.bites() && x.height() > 100))
            .collect(Collectors.toList());
    }

    @NotNull
    public static Integer countAnimalWeightOverHeight(List<Animal> animals) {
        return (int) animals.stream()
            .filter(x -> x.weight() > x.height())
            .count();
    }
}
