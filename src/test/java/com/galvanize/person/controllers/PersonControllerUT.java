package com.galvanize.person.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.person.models.Person;
import com.galvanize.person.repositories.PersonRepository;
import com.galvanize.person.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerUT {
    @Autowired
    MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldGetPerson() throws Exception {
        // Setup
        Person person = new Person("Nathan", "Zukoff");
        List<Person> expected = new ArrayList<>();
        expected.add(person);
        when(this.personService.getPersonList()).thenReturn(expected);

        // Exercise
        this.mvc.perform(get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Nathan")));
    }

    @Test
    public void shouldAddVideo() throws Exception {
        // Setup
        Person expected = new Person("Nathan", "Zukoff");
        when(this.personService.addPerson(Mockito.any(Person.class))).thenReturn(expected);

        // Exercise
        this.mvc.perform(post("/api/person")
                .content(mapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Nathan")));
    }

    @Test
    public void shouldEditVideo() throws Exception {
        // Setup
        Person expected = new Person("Nathan", "Zukoff");
        expected.setId(3);
        when(this.personService.editPerson(Mockito.any(Person.class), anyInt())).thenReturn(expected);

        // Exercise
        this.mvc.perform(put("/api/person/3")
                .content(mapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Nathan")));
    }

    @Test
    public void shouldDeleteVideo() throws Exception {
        // Exercise
        this.mvc.perform(delete("/api/person/3"))
                .andExpect(status().isOk());

        verify(this.personService).deletePerson(3);
    }
}

























