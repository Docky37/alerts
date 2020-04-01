package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.Repositery.PersonRepositery;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

@Service
public class PersonService {

    private PersonRepositery personRepositery;

    public PersonService(PersonRepositery pPersonRepositery) {
        personRepositery = pPersonRepositery;
    }

    public List<Person> findAll() {
        List<Person> personList = personRepositery.findAll();
        return personList;
    }

    public Person findByLastNameAndFirstName(final String pLastName,
            final String pFirstName) {
        Person foundPerson = personRepositery.findByLastNameAndFirstName(pLastName,pFirstName);
        if (foundPerson == null) {
            throw new PersonNotFoundException();
        }
        return foundPerson;
    }

    public Person addPerson(Person pPerson) {
        Person addedPerson = personRepositery.addPerson(pPerson);
        return addedPerson;
    }

    public Person updatePerson(Person pPerson) {
        Person updatedPerson = personRepositery.updatePerson(pPerson);
        return updatedPerson;
    }

    public Boolean deletePerson(Person pPerson) {
        Boolean isPersonDeleted = personRepositery.deletePerson(pPerson);
        return isPersonDeleted;
    }
}
