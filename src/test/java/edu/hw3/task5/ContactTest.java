package edu.hw3.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static edu.hw3.task5.Contact.parseContacts;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ContactTest {
    @Test
    @DisplayName("Empty array produces empty array")
    void parseContacts_emptyArray_emptyArray() {
        assertIterableEquals(List.of(),
            Arrays.asList(parseContacts(new String[]{}, ContactSortingOrder.ASC)));
    }

    @Test
    @DisplayName("Null Strings are skipped")
    void parseContacts_nullString_emptyArray() {
        assertIterableEquals(List.of(),
            Arrays.asList(parseContacts(new String[]{null}, ContactSortingOrder.ASC)));
    }

    @Test
    @DisplayName("Parsing and sorting contacts with last names each")
    void parseContacts_allHaveLastNames_correctlySortedArray() {
        Contact[] contacts = parseContacts(new String[]{"John Locke"
            , "Thomas Aquinas", "David Hume", "Rene Descartes"}, ContactSortingOrder.ASC);
        Contact[] expected = new Contact[]{new Contact("Thomas", "Aquinas"),
            new Contact("Rene", "Descartes"),
            new Contact("David", "Hume"),
            new Contact("John", "Locke")};
        assertIterableEquals(Arrays.asList(expected), Arrays.asList(contacts));
    }

    @Test
    @DisplayName("Parsing and sorting contacts with last names missing")
    void parseContacts_notAllHaveLastNames_correctlySortedArray() {
        Contact[] contacts = parseContacts(new String[]{"John"
            ,"Химчистка Центр", "Thomas Aquinas", "David Hume", "Rene"}, ContactSortingOrder.ASC);
        Contact[] expected = new Contact[]{new Contact("Thomas", "Aquinas"),
            new Contact("David", "Hume"),
            new Contact("John", null),
            new Contact("Rene", null),
            new Contact("Химчистка", "Центр")};
        assertIterableEquals(Arrays.asList(expected), Arrays.asList(contacts));
    }

    @Test
    @DisplayName("Test descending sorting order")
    void parseContacts_descendingOrder_correctlySortedArray() {
        Contact[] contacts = parseContacts(new String[]{"John"
            ,"Химчистка Центр", "Thomas Aquinas", "David Hume", "Rene"}, ContactSortingOrder.DESC);
        Contact[] expected = new Contact[]{new Contact("Химчистка", "Центр"),
            new Contact("Rene", null),
            new Contact("John", null),
            new Contact("David", "Hume"),
            new Contact("Thomas", "Aquinas")};
        assertIterableEquals(Arrays.asList(expected), Arrays.asList(contacts));
    }
}
