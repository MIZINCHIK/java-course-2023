package edu.hw7.task3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static edu.hw7.task3.MapSetUtils.addToSetInMap;
import static edu.hw7.task3.MapSetUtils.removeFromSetInMap;

public class UnsynchronizedPersonDatabase implements PersonDatabase {
    private final Map<String, Set<Person>> personsByName;
    private final Map<String, Set<Person>> personsByAddress;
    private final Map<String, Set<Person>> personsByPhone;
    private final Map<Integer, Person> personById;

    public UnsynchronizedPersonDatabase() {
        personsByName = new HashMap<>();
        personsByAddress = new HashMap<>();
        personsByPhone = new HashMap<>();
        personById = new HashMap<>();
    }

    @Override
    public void add(Person person) {
        if (personById.containsKey(person.id())) {
            throw new IllegalStateException("Ids must be unique");
        }
        personById.put(person.id(), person);
        addToSetInMap(personsByName, person, person.name());
        addToSetInMap(personsByAddress, person, person.address());
        addToSetInMap(personsByPhone, person, person.phoneNumber());
    }

    @Override
    public boolean delete(int id) {
        if (!personById.containsKey(id)) {
            return false;
        }
        Person person = personById.get(id);
        personById.remove(id);
        removeFromSetInMap(personsByName, person, person.name());
        removeFromSetInMap(personsByAddress, person, person.address());
        removeFromSetInMap(personsByPhone, person, person.phoneNumber());
        return true;
    }

    @Override
    public List<Person> findByName(String name) {
        return List.copyOf(personsByName.getOrDefault(name, new HashSet<>()));
    }

    @Override
    public List<Person> findByAddress(String address) {
        return List.copyOf(personsByAddress.getOrDefault(address, new HashSet<>()));
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return List.copyOf(personsByPhone.getOrDefault(phone, new HashSet<>()));
    }
}
