package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.controller.AddressFireStationNotFoundException;

/**
 * AddressFireStationService is the class in charge of the business work about
 * adress - fire station association.
 *
 * @author Thierry Schreiner
 */
@Service
public class AddressFireStationService implements IAddressFireStationService {
    /**
     * PersonRepository is an Interface that extends CrudRepository.
     */
    private AddressFireStationRepository addressFireStRepository;

    /**
     * Class constructor - Set addressFireStRepository (IoC).
     *
     * @param pAddressFireStRepositery
     */
    public AddressFireStationService(
            final AddressFireStationRepository pAddressFireStRepositery) {
        addressFireStRepository = pAddressFireStRepositery;
    }

    /**
     * The addListFireStations method is used to add a list of addresses (with
     * each corresponding covering station) in the DataBase. The method is
     * called by the POST request on /firestations.
     *
     * @param pListFireStation - a List<AddressEntity> instance
     * @return a List<AddressEntity> instance
     */
    @Override
    public List<AddressEntity> addListFireStations(
            final List<AddressEntity> pListFireStation) {
        List<AddressEntity> createdList;
        createdList = (List<AddressEntity>) addressFireStRepository
                .saveAll(pListFireStation);
        return createdList;
    }

    /**
     * The find all method is used to get a list of all addresses (with each
     * corresponding covering station) saved in the DataBase. The method is
     * called by a GET request on /firestation.
     *
     * @return a List<AddressEntity> instance
     */
    @Override
    public List<AddressEntity> findAll() {
        List<AddressEntity> addressFireStList;
        addressFireStList = (List<AddressEntity>) addressFireStRepository
                .findAll();
        return addressFireStList;
    }

    /**
     * The addAddressFireStation method is used to add one new address with its
     * corresponding covering station in database. The method is called by the
     * POST request on /firestation.
     *
     * @param pAddressFireStation an AddressEntity instance
     * @return an AddressEntity instance
     */
    @Override
    public AddressEntity addAddressFireStation(
            final AddressEntity pAddressFireStation) {
        AddressEntity foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddressFireStation.getAddress());
        if (foundAddressFireSt == null) {
            AddressEntity addedAddressFireSt = addressFireStRepository
                    .save(pAddressFireStation);
            return addedAddressFireSt;
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
    public AddressEntity findByAddress(final String pAddress)
            throws AddressFireStationNotFoundException {
        AddressEntity foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddress);
        if (foundAddressFireSt == null) {
            throw new AddressFireStationNotFoundException();
        }
        return foundAddressFireSt;
    }

    /**
     * The updateAddress method is used to update an address or its
     * corresponding covering station in database. The method is called by the
     * PUT request on /firestation.
     *
     * @param pAddressFireStation an AddressEntity instance
     * @return an AddressEntity instance
     */
    @Override
    public AddressEntity updateAddress(
            final AddressEntity pAddressFireStation) {
        AddressEntity addressFireStToUpdate = pAddressFireStation;
        AddressEntity foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddressFireStation.getAddress());
        addressFireStToUpdate.setId(foundAddressFireSt.getId());
        AddressEntity updatedAddressFireSt = addressFireStRepository
                .save(addressFireStToUpdate);
        return updatedAddressFireSt;
    }

    /**
     * The deleteAnAddress method is used to remove an address (and its
     * corresponding covering station of database. The method is called by the
     * DELETE request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressEntity instance
     */
    @Override
    public AddressEntity deleteAnAddress(final String pAddress) {
        AddressEntity foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddress);
        if (foundAddressFireSt != null) {
            addressFireStRepository.deleteById(foundAddressFireSt.getId());
        }
        return foundAddressFireSt;
    }

}
