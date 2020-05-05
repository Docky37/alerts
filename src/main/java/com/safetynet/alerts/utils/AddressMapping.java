package com.safetynet.alerts.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.DTO.AddressDTO;
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
     * This method convert a AddressDTO to AddressEntity.
     *
     * @param pAddressDTO - the AddressDTO object to convert
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
     * @param pAddressEntityList - the list of AddressEntity
     * @return a List<AddressDTO> object
     */
    public List<AddressDTO> convertToAddressDTO(
            final List<AddressEntity> pAddressEntityList) {
        List<AddressDTO> addressDTOList = new ArrayList<AddressDTO>();
        AddressDTO addressDTO;
        for (AddressEntity addressEntity : pAddressEntityList) {
            addressDTO = convertToAddressDTO(addressEntity);
            addressDTOList.add(addressDTO);
        }

        return (addressDTOList);
    }

    /**
     * This method convert a AddressEntity to a AddressDTO.
     *
     * @param pAddressEntity - the AddressEntity object to convert
     * @return a AddressDTO object
     */
    public AddressDTO convertToAddressDTO(final AddressEntity pAddressEntity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddress(pAddressEntity.getAddress());
        addressDTO.setCity(pAddressEntity.getCity());
        addressDTO.setZip(pAddressEntity.getZip());
        addressDTO.setStation(pAddressEntity.getStation());

        return addressDTO;
    }

}
