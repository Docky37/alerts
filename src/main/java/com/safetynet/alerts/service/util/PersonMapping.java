package com.safetynet.alerts.service.util;

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
    public List<PersonEntity> convertToPersonEntity(List<Person> pListPerson) {
        List<PersonEntity> listPE = new ArrayList<PersonEntity>();
        PersonEntity pEnt = new PersonEntity();
        for (Person p : pListPerson) {
            pEnt = convertToPersonEntity(p);
            listPE.add(pEnt);
            pEnt = new PersonEntity();
        }
        return listPE;
    }

    /**
     * This method convert a Person to PersonEntity.
     *
     * @param pPerson - the Person object to convert
     * @return a PersonEntity object
     */
    public PersonEntity convertToPersonEntity(Person pPerson) {
        PersonEntity pEnt = new PersonEntity();
        pEnt.setFirstName(pPerson.getFirstName());
        pEnt.setLastName(pPerson.getLastName());
        pEnt.setAddressFireSt(addressFireStationRepository
                .findByAddress(pPerson.getAddress()));
        pEnt.setPhone(pPerson.getPhone());
        pEnt.setEmail(pPerson.getEmail());

        return pEnt;
    }

}
