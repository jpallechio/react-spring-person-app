package com.galvanize.person.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.person.models.Person;
import com.galvanize.person.repositories.PersonRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {
    @Autowired
    MockMvc mvc;

    @Autowired
    PersonRepository personRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        personRepository.deleteAll();
    }

    @After
    public void after() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldGetPeople() throws Exception {
        // Setup
        Person person = new Person("Joe", "Peterson");
        personRepository.save(person);
        String expected = person.getFirstName();

        // Exercise
        String response = mvc.perform(get("/api/person"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        // Assert
        assertNotEquals("should return persons name", -1, response.indexOf(expected));
    }

    @Test
    public void shouldAddPerson() throws Exception {
        // Setup
        String firstName = "Nathan";
        String lastName = "Zukoff";
        String newVideo = "{\"id\": 1, \"firstName\": \"Nathan\", \"lastName\": \"Zukoff\"}";
        Person expected = new Person(firstName, lastName);

        // Exercise
        String response = mvc.perform(post("/api/person")
                .content(newVideo).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Assert
        List<Person> people = personRepository.findAllByOrderByIdAsc();
        assertEquals(1, people.size());
        assertEquals(
                mapper.writeValueAsString(expected.getFirstName()),
                mapper.writeValueAsString(people.get(0).getFirstName())
        );
    }

    @Test
    public void shouldEditPerson() throws Exception {
        // Setup
        personRepository.deleteAll();
        Person existingPerson = new Person("Nathan", "Zukoff");
        existingPerson = personRepository.save(existingPerson);

        String expected = "Joe";
        String newVideo = "{\"id\": 1, \"firstName\": \"Joe\"}";

        // Exercise
        String response = mvc.perform(put("/api/person/" + existingPerson.getId())
                .content(newVideo).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Assert
        List<Person> people = personRepository.findAllByOrderByIdAsc();
        assertEquals(1, people.size());
        assertNotEquals(-1, response.indexOf(expected));
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        // Setup
        personRepository.deleteAll();
        Person existingPerson = new Person("Nathan", "Zukoff");
        existingPerson = personRepository.save(existingPerson);

        // Exercise
        String response = mvc.perform(delete("/api/person/" + existingPerson.getId()))
                // .content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Assert
        List<Person> people = personRepository.findAllByOrderByIdAsc();
        assertEquals(0, people.size());
    }
}
