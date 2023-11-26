package edu.hw7.task3half;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import edu.hw7.task3.UnsynchronizedPersonDatabase;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase implements PersonDatabase {
    private final ReadWriteLock lock;
    private final UnsynchronizedPersonDatabase database;

    public ReadWriteLockPersonDatabase() {
        lock = new ReentrantReadWriteLock(true);
        database = new UnsynchronizedPersonDatabase();
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            database.add(person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean delete(int id) {
        lock.writeLock().lock();
        try {
            return database.delete(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return database.findByName(name);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return database.findByAddress(address);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return database.findByPhone(phone);
        } finally {
            lock.readLock().unlock();
        }
    }
}
