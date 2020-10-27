package ru.makarov.springripper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.makarov.springripper.domain.Department;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.service.DepartmentService;
import ru.makarov.springripper.service.PersonServis;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Query work ability test")
class QueryDataBaseTests {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PersonServis personServis;

    @DisplayName("Entity Person: должен вернуть по id = 1 by Person (департамент-engineer)")
    @Test
    void whenShouldCheckFindDepartmentByIdPerson() {
        Department department = departmentService.findDepartmentByIdOfPerson(1);
        assertThat(department.getName()).isEqualTo("engineer");
    }

    @DisplayName("Entity Person: должен вернуть по перовму элементу 2 person (Сидоров1 and Сидоров2) ")
    @Test
    void whenShouldCheckGetAlLPersonByIdDepartment() {
        List<Person> people = departmentService.getAllIdPerson(1);
        assertThat(people.get(0).getName()).isEqualTo("Sidorov1");
        assertThat(people.get(1).getName()).isEqualTo("Sidorov2");
    }

    @DisplayName("Entity Person: должен вернуть 5 элементов базе данных после удаления")
    @Test
    @Sql(scripts = "/create-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenShouldCheckRemovePersonFromGeneralTable() {
        Person person = personServis.findPersonById(1);
        personServis.deletePerson(person);
        List<Person> people = personServis.findAll();
        assertThat(people.size()).isEqualTo(5);
    }

    @DisplayName("Entity Department: должен вернуть 2 элемента в базе данных после удаления")
    @Test
    @Sql(scripts = "/create-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenShouldCheckRemoveDepartmentFromGeneralTable() {
        Department department = departmentService.findDepartmentByName("engineer");
        departmentService.deleteDepartment(department);
        List<Department> departments = departmentService.findAll();
        assertThat(departments.size()).isEqualTo(2);
    }

    @DisplayName("Entity Department: должен вернуть Department, который был добавлен в БД")
    @Test
    @Sql(scripts = "/create-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void whenShouldCheckAddDepartment() {
        Department department = new Department();
        department.setName("new");
        departmentService.addDepartment(department);
        assertThat(departmentService.findDepartmentByName("new").getName()).isEqualTo("new");
    }

}
