package ru.makarov.springripper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.makarov.springripper.domain.Department;

import java.util.List;
import java.util.Map;

/**
 * JpaRepository for Department DATA.
 */
@Repository
public interface DepartmentJpaRepo extends JpaRepository<Department, Integer> {
    /**
     * Data of select all person belongs to Department.
     *
     * @param idDepartment - id of department.
     * @return - list of data select.
     */
    @Query(value = "select p.id,p.name from person as p  left join department_person as d on (d.person_id = p.id)  where d.department_id = :id", nativeQuery = true)
    List<Map<String, Object>> getAllIdPerson(@Param("id") Integer idDepartment);

    /**
     * Data of select department by id Person.
     *
     * @param id - id person.
     * @return - department select.
     */
    @Query(value = "select * from department left join department_person d on (d.department_id = department.id)  where d.person_id = :id", nativeQuery = true)
    Department findDepartmentByIdOfPerson(@Param("id") Integer id);

    /**
     * General query for init department by name.
     *
     * @param name - name of department.
     * @return - department.
     */
    Department findDepartmentByName(String name);

    /**
     * Remove person from general table department_person.
     *
     * @param id -  person.
     */
    @Modifying
    @Transactional
    @Query(value = "delete from department_person where department_id = :id", nativeQuery = true)
    void deleteDepartmentByIDFromGeneralTable(@Param("id") Integer id);



}
