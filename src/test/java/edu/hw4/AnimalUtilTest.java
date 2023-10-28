package edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static edu.hw4.AnimalUtil.eachTypeAmount;
import static edu.hw4.AnimalUtil.getKTopWeighing;
import static edu.hw4.AnimalUtil.sortHeightAscending;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class AnimalUtilTest {
    Animal marginal, milesMorales, perro, sobaka, pug, salmon, tuna
        , raven, baldEagle, nyanCat, grumpyCat;
    List<Animal> unorderedList;
    List<Animal> expected;

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
        expected = new ArrayList<>();

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
        expected = Arrays.asList(new Animal[]{raven, tuna, salmon
            , pug, grumpyCat, perro, sobaka, baldEagle, milesMorales, marginal, nyanCat});
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
        expected = Arrays.asList(nyanCat, perro, marginal);
        assertIterableEquals(expected, getKTopWeighing(unorderedList, 3));
    }

    @Test
    @DisplayName("Getting k when k is over the size returns the whole list")
    void getKTopWeighing_limitOverSize_wholeSortedList() {
        unorderedList = Arrays.asList(tuna, raven, baldEagle);
        expected = Arrays.asList(baldEagle, tuna, raven);
        assertIterableEquals(expected, getKTopWeighing(unorderedList, 100));
    }

    @Test
    @DisplayName("Each type amount on an empty list return zero for each")
    void eachTypeAmount_emptyList_eachType0() {
        var expected = new HashMap<Type, Integer>();
        expected.put(Type.BIRD, 0);
        expected.put(Type.DOG, 0);
        expected.put(Type.FISH, 0);
        expected.put(Type.CAT, 0);
        expected.put(Type.SPIDER, 0);
        assertThat(eachTypeAmount(new ArrayList<>())).isEqualTo(expected);
    }

    @Test
    @DisplayName("Each type amount on list count the amount correctly")
    void eachTypeAmount_nonEmptyList_amountsForEach() {
        var expected = new HashMap<Type, Integer>();
        expected.put(Type.BIRD, 2);
        expected.put(Type.DOG, 3);
        expected.put(Type.FISH, 2);
        expected.put(Type.CAT, 2);
        expected.put(Type.SPIDER, 2);
        assertThat(eachTypeAmount(unorderedList)).isEqualTo(expected);
    }
}
