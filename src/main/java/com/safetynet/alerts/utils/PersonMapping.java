package com.safetynet.alerts.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;

/**
 * This class is in charge of conversion between Person and PersonEntity
 * classes.
 *
 * @author Thierry Schreiner
 */
@Component
public class PersonMapping {

    /**
     * Create a instance of the interface AddressFireStationRepository to
     * dialogue with the table address of the DataBase.
     */
    @Autowired
    private AddressFireStationRepository addressFireStationRepository;

    /**
     * This method convertToPersonEntity(List<Person> pListPerson) convert a
     * list of Person to a list of PersonEntity, using the next method
     * convertToPersonEntity(Person pPerson) as a sub-method to convert each
     * person of the list to a personEntity.
     *
     * @param pListPerson - a list of Person objects
     * @return a list of PersonEntity objects
     */
    public List<PersonEntity> convertToPersonEntity(
            final List<Person> pListPerson) {
        List<PersonEntity> listPE = new ArrayList<PersonEntity>();
        PersonEntity pEnt;
        for (Person p : pListPerson) {
            pEnt = convertToPersonEntity(p);
            listPE.add(pEnt);
        }
        return listPE;
    }

    /**
     * This method convert a Person to PersonEntity.
     *
     * @param pPerson - the Person object to convert
     * @return a PersonEntity object
     */
    public PersonEntity convertToPersonEntity(final Person pPerson) {
        PersonEntity pEnt = new PersonEntity();
        pEnt.setFirstName(pPerson.getFirstName());
        pEnt.setLastName(pPerson.getLastName());
        pEnt.setAddressFireSt(addressFireStationRepository
                .findByAddress(pPerson.getAddress()));
        pEnt.setPhone(pPerson.getPhone());
        pEnt.setEmail(pPerson.getEmail());
        pEnt.setMedRecId(null);
        return pEnt;
    }

    /**
     * This method is used to transform a list of PersonEntity to a list of
     * Person, using the next method to convert each PersonEntity to Person.
     *
     * @param listPE - the list of PersonEntity
     * @return a List<Person> object
     */
    public List<Person> convertToPerson(final List<PersonEntity> listPE) {
        List<Person> listPersons = new ArrayList<Person>();
        Person person;
        for (PersonEntity pEnt : listPE) {
            person = convertToPerson(pEnt);
            listPersons.add(person);
        }

        return (listPersons);
    }

    /**
     * This method convert a PersonEntity to a Person.
     *
     * @param pEnt - the PersonEntity object to convert
     * @return a Person object
     */
    public Person convertToPerson(final PersonEntity pEnt) {
        Person person = new Person();
        person.setId(pEnt.getId());
        person.setFirstName(pEnt.getFirstName());
        person.setLastName(pEnt.getLastName());
        person.setAddress(pEnt.getAddressFireSt().getAddress());
        person.setCity(pEnt.getAddressFireSt().getCity());
        person.setZip(pEnt.getAddressFireSt().getZip());
        person.setPhone(pEnt.getPhone());
        person.setEmail(pEnt.getEmail());

        return person;
    }

}
