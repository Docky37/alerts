package com.safetynet.alerts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.PersonMapping;

/**
 * PersonService is the class in charge of the person business work.
 *
 * @author Thierry Schreiner
 */
@Service
public class PersonService implements IPersonService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * PersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * PersonMapping is an utility that provides bidirectional conversion
     * between PersonDTO and PersoonEntity.
     */
    @Autowired
    private PersonMapping personMapping;

    /**
     * Class constructor - Set personRepository and personMapping(IoC).
     *
     * @param pPersonRepos
     * @param pPersonMapping
     */
    public PersonService(final PersonRepository pPersonRepos,
            final PersonMapping pPersonMapping) {
        personRepository = pPersonRepos;
        personMapping = pPersonMapping;
    }

    /**
     * The addListPersons method allows user to save a list of persons in DB.
     *
     * @param pListPerson
     * @return a List<PersonDTO> (of created persons)
     */
    @Override
    public List<PersonDTO> addListPersons(final List<PersonDTO> pListPerson) {
        List<PersonEntity> listPE = personMapping
                .convertToPersonEntity(pListPerson);

        List<PersonEntity> createdList = (List<PersonEntity>) personRepository
                .saveAll(listPE);

        return personMapping.convertToPerson(createdList);
    }

    /**
     * The find all method allows user to get a list of all persons saved in DB.
     *
     * @return a List<PersonDTO> (of saved persons)
     */
    @Override
    public List<PersonDTO> findAll() {
        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findAll();
        List<PersonDTO> personList = personMapping.convertToPerson(listPE);

        return personList;
    }

    /**
     * The findByLastNameAndFirstName method that allows user to find a person
     * in DB.
     *
     * @param pLastName
     * @param pFirstName
     * @return a PersonDTO (the found person)
     */
    @Override
    public PersonDTO findByLastNameAndFirstName(final String pLastName,
            final String pFirstName) throws PersonNotFoundException {
        PersonEntity foundPE = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (foundPE == null) {
            throw new PersonNotFoundException();
        }
        PersonDTO foundPerson = personMapping.convertToPerson(foundPE);
        return foundPerson;
    }

    /**
     * The addPerson method first uses the
     * personRepository.findByLastNameAndFirstName method to verify if the
     * person already exists in DB. If not, it calls the method
     * personMapping.convertToPersonEntity(pPerson), and then it add the
     * PersonEntity returned with the save method of CrudRepository.
     *
     * @param pPerson
     * @return a PersonDTO (the added person) or null if person already exists.
     */
    @Override
    public PersonDTO addPerson(final PersonDTO pPerson) {
        PersonEntity pEnt = personRepository.findByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        if (pEnt == null) {
            pEnt = personMapping.convertToPersonEntity(pPerson);
            PersonEntity addedPerson = personRepository.save(pEnt);
            pEnt = personRepository.findByLastNameAndFirstName(
                    addedPerson.getLastName(), addedPerson.getFirstName());
            return personMapping.convertToPerson(pEnt);
        }
        return null;
    }

    /**
     * The updatePerson method first uses the
     * personRepository.findByLastNameAndFirstName method to verify if the
     * person already exists in DB. If person exists, it calls the method
     * personMapping.convertToPersonEntity(pPerson), and then it calls the save
     * method of CrudRepository. It ends returning the convertToPerson return of
     * the personRepository.save method.
     *
     * @param pPerson - the person to update
     * @return a PersonDTO (the updated person) or null if person to update not
     *         found.
     */
    @Override
    public PersonDTO updatePerson(final String pLastName,
            final String pFirstName, final PersonDTO pPerson)
            throws PersonNotFoundException {
        PersonEntity pEnt = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (pEnt == null) {
            throw new PersonNotFoundException();
        } else if (pEnt.getFirstName().contentEquals(pPerson.getFirstName())
                && pEnt.getLastName().contentEquals(pPerson.getLastName())) {
            long id = pEnt.getId();
            pEnt = personMapping.convertToPersonEntity(pPerson);
            pEnt.setId(id);
            PersonEntity updatedPEnt = personRepository.save(pEnt);
            return personMapping.convertToPerson(updatedPEnt);
        }
        return null;
    }

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * PersonDTO to delete in DB and get its id to invokes the deleteById method
     * of CrudRepository.
     *
     * @param pLastName
     * @param pFirstName
     * @return a PersonDTO (the deleted person) or null if person to delete not
     *         found.
     */
    @Override
    public PersonDTO deleteAPerson(final String pLastName,
            final String pFirstName) {
        PersonEntity pEnt = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (pEnt != null) {
            personRepository.deleteById(pEnt.getId());
            return personMapping.convertToPerson(pEnt);
        }
        return null;
    }

}
