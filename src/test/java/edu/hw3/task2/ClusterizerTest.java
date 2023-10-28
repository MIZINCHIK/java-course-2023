package edu.hw3.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Objects;
import static edu.hw3.task2.Clusterizer.clusterize;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ClusterizerTest {
    @Test
    @DisplayName("Clusterizing empty String produces an empty list")
    void clusterize_emptyString_emptyList() {
        String targetString = "";
        assertThat(Objects.requireNonNull(clusterize(targetString)).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Clusterizing a String of incorrect format produces a null")
    void clusterize_incorrectCharacters_IllegalArgumentException() {
        String targetString = "(1)()()";
        assertThatThrownBy(() -> clusterize(targetString))
            .isInstanceOf(NullPointerException.class)
            .message().isEqualTo("No string of braces provided!");
    }

    @Test
    @DisplayName("Clusterizing a String that contains an opening brace that isn't closed produces a null")
    void clusterize_unclosedBrace_null() {
        String targetString = "(";
        assertThat(clusterize(targetString)).isNull();
    }

    @Test
    @DisplayName("Clusterizing a String that contains a closing brace that doesn't belong to any opening one produces a null")
    void clusterize_extraClosing_null() {
        String targetString = ")";
        assertThat(clusterize(targetString)).isNull();
    }

    @Test
    @DisplayName("Clusterizing a String of adjacent clusters of braces")
    void clusterize_neighbouringClusters_listOfClusters() {
        String targetString = "()()()";
        List<String> result = clusterize(targetString);
        assertThat(Objects.requireNonNull(result).size()).isEqualTo(3);
        for (String cluster : result) {
            assertThat(cluster).isEqualTo("()");
        }
    }

    @Test
    @DisplayName("Clusterizing a String of a single nested cluster")
    void clusterize_oneNestedCluster_listOfASingleCluster() {
        String targetString = "((()))";
        List<String> result = clusterize(targetString);
        assertThat(Objects.requireNonNull(result).size()).isEqualTo(1);
        for (String cluster : result) {
            assertThat(cluster).isEqualTo("((()))");
        }
    }

    @Test
    @DisplayName("Clusterizing a String of adjacent nested clusters")
    void clusterize_complicatedNeighbouringClusters_listOfClusters() {
        String targetString = "((()))(())()()(()())";
        List<String> result = clusterize(targetString);
        List<String> expected = List.of("((()))", "(())", "()", "()", "(()())");
        assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("Clusterizing a String of clusters with complicated nesting")
    void clusterize_complicatedNestedClusters_listOfClusters() {
        String targetString = "((())())(()(()()))";
        List<String> result = clusterize(targetString);
        List<String> expected = List.of("((())())", "(()(()()))");
        assertIterableEquals(expected, result);
    }
}
