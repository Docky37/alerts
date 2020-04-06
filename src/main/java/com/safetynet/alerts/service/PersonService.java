package com.safetynet.alerts.service;

import java.util.List;

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
        List<Person> personList = (List<Person>) personRepository.findAll();
        return personList;
    }

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
        Person addedPerson = personRepository.save(pPerson);
        return addedPerson;
    }

    public Person updatePerson(Person pPerson) {
        Person foundPerson = personRepository.findByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        pPerson.setId(foundPerson.getId());
        Person updatedPerson = personRepository.save(foundPerson);
        return updatedPerson;
    }

    public void deleteAPerson(Person pPerson) {
        Person foundPerson = personRepository.findByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        pPerson.setId(foundPerson.getId());
        personRepository.deleteById(pPerson.getId());
    }
}
