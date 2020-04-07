package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.controller.AddressFireStationNotFoundException;

public class AddressFireStationService {

    public List<AddressFireStation> addListFireStations(
            final List<AddressFireStation> pListFireStation) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<AddressFireStation> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    public AddressFireStation addAddressFireStation(
            final AddressFireStation pAddressFireStation) {
        // TODO Auto-generated method stub
        return null;
    }

    public AddressFireStation findByAddress(final String pAddress)
            throws AddressFireStationNotFoundException {
        // TODO Auto-generated method stub
        if (pAddress == null) {
            throw new AddressFireStationNotFoundException();
        }

        return null;
    }

    public AddressFireStation updateAddress(
            final AddressFireStation pAddressFireStation) {
        // TODO Auto-generated method stub
        return null;
    }

    public AddressFireStation deleteAddress(final String pAddressFireStation) {
        // TODO Auto-generated method stub
        return null;
    }

    public AddressFireStation deleteAnAddress(final String pAddress) {
        // TODO Auto-generated method stub
        return null;
    }

}
