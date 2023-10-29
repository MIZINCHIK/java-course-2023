package edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static edu.hw4.AnimalUtil.countAnimalWeightOverHeight;
import static edu.hw4.AnimalUtil.countPaws;
import static edu.hw4.AnimalUtil.getAnimalsAgeNotEqualPaws;
import static edu.hw4.AnimalUtil.getAnimalsCanBiteHigher100cm;
import static edu.hw4.AnimalUtil.getAnimalsNameSeveralWords;
import static edu.hw4.AnimalUtil.getEachTypeAmount;
import static edu.hw4.AnimalUtil.getEachTypeHeaviestAnimal;
import static edu.hw4.AnimalUtil.getHeaviestAnimalUnderKcmHeight;
import static edu.hw4.AnimalUtil.getKTopWeighing;
import static edu.hw4.AnimalUtil.getKthOldestAnimal;
import static edu.hw4.AnimalUtil.getLongestNameAnimal;
import static edu.hw4.AnimalUtil.getMostAnimalsSex;
import static edu.hw4.AnimalUtil.isInListDogHigherKcm;
import static edu.hw4.AnimalUtil.sortHeightAscending;
import static edu.hw4.AnimalUtil.sumWeightAnimalsKtoLYearsOldInclusive;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class AnimalUtilTest {
    Animal marginal, milesMorales, perro, sobaka, pug, salmon, tuna, raven, baldEagle, nyanCat, grumpyCat;
    List<Animal> unorderedList;

    @BeforeEach
    void createAnimals() {
        marginal = new Animal("Marginal", Type.SPIDER
            , Sex.M, 31, 180, 80, true);
        milesMorales = new Animal("Miles Morales", Type.SPIDER
            , Sex.M, 17, 177, 75, false);
        perro = new Animal("Perro", Type.DOG
            , Sex.M, 3, 55, 110, true);
        sobaka = new Animal("Sobaka", Type.DOG
            , Sex.M, 4, 70, 73, true);
        pug = new Animal("Pug", Type.DOG
            , Sex.F, 2, 40, 30, false);
        salmon = new Animal("Salmon", Type.FISH
            , Sex.F, 1, 30, 5, false);
        tuna = new Animal("Tuna", Type.FISH
            , Sex.M, 2, 20, 3, false);
        raven = new Animal("Raven", Type.BIRD
            , Sex.F, 2, 15, 1, true);
        baldEagle = new Animal("Bald Eagle", Type.BIRD
            , Sex.M, 3, 100, 4, true);
        nyanCat = new Animal("Nyan Cat", Type.CAT
            , Sex.M, 11, 9999, 9999, false);
        grumpyCat = new Animal("Grumpy Cat", Type.CAT
            , Sex.M, 7, 50, 7, true);

        unorderedList = new ArrayList<>();

        unorderedList.add(marginal);
        unorderedList.add(milesMorales);
        unorderedList.add(perro);
        unorderedList.add(sobaka);
        unorderedList.add(pug);
        unorderedList.add(salmon);
        unorderedList.add(tuna);
        unorderedList.add(raven);
        unorderedList.add(baldEagle);
        unorderedList.add(nyanCat);
        unorderedList.add(grumpyCat);
    }

    @Test
    @DisplayName("Sorting empty list doesn't affect anything")
    void sortHeightAscending_emptyList_emptyList() {
        assertIterableEquals(sortHeightAscending(new ArrayList<>()), new ArrayList<>());
    }

    @Test
    @DisplayName("Sorting works in ascending order by height")
    void sortHeightAscending_nonEmptyList_sortedList() {
        var real = sortHeightAscending(unorderedList);
        var expected = Arrays.asList(raven, tuna, salmon
            , pug, grumpyCat, perro, sobaka, baldEagle, milesMorales, marginal, nyanCat);
        assertIterableEquals(expected, real);
    }

    @Test
    @DisplayName("Getting k top weighing from empty lists doesn't work")
    void getKTopWeighing_emptyList_emptyList() {
        assertIterableEquals(getKTopWeighing(new ArrayList<>(), 100), new ArrayList<>());
    }

    @Test
    @DisplayName("Getting k when k is within size works fine")
    void getKTopWeighing_limitWithingSize_kElements() {
        var expected = Arrays.asList(nyanCat, perro, marginal);
        assertIterableEquals(expected, getKTopWeighing(unorderedList, 3));
    }

    @Test
    @DisplayName("Getting k when k is over the size returns the whole list")
    void getKTopWeighing_limitOverSize_wholeSortedList() {
        unorderedList = Arrays.asList(tuna, raven, baldEagle);
        var expected = Arrays.asList(baldEagle, tuna, raven);
        assertIterableEquals(expected, getKTopWeighing(unorderedList, 100));
    }

    @Test
    @DisplayName("Each type amount on an empty list return zero for each")
    void getEachTypeAmount_emptyList_eachType0() {
        var expected = new HashMap<Type, Integer>();
        expected.put(Type.BIRD, 0);
        expected.put(Type.DOG, 0);
        expected.put(Type.FISH, 0);
        expected.put(Type.CAT, 0);
        expected.put(Type.SPIDER, 0);
        assertThat(getEachTypeAmount(new ArrayList<>())).isEqualTo(expected);
    }

    @Test
    @DisplayName("Each type amount on list count the amount correctly")
    void getEachTypeAmount_nonEmptyList_amountsForEach() {
        var expected = new HashMap<Type, Integer>();
        expected.put(Type.BIRD, 2);
        expected.put(Type.DOG, 3);
        expected.put(Type.FISH, 2);
        expected.put(Type.CAT, 2);
        expected.put(Type.SPIDER, 2);
        assertThat(getEachTypeAmount(unorderedList)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Animal with the longest name in an empty List is null")
    void getLongestNameAnimal_emptyList_null() {
        assertThat(getLongestNameAnimal(new ArrayList<>())).isEqualTo(null);
    }

    @Test
    @DisplayName("Animal with the longest name in an unordered list")
    void getLongestNameAnimal_nonEmptyList_animalWithLongestName() {
        assertThat(getLongestNameAnimal(unorderedList)).isEqualTo(milesMorales);
    }

    @Test
    @DisplayName("Sex with the most amount of animals in an empty List is null")
    void getMostAnimalsSex_emptyList_null() {
        assertThat(getMostAnimalsSex(new ArrayList<>())).isEqualTo(null);
    }

    @Test
    @DisplayName("Sex with the most amount of animals in a list is Male")
    void getMostAnimalsSex_nonEmptyList_male() {
        assertThat(getMostAnimalsSex(unorderedList)).isEqualTo(Sex.M);
    }

    @Test
    @DisplayName("Sex with the most amount of animals in a list as Female")
    void getMostAnimalsSex_nonEmptyList_female() {
        unorderedList = new ArrayList<>();
        unorderedList.add(pug);
        unorderedList.add(salmon);
        unorderedList.add(raven);
        unorderedList.add(marginal);
        assertThat(getMostAnimalsSex(unorderedList)).isEqualTo(Sex.F);
    }

    @Test
    @DisplayName("Heaviest animals in each type for an empty list are all nulls")
    void getEachTypeHeaviestAnimal_emptyList_emptyMap() {
        assertThat(getEachTypeHeaviestAnimal(new ArrayList<>())).isEqualTo(new HashMap<>());
    }

    @Test
    @DisplayName("Heaviest animals in each type for an unordered list")
    void getEachTypeHeaviestAnimal_nonEmptyList_heaviestAnimals() {
        var expected = new HashMap<Type, Animal>();
        expected.put(Type.BIRD, baldEagle);
        expected.put(Type.FISH, salmon);
        expected.put(Type.DOG, perro);
        expected.put(Type.SPIDER, marginal);
        expected.put(Type.CAT, nyanCat);
        assertThat(getEachTypeHeaviestAnimal(unorderedList)).isEqualTo(expected);
    }

    @Test
    @DisplayName("K-th oldest animal in an empty list is null")
    void getKthOldestAnimal_emptyList_null() {
        assertThat(getKthOldestAnimal(new ArrayList<>(), 1)).isNull();
    }

    @Test
    @DisplayName("K-th oldest animal in list of size k is the overall youngest animal")
    void getKthOldestAnimal_lastIndex_youngestAnimalOverall() {
        assertThat(getKthOldestAnimal(unorderedList, unorderedList.size())).isEqualTo(salmon);
    }

    @Test
    @DisplayName("K-th oldest animal in list of size less than k is null")
    void getKthOldestAnimal_indexOutOfBounds_null() {
        assertThat(getKthOldestAnimal(unorderedList, unorderedList.size() + 1)).isNull();
    }

    @Test
    @DisplayName("K-th oldest animal in list of size less than k is null")
    void getKthOldestAnimal_indexInside_kthOldestAnimal() {
        assertThat(getKthOldestAnimal(unorderedList, 3)).isEqualTo(nyanCat);
    }

    @Test
    @DisplayName("Heaviest animal under k cm of height in empty list is empty")
    void getHeaviestAnimalUnderKcmHeight_emptyList_empty() {
        assertThat(getHeaviestAnimalUnderKcmHeight(new ArrayList<>(), Integer.MAX_VALUE)).isEmpty();
    }

    @Test
    @DisplayName("Heaviest animal under k cm of height in a list with no such animals is empty")
    void getHeaviestAnimalUnderKcmHeight_noSuchAnimal_empty() {
        assertThat(getHeaviestAnimalUnderKcmHeight(unorderedList, Integer.MIN_VALUE)).isEmpty();
    }

    @Test
    @DisplayName("Heaviest animal under k cm of height in a list is present")
    void getHeaviestAnimalUnderKcmHeight_suchAnimalsPresent_heaviestOfTheEligible() {
        assertThat(getHeaviestAnimalUnderKcmHeight(unorderedList, 70))
            .isEqualTo(Optional.of(perro));
    }

    @Test
    @DisplayName("Number of Paws in an empty list is zero")
    void countPaws_emptyList_zero() {
        assertThat(countPaws(new ArrayList<>())).isEqualTo(0);
    }

    @Test
    @DisplayName("Number of Paws in a non-empty list")
    void countPaws_nonEmptyList_totalAmount() {
        assertThat(countPaws(unorderedList)).isEqualTo(40);
    }

    @Test
    @DisplayName("Given an empty list getAnimalsAgeNotEqualPaws produces an empty list")
    void getAnimalsAgeNotEqualPaws_emptyList_emptyList() {
        assertThat(getAnimalsAgeNotEqualPaws(new ArrayList<>())).isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("Given a list in which there are animals getAnimalsAgeNotEqualPaws produces a list without irrelevant ones")
    void getAnimalsAgeNotEqualPaws_nonEmptyList_listOfEligibleAnimals() {
        var expected = new ArrayList<>(unorderedList);
        expected.remove(sobaka);
        expected.remove(raven);
        assertThat(getAnimalsAgeNotEqualPaws(unorderedList)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Given an empty list getAnimalsCanBiteHigher100cm produces an empty list")
    void getAnimalsCanBiteHigher100cm_emptyList_emptyList() {
        assertThat(getAnimalsCanBiteHigher100cm(new ArrayList<>())).isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("Given a list in which there are animals getAnimalsCanBiteHigher100cm produces a list of the eligible ones")
    void getAnimalsCanBiteHigher100cm_nonEmptyList_listOfEligibleAnimals() {
        var expected = new ArrayList<>();
        expected.add(marginal);
        assertThat(getAnimalsCanBiteHigher100cm(unorderedList)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Number of animals with weight higher than height in empty list is 0")
    void countAnimalWeightOverHeight_emptyList_0() {
        assertThat(countAnimalWeightOverHeight(new ArrayList<>())).isEqualTo(0);
    }

    @Test
    @DisplayName("Number of animals with weight higher than height in a non-empty list")
    void countAnimalWeightOverHeight_nonEmptyList_amount() {
        assertThat(countAnimalWeightOverHeight(unorderedList)).isEqualTo(2);
    }

    @Test
    @DisplayName("No animals with names of length > 1 word in empty list")
    void getAnimalsNameSeveralWords_emptyList_emptyList() {
        assertThat(getAnimalsNameSeveralWords(new ArrayList<>())).isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("Animals with names longer than 1 word in a list")
    void getAnimalsNameSeveralWords_nonEmptyList_listOfEligibleAnimals() {
        var expected = new ArrayList<Animal>();
        expected.add(milesMorales);
        expected.add(baldEagle);
        expected.add(nyanCat);
        expected.add(grumpyCat);
        assertThat(getAnimalsNameSeveralWords(unorderedList)).isEqualTo(expected);
    }

    @Test
    @DisplayName("No dogs higher than k cm in empty list")
    void isInListDogHigherKcm_emptyList_false() {
        assertThat(isInListDogHigherKcm(new ArrayList<>(), Integer.MIN_VALUE)).isFalse();
    }

    @Test
    @DisplayName("Animals with names longer than 1 word in a list")
    void isInListDogHigherKcm_nonEmptyList_true() {
        assertThat(isInListDogHigherKcm(unorderedList, 69)).isTrue();
    }

    @Test
    @DisplayName("Animals with names longer than 1 word in a list")
    void isInListDogHigherKcm_nonEmptyList_false() {
        assertThat(isInListDogHigherKcm(unorderedList, Integer.MAX_VALUE)).isFalse();
    }

    @Test
    @DisplayName("Total weight of animals from k to l y.o. in empty list is 0")
    void sumWeightAnimalsKtoLYearsOldInclusive_emptyList_zero() {
        assertThat(sumWeightAnimalsKtoLYearsOldInclusive(
            new ArrayList<>(), Integer.MIN_VALUE, Integer.MAX_VALUE)).isEqualTo(0);
    }

    @Test
    @DisplayName("Total weight of animals from k to l y.o. in a non-empty list")
    void sumWeightAnimalsKtoLYearsOldInclusive_nonEmptyList_totalSum() {
        assertThat(sumWeightAnimalsKtoLYearsOldInclusive(
            unorderedList, 10, 20)).isEqualTo(10074);
    }
}
