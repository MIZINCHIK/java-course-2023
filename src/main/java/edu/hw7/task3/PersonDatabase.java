package edu.hw7.task3;

import java.util.List;

public interface PersonDatabase {
    void add(Person person);

    boolean delete(int id);

    List<Person> findByName(String name);

    List<Person> findByAddress(String address);

    List<Person> findByPhone(String phone);
}
