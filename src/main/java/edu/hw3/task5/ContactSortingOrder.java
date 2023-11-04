package edu.hw3.task5;

public enum ContactSortingOrder {
    ASC(1), DESC(-1);

    private final int comparatorValue;

    ContactSortingOrder(int value) {
        this.comparatorValue = value;
    }

    public int getComparatorValue() {
        return comparatorValue;
    }
}
