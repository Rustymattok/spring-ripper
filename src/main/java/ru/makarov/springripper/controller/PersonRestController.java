package ru.makarov.springripper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.repository.PersonCrudRepo;
import ru.makarov.springripper.service.PersonServis;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Person DataBase activities.
 */
@RestController
public class PersonRestController {

    private final PersonCrudRepo personCrudRepo;

    private final PersonServis servis;

    @Autowired
    public PersonRestController(PersonServis servis, PersonCrudRepo personCrudRepo) {
        this.servis = servis;
        this.personCrudRepo = personCrudRepo;
    }

    /**
     * Rest api for get all data from Person by DataBase.
     * Sample curl response:
     * $ curl -i http://localhost:8080/person
     *
     * @return - all data.
     */
    @GetMapping(value = "/person")
    public List<Person> findAll() {
        return new ArrayList<>(this.servis.findAll());
    }

    /**
     * Rest api for get Person by id data from Person by DataBase.
     * Sample curl response:
     * $ curl -i http://localhost:8080/person/1
     *
     * @return - person by id.
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Person person = servis.findPersonById(id);
        return new ResponseEntity<Person>(person, person != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Rest api for add to Person DataBase by person.
     * Sample curl response:
     * $ curl -H 'Content-Type: application/json' -X POST -d '{"name":"Vlad"}' http://localhost:8080/person
     *
     * @param person - person to add.
     */
    @PostMapping("/person")
    public void addPerson(@RequestBody Person person) {
        servis.addPerson(person);
    }

    /**
     * Rest api for put(update) from Person DataBase by person.
     * Sample curl response:
     * $ curl -i -H 'Content-Type: application/json' -X PUT -d '{"id":"10","name":"Vlad2"}' http://localhost:8080/person
     *
     * @param person - by person.
     * @return - response.
     */
    @PutMapping("/person/put")
    public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
        this.personCrudRepo.save(person);
        return ResponseEntity.ok().build();
    }

    /**
     * Rest api for delete from Person DataBase by id.
     * Sample curl response:
     * $ curl -i -X DELETE http://localhost:8080/person/1
     *
     * @param id - by id remove.
     * @return - response.
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = servis.findPersonById(id);
        servis.deletePerson(person);
        return ResponseEntity.ok().build();
    }

}
