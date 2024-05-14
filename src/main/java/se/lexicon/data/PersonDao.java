package se.lexicon.data;

import se.lexicon.model.Person;

import java.util.List;

public interface PersonDao {
    Person create(Person person);
    Person findById(int id);
    List<Person> findAll();

    List<Person> findByName(String name);

    Person update(Person person);
    boolean deleteById(Person person);
}
