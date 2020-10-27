package ru.makarov.springripper.service;

import ru.makarov.springripper.domain.Department;
import ru.makarov.springripper.domain.Person;

import java.util.List;
/**
 * Interface for activities with Department data.
 */
public interface StoreDepartment {
    List<Person> getAllIdPerson(Integer id);

    List<Department> findAll();

    Department findDepartmentByIdOfPerson(Integer idPerson);

    Department findDepartmentByName(String name);

    void addDepartment(Department department);

    void deleteDepartment(Department department);

    void updateDepartment(Department department);
}
