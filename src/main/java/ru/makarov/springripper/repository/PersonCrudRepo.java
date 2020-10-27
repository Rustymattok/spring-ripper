package ru.makarov.springripper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.makarov.springripper.domain.Person;

/**
 * JpaRepository for Person DATA.
 */
public interface PersonCrudRepo extends JpaRepository<Person, Integer> {

    Person findPersonById(Integer id);

    /**
     * Remove person from general table department_person.
     *
     * @param id -  person.
     */
    @Modifying
    @Transactional
    @Query(value = "delete from department_person where person_id = :id", nativeQuery = true)
    void deletePersonByIDFromGeneralTable(@Param("id") Integer id);

}
