package com.safetynet.alerts.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.model.AddressDTO;
import com.safetynet.alerts.model.AddressEntity;

/**
 * This class is in charge of conversion between PersonDTO and AddressEntity
 * classes.
 *
 * @author Thierry Schreiner
 */
@Component
public class AddressMapping {

    /**
     * This method convertToAddressEntity(List<AddressDTO> pListAddress) convert a
     * list of AddressDTO to a list of AddressEntity, using the next method
     * convertToAddressEntity(AddressDTO pPerson) as a sub-method to convert each
     * person of the list to a personEntity.
     *
     * @param pListAddress - a list of AddressDTO objects
     * @return a list of AddressEntity objects
     */
    public List<AddressEntity> convertToAddressEntity(
            final List<AddressDTO> pListAddress) {
        List<AddressEntity> listPE = new ArrayList<AddressEntity>();
        AddressEntity addressEntity;
        for (AddressDTO address : pListAddress) {
            addressEntity = convertToAddressEntity(address);
            listPE.add(addressEntity);
        }
        return listPE;
    }

    /**
     * This method convert a AddressDTO to AddressEntity.
     *
     * @param pPerson - the AddressDTO object to convert
     * @return a AddressEntity object
     */
    public AddressEntity convertToAddressEntity(final AddressDTO pAddressDTO) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress(pAddressDTO.getAddress());
        addressEntity.setCity(pAddressDTO.getCity());
        addressEntity.setZip(pAddressDTO.getZip());
        addressEntity.setStation(pAddressDTO.getStation());
        return addressEntity;
    }

    /**
     * This method is used to transform a list of AddressEntity to a list of
     * AddressDTO, using the next method to convert each AddressEntity to
     * AddressDTO.
     *
     * @param listPE - the list of AddressEntity
     * @return a List<AddressDTO> object
     */
    public List<AddressDTO> convertToPerson(final List<AddressEntity> listPE) {
        List<AddressDTO> listPersons = new ArrayList<AddressDTO>();
        AddressDTO AddressDTO;
        for (AddressEntity addressEntity : listPE) {
            AddressDTO = convertToPerson(addressEntity);
            listPersons.add(AddressDTO);
        }

        return (listPersons);
    }

    /**
     * This method convert a AddressEntity to a AddressDTO.
     *
     * @param pAddressEntity - the AddressEntity object to convert
     * @return a AddressDTO object
     */
    public AddressDTO convertToPerson(final AddressEntity pAddressEntity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddress(pAddressEntity.getAddress());
        addressDTO.setCity(pAddressEntity.getCity());
        addressDTO.setZip(pAddressEntity.getZip());
        addressDTO.setStation(pAddressEntity.getStation());

        return addressDTO;
    }

}
