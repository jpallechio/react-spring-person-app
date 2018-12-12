package com.galvanize.person.services;

import com.galvanize.person.models.Person;
import com.galvanize.person.repositories.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
public class PersonServiceUT {

    @TestConfiguration
    static class PersonServiceTestConfiguration {
        @Bean
        public PersonService personService() {
            return new PersonService();
        }
    }

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Captor
    ArgumentCaptor argCaptor;

    @Test
    public void shouldGetPeople() throws Exception {
        // Setup
        Person person = new Person("Nathan", "Zukoff");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(this.personRepository.findAllByOrderByIdAsc()).thenReturn(personList);

        // Exercise
        List<Person> returnedPeople = this.personService.getPersonList();

        // Assert
        assertEquals(returnedPeople, personList);
    }

    @Test
    public void shouldAddPersonValidPerson() throws Exception {
        // Setup
        Person person = new Person("Nathan", "Zukoff");
        when(this.personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        // Exercise
        Person returnedPerson = this.personService.addPerson(person);

        // Assert
        verify(this.personRepository).save(argThat(per -> per.getFirstName().equals("Nathan")));
        assertEquals(returnedPerson.getFirstName(), person.getFirstName());
    }

    @Test
    public void shouldAddPersonBlankPerson() throws Exception {
        // Setup
        Person person = new Person("","");
        Person expected = new Person(-1);

        // Exercise
        Person returnedPerson = this.personService.addPerson(person);

        // Assert
        verify(this.personRepository, never()).save(Mockito.any(Person.class));
        assertEquals(returnedPerson.getId(), expected.getId());
    }

    @Test
    public void shouldEditPersonValidPerson() throws Exception {
        // Setup
        Person origPerson = new Person("Nathan", "Zukoff");
        origPerson.setId(3);
        Person person = new Person("Joe", "Peterson");
        person.setId(3);
        when(this.personRepository.findOneById(anyInt())).thenReturn(origPerson);
        when(this.personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        // Exercise
        Person returnedPerson = this.personService.editPerson(person, 3);

        // Assert
        verify(this.personRepository).findOneById(3);
        verify(this.personRepository).save(argThat(per -> per.getFirstName().equals("Joe")));
        verify(this.personRepository).save(argThat(per -> per.getId() == 3));
        assertEquals(returnedPerson.getFirstName(), person.getFirstName());
    }
//
//    @Test
//    public void shouldEditVideoBlankVideo() throws Exception {
//        // Setup
//        Person origVideo = new Person("");
//        origVideo.setId(3);
//        Person expected = new Person(-1);
//
//        // Exercise
//        Person returnedVideo = this.videoService.editVideo(origVideo, 3);
//
//        // Assert
//        verify(this.videoRepository, never()).findOneById(anyInt());
//        verify(this.videoRepository, never()).save(Mockito.any(Person.class));
//        assertEquals(returnedVideo.getId(), expected.getId());
//    }

    @Test
    public void shouldDeletePerson() throws Exception {
        // Exercise
        this.personService.deletePerson(3);

        // Assert
        verify(this.personRepository).deleteById(3);
    }
}