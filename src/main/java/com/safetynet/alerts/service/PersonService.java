package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.DozerMappingUtil;
import com.safetynet.alerts.service.util.PersonMapping;

/**
 * PersonService is the class in charge of the person business work.
 *
 * @author Thierry Schreiner
 */
@Service
public class PersonService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * PersonRepository is an Interface that extends CrudRepository.
     */
    private PersonRepository personRepository;

    @Autowired
    private PersonMapping personMapping;
    /**
     * Class constructor - Set personRepository (IoC).
     *
     * @param pPersonRepositery
     */
    public PersonService(final PersonRepository pPersonRepositery) {
        personRepository = pPersonRepositery;
    }

    /**
     * The addListPersons method allows user to save a list of persons in DB.
     *
     * @param pListPerson
     * @return a List<Person> (of created persons)
     */
    public List<Person> addListPersons(final List<Person> pListPerson) {
        List<PersonEntity> listPE = personMapping.convertToPersonEntity(pListPerson);


        List<PersonEntity> createdList = (List<PersonEntity>) personRepository
                .saveAll(listPE);
        return pListPerson;
    }

    /**
     * The find all method allows user to get a list of all persons saved in DB.
     *
     * @return a List<Person> (of saved persons)
     */
    /*
     * public List<Person> findAll() { List<Person> personList = (List<Person>)
     * personRepository.findAll(); return personList; }
     * 
     * /** The findByLastNameAndFirstName method that allows user to find a
     * person in DB.
     *
     * @param pLastName
     * 
     * @param pFirstName
     * 
     * @return a Person (the found person)
     */
    /*
     * public Person findByLastNameAndFirstName(final String pLastName, final
     * String pFirstName) { Person foundPerson = personRepository
     * .findByLastNameAndFirstName(pLastName, pFirstName); if (foundPerson ==
     * null) { throw new PersonNotFoundException(); } return foundPerson; }
     * 
     * /** The addPerson method that first verify if the person already exists
     * in DB before adding him with the save method of CrudRepository.
     *
     * @param pPerson
     * 
     * @return a Person (the added person) or null if person already exists.
     */
    /*
     * public Person addPerson(final Person pPerson) { Person foundPerson =
     * personRepository.findByLastNameAndFirstName( pPerson.getLastName(),
     * pPerson.getFirstName()); if (foundPerson == null) { Person addedPerson =
     * personRepository.save(pPerson); return addedPerson; } else { return null;
     * } }
     * 
     * /** Update method that uses first findByLastNameAndFirstName to find the
     * Person to update in DB and invokes the save method of CrudRepository.
     *
     * @param pPerson
     * 
     * @return a Person (the updated person)
     */
    /*
     * public Person updatePerson(final Person pPerson) { Person personToUpdate
     * = pPerson; Person foundPerson =
     * personRepository.findByLastNameAndFirstName( pPerson.getLastName(),
     * pPerson.getFirstName()); personToUpdate.setId(foundPerson.getId());
     * Person updatedPerson = personRepository.save(personToUpdate); return
     * updatedPerson; }
     * 
     * /** Delete method that uses first findByLastNameAndFirstName to find the
     * Person to delete in DB and get its id to invokes the deleteById method of
     * CrudRepository.
     *
     * @param pLastName
     * 
     * @param pFirstName
     * 
     * @return a Person (the deleted person) or null if not found.
     */
    /*
     * public Person deleteAPerson(final String pLastName, final String
     * pFirstName) { Person foundPerson = personRepository
     * .findByLastNameAndFirstName(pLastName, pFirstName); if (foundPerson !=
     * null) { personRepository.deleteById(foundPerson.getId()); } return
     * foundPerson; }
     */
}
