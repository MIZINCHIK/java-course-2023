package edu.hw3.task5;

import java.util.Arrays;
import java.util.Objects;

public record Contact(String firstName, String lastName) {
    public static Contact[] parseContacts(String[] contacts, ContactSortingOrder order) {
        return Arrays.stream(contacts).filter(Objects::nonNull).map(contact -> {
            String[] names = contact.split(" ", 2);
            return new Contact(names[0], names.length >= 2 ? names[1] : null);
        }).sorted((o1, o2) -> {
            int compareValue;
            if (o1.lastName == null && o2.lastName == null) {
                compareValue = o1.firstName.compareTo(o2.firstName);
            } else if (o1.lastName == null) {
                compareValue = o1.firstName.compareTo(o2.lastName);
            } else if (o2.lastName == null) {
                compareValue = o1.lastName.compareTo(o2.firstName);
            } else {
                compareValue = o1.lastName.compareTo(o2.lastName);
            }
            return compareValue * order.getComparatorValue();
        }).toArray(Contact[]::new);
    }
}
