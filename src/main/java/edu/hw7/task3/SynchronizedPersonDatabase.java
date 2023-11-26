package edu.hw7.task3;

import java.util.List;

public class SynchronizedPersonDatabase implements PersonDatabase {
    private final UnsynchronizedPersonDatabase database;

    public SynchronizedPersonDatabase() {
        database = new UnsynchronizedPersonDatabase();
    }

    @Override
    public synchronized void add(Person person) {
        database.add(person);
    }

    @Override
    public synchronized boolean delete(int id) {
        return database.delete(id);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return database.findByName(name);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return database.findByAddress(address);
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return database.findByPhone(phone);
    }
}
