package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.controller.AddressNotFoundException;

public interface IAddressService {

    /**
     * The addListFireStations method is used to add a list of addresses (with
     * each corresponding covering station) in the DataBase. The method is
     * called by the POST request on /firestations.
     *
     * @param pListAddress - a List<AddressDTO> instance
     * @return a List<AddressDTO> instance
     */
    List<AddressDTO> addListAddress(
            List<AddressDTO> pListAddress);

    /**
     * The find all method is used to get a list of all addresses (with each
     * corresponding covering station) saved in the DataBase. The method is
     * called by a GET request on /firestation.
     *
     * @return a List<AddressDTO> instance
     */
    List<AddressDTO> findAll();

    /**
     * The addAddressFireStation method is used to add one new address with its
     * corresponding covering station in database. The method is called by the
     * POST request on /firestation.
     *
     * @param pAddressDTO an AddressDTO instance
     * @return an AddressDTO instance
     */
    AddressDTO addAddress(
            AddressDTO pAddressDTO);

    /**
     * The findByAddress method is used to find an address with its
     * corresponding covering station in database. The method is called by the
     * GET request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressDTO instance
     * @throws AddressNotFoundException
     */
    AddressDTO findByAddress(String pAddress)
            throws AddressNotFoundException;

    /**
     * The updateAddress method is used to update an address or its
     * corresponding covering station in database. The method is called by the
     * PUT request on /firestation.
     *
     * @param pAddressDTO an AddressDTO instance
     * @return an AddressDTO instance
     */
    AddressDTO updateAddress(AddressDTO pAddressDTO);

    /**
     * The deleteAnAddress method is used to remove an address (and its
     * corresponding covering station of database. The method is called by the
     * DELETE request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressDTO instance
     */
    AddressDTO deleteAnAddress(String pAddress);

}
