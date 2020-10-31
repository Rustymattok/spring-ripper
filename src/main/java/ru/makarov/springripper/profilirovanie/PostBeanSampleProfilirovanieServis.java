package ru.makarov.springripper.profilirovanie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.repository.PersonCrudRepo;

import java.util.List;
/**
 * Sample for Proxi creation.
 * Servis for take all data of persons.
 *
 * Annotation @Profiling - marker for init Proxy if found.
 */
@Profiling
@Service
public class PostBeanSampleProfilirovanieServis implements PostBeanSampleProfilirovanie {

    private final PersonCrudRepo personCrudRepo;

    @Autowired
    public PostBeanSampleProfilirovanieServis(PersonCrudRepo personCrudRepo) {
        this.personCrudRepo = personCrudRepo;
    }

    @Override
    public List<Person> findAll() {
       return personCrudRepo.findAll();
    }
}
