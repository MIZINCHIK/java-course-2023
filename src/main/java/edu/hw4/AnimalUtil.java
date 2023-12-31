package edu.hw4;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    public static Integer countAnimalWeightOverHeight(List<Animal> animals) {
        return (int) animals.stream()
            .filter(x -> x.weight() > x.height())
            .count();
    }

    public static List<Animal> getAnimalsNameSeveralWords(List<Animal> animals) {
        return animals.stream()
            .filter(x -> x.name().split(" ").length > 1)
            .collect(Collectors.toList());
    }

    public static Boolean isInListDogHigherKcm(List<Animal> animals, int k) {
        return animals.stream()
            .anyMatch(x -> x.type() == Type.DOG && x.height() > k);
    }

    public static Integer sumWeightAnimalsKtoLYearsOldInclusive(List<Animal> animals, int k, int l) {
        return animals.stream()
            .filter(x -> x.age() >= k && x.age() <= l)
            .map(Animal::weight)
            .reduce(0, Integer::sum);
    }

    public static List<Animal> getAnimalsSortedByTypeSexName(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(Collectors.toList());
    }

    public static Boolean spidersBiteMoreThanDogs(List<Animal> animals) {
        var portions = animals.stream()
            .filter(x -> x.type() == Type.SPIDER || x.type() == Type.DOG)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.averagingDouble(x -> x.bites() ? 1 : 0)));
        if (!portions.containsKey(Type.DOG) || !portions.containsKey(Type.SPIDER)) {
            return false;
        }
        return portions.get(Type.SPIDER) > portions.get(Type.DOG);
    }

    public static Animal getHeaviestFish(List<List<Animal>> animals) {
        return animals.stream()
            .flatMap(List::stream)
            .filter(x -> x.type() == Type.FISH)
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }

    public static Map<String, Set<ValidationError>> getValidationErrors(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(Animal::name, ValidationError::getAnimalErrors));
    }

    public static Map<String, String> getValidationErrorsPretty(List<Animal> animals) {
        var result = new HashMap<String, String>();
        getValidationErrors(animals).forEach(
            (key, value) -> result.put(key, value.stream()
                        .map(ValidationError::getField)
                        .sorted()
                        .collect(Collectors.joining(", "))));
        return result;
    }
}
