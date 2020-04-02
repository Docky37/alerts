package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositery.PersonRepository;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository pPersonRepositery) {
        personRepository = pPersonRepositery;
    }

    public List<Person> findAll() {
        List<Person> personList = personRepository.findAll();
        return personList;
    }

    @Cacheable("person")
    public Person findByLastNameAndFirstName(final String pLastName,
            final String pFirstName) {
        Person foundPerson = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (foundPerson == null) {
            throw new PersonNotFoundException();
        }
        return foundPerson;
    }

    public Person addPerson(Person pPerson) {
        Person addedPerson = personRepository.savePerson(pPerson);
        return addedPerson;
    }

    public Person updatePerson(Person pPerson) {
        Person updatedPerson = personRepository.updateByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        return updatedPerson;
    }

    public Boolean deletePerson(Person pPerson) {
        personRepository.deleteByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        return true;
    }
}
