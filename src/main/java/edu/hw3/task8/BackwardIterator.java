package edu.hw3.task8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BackwardIterator<E> implements Iterator<E> {
    private final Collection<E> collection;
    private List<E> iterationDomain;
    private int pointerToNextElement;

    public BackwardIterator(Collection<E> collection) {
        if (collection == null) {
            throw new IllegalArgumentException();
        }
        this.collection = collection;
    }

    @Override
    public boolean hasNext() {
        if (iterationDomain == null) {
            iterationDomain = new ArrayList<>(collection);
            pointerToNextElement = iterationDomain.size() - 1;
        }
        return pointerToNextElement >= 0;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterationDomain.get(pointerToNextElement--);
    }
}
