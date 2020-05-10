package com.safetynet.alerts.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressRepository;

/**
 * This class is in charge of conversion between PersonDTO and PersonEntity
 * classes.
 *
 * @author Thierry Schreiner
 */
@Component
public class PersonMapping {

    /**
     * Create a instance of the interface AddressRepository to dialogue with the
     * table address of the DataBase.
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * This method convert a PersonDTO to PersonEntity.
     *
     * @param pPerson - the PersonDTO object to convert
     * @return a PersonEntity object
     */
    public PersonEntity convertToPersonEntity(final PersonDTO pPerson) {
        if (addressRepository.findByAddress(pPerson.getAddress()) == null) {
            return null; // Address unknown in DataBase!
        } else {
            PersonEntity pEnt = new PersonEntity();
            pEnt.setFirstName(pPerson.getFirstName());
            pEnt.setLastName(pPerson.getLastName());
            pEnt.setAddressFireSt(
                    addressRepository.findByAddress(pPerson.getAddress()));
            pEnt.setPhone(pPerson.getPhone());
            pEnt.setEmail(pPerson.getEmail());
            pEnt.setMedRecId(null);
            return pEnt;
        }
    }

    /**
     * This method is used to transform a list of PersonEntity to a list of
     * PersonDTO, using the next method to convert each PersonEntity to
     * PersonDTO.
     *
     * @param listPE - the list of PersonEntity
     * @return a List<PersonDTO> object
     */
    public List<PersonDTO> convertToPerson(final List<PersonEntity> listPE) {
        List<PersonDTO> listPersons = new ArrayList<PersonDTO>();
        PersonDTO personDTO;
        for (PersonEntity pEnt : listPE) {
            personDTO = convertToPerson(pEnt);
            listPersons.add(personDTO);
        }

        return (listPersons);
    }

    /**
     * This method convert a PersonEntity to a PersonDTO.
     *
     * @param pEnt - the PersonEntity object to convert
     * @return a PersonDTO object
     */
    public PersonDTO convertToPerson(final PersonEntity pEnt) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(pEnt.getFirstName());
        personDTO.setLastName(pEnt.getLastName());
        personDTO.setAddress(pEnt.getAddressFireSt().getAddress());
        personDTO.setCity(pEnt.getAddressFireSt().getCity());
        personDTO.setZip(pEnt.getAddressFireSt().getZip());
        personDTO.setPhone(pEnt.getPhone());
        personDTO.setEmail(pEnt.getEmail());

        return personDTO;
    }

}
