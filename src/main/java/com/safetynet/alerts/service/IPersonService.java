package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.controller.PersonNotFoundException;

public interface IPersonService {

    /**
     * The addListPersons method allows user to save a list of persons in DB.
     *
     * @param pListPerson
     * @return a List<PersonDTO> (of created persons)
     */
    List<PersonDTO> addListPersons(List<PersonDTO> pListPerson);

    /**
     * The find all method allows user to get a list of all persons saved in DB.
     *
     * @return a List<PersonDTO> (of saved persons)
     */
    List<PersonDTO> findAll();

    /**
     * The findByLastNameAndFirstName method that allows user to find a person
     * in DB.
     *
     * @param pLastName
     * @param pFirstName
     * @return a PersonDTO (the found person)
     */
    PersonDTO findByLastNameAndFirstName(String pLastName, String pFirstName)
            throws PersonNotFoundException;

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
    PersonDTO addPerson(PersonDTO pPerson);

    /**
     * The updatePerson method first uses the
     * personRepository.findByLastNameAndFirstName method to verify if the
     * person already exists in DB. If person exists, it calls the method
     * personMapping.convertToPersonEntity(pPerson), and then it calls the save
     * method of CrudRepository. It ends returning the convertToPerson return of
     * the personRepository.save method.
     *
     * @param pLastName  - the LastName of the person to update
     * @param pFirstName - the FirstName of the person to update
     * @param pPerson    - the person to update
     * @return a PersonDTO (the updated person) or null if person to update not
     *         found.
     */
    PersonDTO updatePerson(String pLastName, String pFirstName,
            PersonDTO pPerson) throws PersonNotFoundException;

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
    PersonDTO deleteAPerson(String pLastName, String pFirstName);

}
