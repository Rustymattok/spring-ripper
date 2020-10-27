package ru.makarov.springripper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.makarov.springripper.domain.Department;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.repository.DepartmentJpaRepo;
import ru.makarov.springripper.repository.PersonCrudRepo;
import ru.makarov.springripper.service.DepartmentService;
import ru.makarov.springripper.service.PersonServis;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("DataBase work ability test")
public class DataBaseTestStandardMethod {

    @Autowired
    private PersonCrudRepo personServis;

    @Autowired
    private DepartmentJpaRepo departmentService;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" проверяет работоспособность вставки элемента в дб person и извлечение) ")
    @Test
    void whenShouldCheckPersonDataBaseWork() {
        //add to data base before test
        Person person = new Person();
        person.setName("vlad");
        em.persist(person);
        em.flush();
        //test after
        Person personTest = personServis.findPersonById(7);
        assertThat(personTest.getName()).isEqualTo("vlad");
        assertThat(personTest.getId()).isEqualTo(7);
    }

    @DisplayName(" проверяет работоспособность вставки элемента в дб department и извлечение) ")
    @Test
    void whenShouldCheckDepartmentDataBaseWork() {
        //add to data base before test
        Department department = new Department();
        department.setName("it");
        em.persist(department);
        em.flush();
        //test after
        Department departmentTest = departmentService.findDepartmentByName("it");
        assertThat(departmentTest.getName()).isEqualTo("it");
        assertThat(departmentTest.getId()).isEqualTo(4);
    }
}
