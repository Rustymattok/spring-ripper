package ru.makarov.springripper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.repository.PersonCrudRepo;

import java.util.List;

/**
 * Service for work with Person.
 */
@Service
@Transactional
//todo условия проверок...
//todo запрос вытаскивания id внести сюда
//todo сделать тесты
public class PersonServis implements StorePerson {
    private final PersonCrudRepo personCrudRepo;

    @Autowired
    public PersonServis(PersonCrudRepo personCrudRepo) {
        this.personCrudRepo = personCrudRepo;
    }

    /**
     * Save to data base person.
     *
     * @param person - person to save.
     */
    @Override
    //todo метод на логгер в аспект
    public void addPerson(Person person) {
        personCrudRepo.save(person);
    }

    /**
     * Find person by ID.
     *
     * @param id - id of person.
     * @return - person object.
     */
    @Override
    //todo метод на логгер в аспект
    public Person findPersonById(Integer id) {
        return personCrudRepo.findPersonById(id);
    }

    /**
     * Delete person from Data by person object.
     * Before remove person from main Person table should
     * remove person from general table of DEPARTMENT_PERSON by
     * method deletePersonByIDFromGeneralTable(Integer idOfPerson)
     *
     * @param person - person object.
     */
    @Override
    //todo метод на логгер в аспект
    public void deletePerson(Person person) {
        if (person != null) {
            personCrudRepo.deletePersonByIDFromGeneralTable(person.getId());
            personCrudRepo.delete(person);
        }
    }

    /**
     * Select all data of person from Person table.
     *
     * @return - List of persons.
     */
    @Override
    public List<Person> findAll() {
        return personCrudRepo.findAll();
    }
}
