package com.safetynet.alerts.service;

import java.util.ArrayList;
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
     * The balanceSheet is a report of the batch import of person list.
     */
    private String balanceSheet;

    /**
     * Getter of balanceSheet.
     *
     * @return the balanceSheet
     */
    @Override
    public String getBalanceSheet() {
        return balanceSheet;
    }

    /**
     * Used to add line break in String.
     */
    private String newLine = System.getProperty("line.separator");

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
     * The find all method allows user to get a list of all persons saved in DB.
     *
     * @return a List<PersonDTO> (of saved persons)
     */
    @Override
    public List<PersonDTO> findAll() {
        LOGGER.debug(" | PersonService 'Find all persons' start -->");
        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findAll();
        List<PersonDTO> personList = personMapping.convertToPerson(listPE);
        if (personList.size() > 0) {
            for (PersonDTO personDTO : personList) {
                LOGGER.debug(" |  {}", personDTO.toString());
            }
        } else {
            LOGGER.error(" | !   NO PERSON IN DATABASE!");
        }
        LOGGER.debug(" | End of PersonService 'Find all persons.' ---");
        return personList;
    }

    /**
     * The addListPersons method allows user to save a list of persons in DB.
     *
     * @param pListPerson
     * @return a List<PersonDTO> (of created persons)
     */
    @Override
    public List<PersonDTO> addListPersons(final List<PersonDTO> pListPerson) {
        LOGGER.debug(" | PersonService 'Add a list of persons' start -->");
        balanceSheet = "";
        List<PersonDTO> createdList = (new ArrayList<PersonDTO>());

        PersonDTO createPersonDTO;
        int countOfCreatedPerson = 0;
        int countOfRejectedPerson = 0;
        for (PersonDTO personDTO : pListPerson) {
            createPersonDTO = addPerson(personDTO);
            if (createPersonDTO == null) { // MedicalRecord not created.
                countOfRejectedPerson++;
                if (balanceSheet.isEmpty()) {
                    balanceSheet = "These registred persons have not been"
                            + " created, to avoid duplicates: " + newLine;
                }
                balanceSheet = balanceSheet
                        .concat("   -  " + personDTO.toString()) + newLine;
            } else {
                createdList.add(createPersonDTO);
                countOfCreatedPerson++;
            }
        }
        balanceSheet = balanceSheet.concat(newLine + "Balance sheet: "
                + countOfCreatedPerson + " persons have been created and "
                + countOfRejectedPerson + " rejected.");
        LOGGER.debug(" | Balance sheet:  {} persons created and {} rejected.",
                countOfCreatedPerson, countOfRejectedPerson);
        LOGGER.debug(" | End of PersonService 'Add a list of persons.' ---");

        if (createdList.size() > 0) {
            return createdList;
        }
        return new ArrayList<PersonDTO>();
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
        LOGGER.debug(" | PersonService 'Add a person' start -->");
        PersonEntity pEnt = personRepository.findByLastNameAndFirstName(
                pPerson.getLastName(), pPerson.getFirstName());
        if (pEnt == null) {
            pEnt = personMapping.convertToPersonEntity(pPerson);
            PersonEntity addedPerson = personRepository.save(pEnt);
            pEnt = personRepository.findByLastNameAndFirstName(
                    addedPerson.getLastName(), addedPerson.getFirstName());
            LOGGER.debug(" |   + Person '{} {}' has been successfully created.",
                    pPerson.getFirstName(), pPerson.getLastName());
            LOGGER.debug(" | End of PersonService 'Add a person.' ---");
            return personMapping.convertToPerson(pEnt);
        } else {
            LOGGER.error(
                    " | !   Cannot create this new Person, {} {} "
                            + "is already registred!",
                    pPerson.getFirstName() + " " + pPerson.getLastName());
            LOGGER.debug(" | End of PersonService 'Add a person.' ---");
            return null;
        }
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
        LOGGER.debug(" | PersonService 'Find a person ({} {})' start -->",
                pFirstName, pLastName);
        PersonEntity foundPE = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (foundPE == null) {
            LOGGER.error(" |  PERSON NOT FOUND!");
            LOGGER.debug(" | End of PersonService 'Find a person.' ---");
            throw new PersonNotFoundException();
        }
        PersonDTO foundPerson = personMapping.convertToPerson(foundPE);
        LOGGER.debug(" |  {}", foundPerson.toString());
        LOGGER.debug(" | End of PersonService 'Find a person.' ---");
        return foundPerson;
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
        LOGGER.debug(" | PersonService 'Update the person {} {}' start -->",
                pFirstName, pLastName);
        PersonEntity pEnt = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (pEnt == null) { // Unregistred person
            LOGGER.error(
                    " | !   Cannot update this unregistered person ({} {})!",
                    pFirstName, pLastName);
            LOGGER.debug(" | End of PersonService 'Update a person'. ---");
            throw new PersonNotFoundException();
        } else if (pEnt.getFirstName().contentEquals(pPerson.getFirstName())
                && pEnt.getLastName().contentEquals(pPerson.getLastName())) {
            long id = pEnt.getId();
            pEnt = personMapping.convertToPersonEntity(pPerson);
            if (pEnt == null) { // Unknown address
                LOGGER.error(
                        " | !   Cannot update {} {} "
                                + "with an unknown address ({} {} {}) !",
                        pPerson.getFirstName(), pPerson.getLastName(),
                        pPerson.getAddress(), pPerson.getZip(),
                        pPerson.getCity());
                LOGGER.debug(" | End of PersonService 'Update a person'. ---");
                pPerson.setAddress("'? "+ pPerson.getAddress()+ "?'");
                return pPerson;
            }
            // Successful update
            pEnt.setId(id);
            PersonEntity updatedPEnt = personRepository.save(pEnt);
            PersonDTO updatePersonDTO = personMapping
                    .convertToPerson(updatedPEnt);
            LOGGER.debug(" |   OK now this person is updated: {}.",
                    updatePersonDTO.toString());
            LOGGER.debug(" | End of PersonService 'Update a person'. ---");
            return updatePersonDTO;
        }
        LOGGER.debug(" | End of PersonService 'Update a person'. ---");
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
     * @throws PersonNotFoundException
     */
    @Override
    public PersonDTO deleteAPerson(final String pLastName,
            final String pFirstName) throws PersonNotFoundException {
        LOGGER.debug(
                " | PersonService - Delete the person named '{} {}' start -->",
                pLastName, pFirstName);
        PersonEntity pEnt = personRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (pEnt != null) {
            PersonDTO deletedPersonDTO = personMapping.convertToPerson(pEnt);
            personRepository.deleteById(pEnt.getId());
            LOGGER.debug(" |   OK now person '{}' is deleted.'",
                    deletedPersonDTO.toString());
            LOGGER.debug(" | End of PersonService 'Delete a person.' ---");
            return personMapping.convertToPerson(pEnt);
        }
        LOGGER.error(" |   PERSON NOT FOUND!");
        LOGGER.debug(" | End of PersonService 'Delete a person.' ---");
        throw new PersonNotFoundException();
    }

}
