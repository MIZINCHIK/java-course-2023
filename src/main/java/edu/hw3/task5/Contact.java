package edu.hw3.task5;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public record Contact(String firstName, String lastName) implements Comparable<Contact> {
    public static Contact[] parseContacts(String[] contacts, ContactSortingOrder order) {
        return Arrays.stream(contacts).filter(Objects::nonNull).map(contact -> {
            String[] names = contact.split(" ", 2);
            return new Contact(names[0], names.length >= 2 ? names[1] : null);
        }).sorted(order == ContactSortingOrder.DESC
            ? Comparator.reverseOrder()
            : Comparator.naturalOrder()).toArray(Contact[]::new);
    }

    @Override
    public int compareTo(@NotNull Contact o) {
        if (lastName == null && o.lastName == null) {
            return firstName.compareTo(o.firstName);
        }
        if (lastName == null) {
            return firstName.compareTo(o.lastName);
        }
        if (o.lastName == null) {
            return lastName.compareTo(o.firstName);
        }
        return lastName.compareTo(o.lastName);
    }
}
