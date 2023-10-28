package edu.project1.model;

public record State(String wordMask, String currentWord, int maxMistakesLeft, GuessResult result) {
}
