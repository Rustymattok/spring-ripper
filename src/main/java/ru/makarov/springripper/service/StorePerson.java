package ru.makarov.springripper.service;

import ru.makarov.springripper.domain.Person;

import java.util.List;

/**
 * Interface for activities with Person data.
 */
public interface StorePerson {
    void addPerson(Person person);

    Person findPersonById(Integer id);

    void deletePerson(Person person);

    List<Person> findAll();

}
