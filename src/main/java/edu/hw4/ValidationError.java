package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public enum ValidationError {
    NULL_NAME("name"), NULL_TYPE("type"), NULL_SEX("sex"),
    NEGATIVE_AGE("age"), NEGATIVE_WEIGHT("weight"), NEGATIVE_HEIGHT("height");

    private final String field;

    ValidationError(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public static Set<ValidationError> getAnimalErrors(Animal animal) {
        var result = new HashSet<ValidationError>();
        if (animal.name() == null) {
            result.add(NULL_NAME);
        }
        if (animal.type() == null) {
            result.add(NULL_TYPE);
        }
        if (animal.sex() == null) {
            result.add(NULL_SEX);
        }
        if (animal.age() < 0) {
            result.add(NEGATIVE_AGE);
        }
        if (animal.weight() < 0) {
            result.add(NEGATIVE_WEIGHT);
        }
        if (animal.height() < 0) {
            result.add(NEGATIVE_HEIGHT);
        }
        return result;
    }
}
