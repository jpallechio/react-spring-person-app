package com.galvanize.person.controllers;

import com.galvanize.person.models.Person;
import com.galvanize.person.repositories.PersonRepository;
import com.galvanize.person.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PersonController {

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonService personService;

    @GetMapping("/api/person")
    public List<Person> getPersonList() {
        return personService.getPersonList();
    }

    @PostMapping("/api/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping("/api/person/{id}")
    public Person editPerson(@RequestBody Person person, @PathVariable int id) {
        return personService.editPerson(person, id);
    }

    @DeleteMapping("/api/person/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }
}
