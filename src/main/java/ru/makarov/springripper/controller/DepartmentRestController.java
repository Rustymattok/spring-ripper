package ru.makarov.springripper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.makarov.springripper.domain.Department;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Rest api for get all data from Department by DataBase.
     * Sample curl response:
     * $ curl -i http://localhost:8080/department
     *
     * @return - all data.
     */
    @GetMapping(value = "/department")
    public List<Department> findAll() {
        return new ArrayList<>(this.departmentService.findAll());
    }

    /**
     * Rest api for get Department by name data from Department by DataBase.
     * Sample curl response:
     * $ curl -i http://localhost:8080/department/engineer
     *
     * @return - department by name.
     */
    @GetMapping("/department/{name}")
    public ResponseEntity<Department> findById(@PathVariable String name) {
        Department department = departmentService.findDepartmentByName(name);
        return new ResponseEntity<Department>(department, department != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Rest api for get Department by person id by general table department_person.
     * Sample curl response:
     * $ curl -i http://localhost:8080/department/byperson/1
     *
     * @return - department by person.
     */
    @GetMapping("/department/byperson/{id}")
    public ResponseEntity<Department> findDepartmentByIdOfPerson(@PathVariable int id) {
        Department department = departmentService.findDepartmentByIdOfPerson(id);
        return new ResponseEntity<Department>(department, department != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Rest api for get Department by person id by general table department_person.
     * Sample curl response:
     * $ curl -i http://localhost:8080/department/allperson/1
     *
     * @return - list of persons by department.
     */
    @GetMapping("/department/allperson/{id}")
    public ResponseEntity<List<Person>> findPersonBelongDepartmentByIdDepartment(@PathVariable int id) {
        List<Person> persons = departmentService.getAllIdPerson(id);
        return new ResponseEntity<List<Person>>(persons, persons != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Rest api for add to Department DataBase by department.
     * Sample curl response:
     * $ curl -H 'Content-Type: application/json' -X POST -d '{"name":"it"}' http://localhost:8080/department
     *
     * @param department - department to add.
     */
    @PostMapping("/department")
    public void addPerson(@RequestBody Department department) {
        departmentService.addDepartment(department);
    }

    /**
     * Rest api for put(update) from Department DataBase by department.
     * Sample curl response:
     * $ curl -i -H 'Content-Type: application/json' -X PUT -d '{"id":"2","name":"new"}' http://localhost:8080/department/put
     *
     * @param department - by department.
     * @return - response.
     */
    @PutMapping("/department/put")
    public ResponseEntity<Void> updatePerson(@RequestBody Department department) {
        this.departmentService.updateDepartment(department);
        return ResponseEntity.ok().build();
    }

    /**
     * Rest api for delete from Department DataBase by name.
     * Sample curl response:
     * $ curl -i -X DELETE http://localhost:8080/department/engineer
     *
     * @param name - by name remove.
     * @return - response.
     */
    @DeleteMapping("/department/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        Department department = departmentService.findDepartmentByName(name);
        departmentService.deleteDepartment(department);
        return ResponseEntity.ok().build();
    }

}
