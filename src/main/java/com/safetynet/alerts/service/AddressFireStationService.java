package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.controller.AddressFireStationNotFoundException;

/**
 * AddressFireStationService is the class in charge of the business work about
 * adress - fire station association.
 *
 * @author Thierry Schreiner
 */
@Service
public class AddressFireStationService {
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
     * @param pListFireStation - a List<AddressFireStation> instance
     * @return a List<AddressFireStation> instance
     */
    public List<AddressFireStation> addListFireStations(
            final List<AddressFireStation> pListFireStation) {
        List<AddressFireStation> createdList;
        createdList = (List<AddressFireStation>) addressFireStRepository
                .saveAll(pListFireStation);
        return createdList;
    }

    /**
     * The find all method is used to get a list of all addresses (with each
     * corresponding covering station) saved in the DataBase. The method is
     * called by a GET request on /firestation.
     *
     * @return a List<AddressFireStation> instance
     */
    public List<AddressFireStation> findAll() {
        List<AddressFireStation> addressFireStList;
        addressFireStList = (List<AddressFireStation>) addressFireStRepository
                .findAll();
        return addressFireStList;
    }

    /**
     * The addAddressFireStation method is used to add one new address with its
     * corresponding covering station in database. The method is called by the
     * POST request on /firestation.
     *
     * @param pAddressFireStation an AddressFireStation instance
     * @return an AddressFireStation instance
     */
    public AddressFireStation addAddressFireStation(
            final AddressFireStation pAddressFireStation) {
        AddressFireStation foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddressFireStation.getAddress());
        if (foundAddressFireSt == null) {
            AddressFireStation addedAddressFireSt = addressFireStRepository
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
     * @return an AddressFireStation instance
     * @throws AddressFireStationNotFoundException
     */
    public AddressFireStation findByAddress(final String pAddress)
            throws AddressFireStationNotFoundException {
        AddressFireStation foundAddressFireSt = addressFireStRepository
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
     * @param pAddressFireStation an AddressFireStation instance
     * @return an AddressFireStation instance
     */
    public AddressFireStation updateAddress(
            final AddressFireStation pAddressFireStation) {
        AddressFireStation addressFireStToUpdate = pAddressFireStation;
        AddressFireStation foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddressFireStation.getAddress());
        addressFireStToUpdate.setId(foundAddressFireSt.getId());
        AddressFireStation updatedAddressFireSt = addressFireStRepository
                .save(addressFireStToUpdate);
        return updatedAddressFireSt;
    }

    /**
     * The deleteAnAddress method is used to remove an address (and its
     * corresponding covering station of database. The method is called by the
     * DELETE request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressFireStation instance
     */
    public AddressFireStation deleteAnAddress(final String pAddress) {
        AddressFireStation foundAddressFireSt = addressFireStRepository
                .findByAddress(pAddress);
System.out.println(foundAddressFireSt);
        if (foundAddressFireSt != null) {
            addressFireStRepository.deleteById(foundAddressFireSt.getId());
        }
        return foundAddressFireSt;
    }

}
