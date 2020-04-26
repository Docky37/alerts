package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.Person;

public interface IPersonService {

    /**
     * The addListPersons method allows user to save a list of persons in DB.
     *
     * @param pListPerson
     * @return a List<Person> (of created persons)
     */
    List<Person> addListPersons(List<Person> pListPerson);

    /**
     * The find all method allows user to get a list of all persons saved in DB.
     *
     * @return a List<Person> (of saved persons)
     */
    List<Person> findAll();

    /**
     * The findByLastNameAndFirstName method that allows user to find a person
     * in DB.
     *
     * @param pLastName
     * @param pFirstName
     * @return a Person (the found person)
     */
    Person findByLastNameAndFirstName(String pLastName, String pFirstName);

    /**
     * The addPerson method first uses the
     * personRepository.findByLastNameAndFirstName method to verify if the
     * person already exists in DB. If not, it calls the method
     * personMapping.convertToPersonEntity(pPerson), and then it add the
     * PersonEntity returned with the save method of CrudRepository.
     *
     * @param pPerson
     * @return a Person (the added person) or null if person already exists.
     */
    Person addPerson(Person pPerson);

    /**
     * The updatePerson method first uses the
     * personRepository.findByLastNameAndFirstName method to verify if the
     * person already exists in DB. If person exists, it calls the method
     * personMapping.convertToPersonEntity(pPerson), and then it calls the save
     * method of CrudRepository. It ends returning the convertToPerson return of
     * the personRepository.save method.
     *
     * @param pPerson - the person to update
     * @return a Person (the updated person) or null if person to update not
     *         found.
     */
    Person updatePerson(Person pPerson);

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * Person to delete in DB and get its id to invokes the deleteById method of
     * CrudRepository.
     *
     * @param pLastName
     * @param pFirstName
     * @return a Person (the deleted person) or null if person to delete not
     *         found.
     */
    Person deleteAPerson(String pLastName, String pFirstName);

}