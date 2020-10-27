package ru.makarov.springripper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.makarov.springripper.domain.Department;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.repository.DepartmentJpaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DepartmentService implements StoreDepartment {

    private final DepartmentJpaRepo departmentJpaRepo;

    @Autowired
    public DepartmentService(DepartmentJpaRepo departmentJpaRepo) {
        this.departmentJpaRepo = departmentJpaRepo;
    }

//todo подумать как реализовать RowMapper-ом.
    @Override
    public List<Person> getAllIdPerson(Integer id) {
        List<Person> persons = new ArrayList<>();
        List<Map<String, Object>> people = departmentJpaRepo.getAllIdPerson(id);
        for (Map<String, Object> objectMap : people) {
            Person person = new Person();
            person.setId((Integer) objectMap.get("id"));
            person.setName((String) objectMap.get("name"));
            persons.add(person);
        }
        return persons;
    }

    @Override
    public List<Department> findAll() {
        return departmentJpaRepo.findAll();
    }

    @Override
    public Department findDepartmentByIdOfPerson(Integer idPerson) {
        return departmentJpaRepo.findDepartmentByIdOfPerson(idPerson);
    }

    @Override
    public Department findDepartmentByName(String name) {
        return departmentJpaRepo.findDepartmentByName(name);
    }

    @Override
    public void addDepartment(Department department) {
        departmentJpaRepo.save(department);
    }

    @Override
    public void deleteDepartment(Department department) {
        if(department != null){
            departmentJpaRepo.deleteDepartmentByIDFromGeneralTable(department.getId());
            departmentJpaRepo.delete(department);
        }
    }

    @Override
    public void updateDepartment(Department department) {
        departmentJpaRepo.save(department);
    }

}
