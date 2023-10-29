package edu.hw4;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
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
            .max(Comparator.comparingInt(x -> x.name().length())).orElse(null);
    }

    public static Sex getMostAnimalsSex(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparingLong(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElse(null);
    }
}
