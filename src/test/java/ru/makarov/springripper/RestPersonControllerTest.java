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
import ru.makarov.springripper.domain.Person;
import ru.makarov.springripper.service.PersonServis;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("REST API PERSON ability test")
public class RestPersonControllerTest {
    @Autowired
    private PersonServis servis;

    @Autowired
    private MockMvc mock;

    @DisplayName(" проверка соеденения с REST API GET Person по findALL - в List должно быть 6 элементов")
    @Test
    public void shouldChelkGetAllPersons() throws Exception {
        String json = this.mock.perform(get("/person"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Person>> typeReference =
                new TypeReference<List<Person>>() {
                };
        List<Person> persons = mapper.readValue(json, typeReference);
        assertThat(persons.size()).isEqualTo(6);
    }

    @DisplayName(" проверка соеденения с REST API GET Person по find by Id - id = 1 вернулся пользвователь Sidorov1")
    @Test
    public void shouldChelkGetPersonByID() throws Exception {
        String json = this.mock.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(json, Person.class);
        assertThat(person.getName()).isEqualTo("Sidorov1");
    }

    @DisplayName(" проверка соеденения с REST API POST Person по add person  - id = 7 вернулся добавленный элемент Vlad")
    @Test
    public void shouldChelkPostPersonAdd() throws Exception {
        this.mock.perform(
                post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Vlad\"}"))
                .andExpect(status().isOk());
        Person person = servis.findPersonById(7);
        servis.deletePerson(person);
        assertThat(person.getName()).isEqualTo("Vlad");
    }

    @DisplayName(" проверка соеденения с REST API PUT Person по updatePerson person  - id = 3 вернулся обновленный элемент Vlad1")
    @Test
    public void shouldCheckPutUpdatePerson() throws Exception {
        mock.perform(put("/person/put").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":\"3\",\"name\":\"Vlad1\"}"))
                .andExpect(status().isOk());
        Person personUpdate = servis.findPersonById(3);
        assertThat(personUpdate.getName()).isEqualTo("Vlad1");
    }


    @DisplayName(" проверка соеденения с REST API DELETE Person по delete person  - size = 5 после удаления параметра")
    @Test
    @Sql(scripts = "/create-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCheckDeletePersonById() throws Exception {
        this.mock.perform(MockMvcRequestBuilders
                .delete("/person/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<Person> persons = servis.findAll();
        assertThat(persons.size()).isEqualTo(5);
    }
}
