package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.repositery.AddressRepository;
import com.safetynet.alerts.utils.AddressMapping;
import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.controller.AddressNotFoundException;

/**
 * AddressService is the class in charge of the business work about adress -
 * fire station association.
 *
 * @author Thierry Schreiner
 */
@Service
public class AddressService implements IAddressService {
    /**
     * PersonRepository is an Interface that extends CrudRepository.
     */
    private AddressRepository repository;
    /**
     * AddressMapping is an utility that provides bidirectional conversion
     * between AddressDTO and AddressEntity.
     */
    @Autowired
    private AddressMapping addressMapping;

    /**
     * Class constructor - Set addressFireStRepository (IoC).
     *
     * @param pAddressFireStRepositery
     * @param pAddressMapping
     */
    public AddressService(final AddressRepository pAddressFireStRepositery,
            final AddressMapping pAddressMapping) {
        repository = pAddressFireStRepositery;
        addressMapping = pAddressMapping;
    }

    /**
     * The addListFireStations method is used to add a list of addresses (with
     * each corresponding covering station) in the DataBase. The method is
     * called by the POST request on /firestations.
     *
     * @param pListAddress - a List<AddressDTO> instance
     * @return a List<AddressEntity> instance
     */
    @Override
    public List<AddressDTO> addListAddress(
            final List<AddressDTO> pListAddress) {
        List<AddressEntity> createdList;
        createdList = addressMapping.convertToAddressEntity(pListAddress);
        createdList = (List<AddressEntity>) repository.saveAll(createdList);
        return addressMapping.convertToAddressDTO(createdList);
    }

    /**
     * The find all method is used to get a list of all addresses (with each
     * corresponding covering station) saved in the DataBase. The method is
     * called by a GET request on /firestation.
     *
     * @return a List<AddressEntity> instance
     */
    @Override
    public List<AddressDTO> findAll() {
        List<AddressEntity> addressEntityList = (List<AddressEntity>) repository
                .findAll();
        List<AddressDTO> addressDTOList = addressMapping
                .convertToAddressDTO(addressEntityList);
        return addressDTOList;
    }

    /**
     * The addAddressFireStation method is used to add one new address with its
     * corresponding covering station in database. The method is called by the
     * POST request on /firestation.
     *
     * @param pAddressDTO an AddressEntity instance
     * @return an AddressEntity instance
     */
    @Override
    public AddressDTO addAddress(final AddressDTO pAddressDTO) {
        AddressEntity foundAddress = repository
                .findByAddress(pAddressDTO.getAddress());
        if (foundAddress == null) {
            AddressEntity addedAddress = addressMapping
                    .convertToAddressEntity(pAddressDTO);
            addedAddress = repository.save(addedAddress);
            return addressMapping.convertToAddressDTO(addedAddress);
        } else {
            return null;
        }
    }

    /**
     * The findByAddress method is used to find an address with its
     * corresponding covering station in database. The method is called by the
     * GET request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressEntity instance
     * @throws AddressFireStationNotFoundException
     */
    @Override
    public AddressDTO findByAddress(final String pAddress)
            throws AddressNotFoundException {
        AddressEntity addressEntity = repository.findByAddress(pAddress);
        if (addressEntity == null) {
            throw new AddressNotFoundException();
        }
        return addressMapping.convertToAddressDTO(addressEntity);
    }

    /**
     * The updateAddress method is used to update an address or its
     * corresponding covering station in database. The method is called by the
     * PUT request on /firestation.
     *
     * @param pAddressDTO an AddressDTO instance
     * @return an AddressEntity instance
     * @throws AddressNotFoundException
     */
    @Override
    public AddressDTO updateAddress(final String pAdressToUpdate,
            final AddressDTO pAddressDTO) throws AddressNotFoundException {
        AddressEntity foundAddressEntity = repository
                .findByAddress(pAdressToUpdate);
        if (foundAddressEntity == null) {
            throw new AddressNotFoundException();
        } else if (foundAddressEntity.getAddress()
                .contentEquals(pAddressDTO.getAddress())) {
            AddressEntity updateAddressEntity = foundAddressEntity;
            updateAddressEntity.setStation(pAddressDTO.getStation());
            updateAddressEntity = repository.save(updateAddressEntity);
            return addressMapping.convertToAddressDTO(updateAddressEntity);
        }
        return null;
    }

    /**
     * The deleteAnAddress method is used to remove an address (and its
     * corresponding covering station of database. The method is called by the
     * DELETE request on /firestation/{address}.
     *
     * @param pAddressToDelete a String
     * @return an AddressEntity instance
     */
    @Override
    public AddressDTO deleteAnAddress(final String pAddressToDelete) {
        AddressEntity foundAddress = repository.findByAddress(pAddressToDelete);
        if (foundAddress != null) {
            repository.deleteById(foundAddress.getId());
            return addressMapping.convertToAddressDTO(foundAddress);
        }
        return null;
    }

}
