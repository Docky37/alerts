package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.controller.AddressFireStationNotFoundException;
import com.safetynet.alerts.model.AddressEntity;

public interface IAddressFireStationService {

    /**
     * The addListFireStations method is used to add a list of addresses (with
     * each corresponding covering station) in the DataBase. The method is
     * called by the POST request on /firestations.
     *
     * @param pListFireStation - a List<AddressEntity> instance
     * @return a List<AddressEntity> instance
     */
    List<AddressEntity> addListFireStations(
            List<AddressEntity> pListFireStation);

    /**
     * The find all method is used to get a list of all addresses (with each
     * corresponding covering station) saved in the DataBase. The method is
     * called by a GET request on /firestation.
     *
     * @return a List<AddressEntity> instance
     */
    List<AddressEntity> findAll();

    /**
     * The addAddressFireStation method is used to add one new address with its
     * corresponding covering station in database. The method is called by the
     * POST request on /firestation.
     *
     * @param pAddressFireStation an AddressEntity instance
     * @return an AddressEntity instance
     */
    AddressEntity addAddressFireStation(
            AddressEntity pAddressFireStation);

    /**
     * The findByAddress method is used to find an address with its
     * corresponding covering station in database. The method is called by the
     * GET request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressEntity instance
     * @throws AddressFireStationNotFoundException
     */
    AddressEntity findByAddress(String pAddress)
            throws AddressFireStationNotFoundException;

    /**
     * The updateAddress method is used to update an address or its
     * corresponding covering station in database. The method is called by the
     * PUT request on /firestation.
     *
     * @param pAddressFireStation an AddressEntity instance
     * @return an AddressEntity instance
     */
    AddressEntity updateAddress(AddressEntity pAddressFireStation);

    /**
     * The deleteAnAddress method is used to remove an address (and its
     * corresponding covering station of database. The method is called by the
     * DELETE request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressEntity instance
     */
    AddressEntity deleteAnAddress(String pAddress);

}
