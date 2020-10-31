package ru.makarov.springripper.profilirovanie;

import ru.makarov.springripper.domain.Person;

import java.util.List;

/**
 * Sample for Proxy creation.
 * Interface for describe all persons from data.
 */
public interface PostBeanSampleProfilirovanie {
    List<Person> findAll();
}
