package com.galvanize.person.services;

import com.galvanize.person.models.Person;
import com.galvanize.person.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPersonList() {
        return personRepository.findAllByOrderByIdAsc();
    }

    public Person addPerson(Person person) {
        String firstName = person.getFirstName().trim();
        if (!firstName.equals("")) {
            Person newPerson = new Person(person.getFirstName(), person.getLastName());
            return(personRepository.save(newPerson));
        }
        else {
            return new Person(-1);
        }
    }

    public Person editPerson(Person person, int id) {
        String firstName = person.getFirstName().trim();
        String lastName = person.getLastName().trim();
        if (!firstName.equals("") && !lastName.equals("")) {
            Person savedPerson = personRepository.findOneById(id);
            savedPerson.setFirstName(firstName);
            savedPerson.setLastName(lastName);
            return(personRepository.save(savedPerson));
        } else {
            return new Person(-1);
        }
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }
}
