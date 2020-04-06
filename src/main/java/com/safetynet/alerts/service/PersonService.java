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

    public List<Person> addListPersons(List<Person> pListPerson) {
        List<Person> createdList = (List<Person>) personRepository.saveAll(pListPerson);
        return createdList;
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
        Person foundPerson = personRepository
                .findByLastNameAndFirstName(pPerson.getLastName(), pPerson.getFirstName());
        if (foundPerson == null) {
            Person addedPerson = personRepository.save(pPerson);
            return addedPerson;
        }else {
            return null;
        }
    }

    public Person updatePerson(Person pPerson) {
        Person personToUpdate = pPerson;
        Person foundPerson = personRepository.findByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        personToUpdate.setId(foundPerson.getId());
        Person updatedPerson = personRepository.save(personToUpdate);
        return updatedPerson;
    }

    public void deleteAPerson(final String pLastName, final String pFirstName) {
        Person foundPerson = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        personRepository.deleteById(foundPerson.getId());
    }

}
