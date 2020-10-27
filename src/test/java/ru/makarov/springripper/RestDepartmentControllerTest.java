package ru.makarov.springripper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.makarov.springripper.domain.Department;
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.service.DepartmentService;
import ru.makarov.springripper.service.PersonServis;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("REST API DEPARTMENT ability test")
public class RestDepartmentControllerTest {
    @Autowired
    private DepartmentService servis;

    @Autowired
    private MockMvc mock;

    @DisplayName(" проверка соеденения с REST API GET Department по findALL - в List должно быть 3 элементов")
    @Test
    public void shouldChelkGetAllDepartments() throws Exception {
        String json = this.mock.perform(get("/department"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Department>> typeReference =
                new TypeReference<List<Department>>() {
                };
        List<Department> departments = mapper.readValue(json, typeReference);
        assertThat(departments.size()).isEqualTo(3);
    }

    @DisplayName(" проверка соеденения с REST API GET Department по find by name - name = engineer вернулся департамент engineer")
    @Test
    public void shouldChelkGetDepartmentByID() throws Exception {
        String json = this.mock.perform(get("/department/engineer"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Department department = mapper.readValue(json, Department.class);
        assertThat(department.getName()).isEqualTo("engineer");
    }

    @DisplayName(" проверка соеденения с REST API GET Department по find by id person в общей таблице" +
            " - id = 1 вернулся департамент engineer")
    @Test
    public void shouldChelkGetDepartmentByIDOfPersonInGeneralTable() throws Exception {
        String json = this.mock.perform(get("/department/byperson/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Department department = mapper.readValue(json, Department.class);
        assertThat(department.getName()).isEqualTo("engineer");
    }

    @DisplayName(" проверка соеденения с REST API GET List of Persons по find by id department в общей таблице" +
            " - id = 1 вернутся два пользователя")
    @Test
    public void shouldChelkGetAllPersonsByIdOfDepartment() throws Exception {
        String json = this.mock.perform(get("/department/allperson/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Person>> typeReference =
                new TypeReference<List<Person>>() {
                };
        List<Person> persons = mapper.readValue(json, typeReference);
        assertThat(persons.size()).isEqualTo(2);
    }

    @DisplayName(" проверка соеденения с REST API POST Department по add department  - name = it вернулся добавленный элемент it")
    @Test
    @Sql(scripts = "/create-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldChelkPostDepartmentAdd() throws Exception {
        this.mock.perform(
                post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"it\"}"))
                .andExpect(status().isOk());
        Department department = servis.findDepartmentByName("it");
        assertThat(department.getName()).isEqualTo("it");
    }

    @DisplayName(" проверка соеденения с REST API PUT Department по updateDepartment person  - name = new вернулся обновленный элемент new")
    @Test
    public void shouldCheckPutUpdatePerson() throws Exception {
        mock.perform(put("/department/put").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":\"2\",\"name\":\"new\"}"))
                .andExpect(status().isOk());
        Department department = servis.findDepartmentByName("new");
        assertThat(department.getName()).isEqualTo("new");
    }


    @DisplayName(" проверка соеденения с REST API DELETE Department по delete department  - size = 3 после удаления параметра 2")
    @Test
    @Sql(scripts = "/create-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCheckDeletePersonById() throws Exception {
        this.mock.perform(MockMvcRequestBuilders
                .delete("/department/engineer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<Department> departments = servis.findAll();
        assertThat(departments.size()).isEqualTo(2);
    }
}
