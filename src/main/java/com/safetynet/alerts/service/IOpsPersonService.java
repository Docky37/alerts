package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.ChildAlert;
import com.safetynet.alerts.model.CoveredPopulation;

public interface IOpsPersonService {

    /**
     * OPS#1 - Get the count of children and adults and the list of all persons
     * covered by the given station.
     *
     * @param pStation
     * @return a CoveredPopulation object
     */
    CoveredPopulation populationCoveredByStation(String pStation);

    // OPS #2 ENDPOINT -------------------------------------------------------
    /**
     * OPS#2 - ChildAlert: the list of children (with age) and adults living in
     * a given address.
     *
     * @param address
     * @return a ChildAlert object
     */
    ChildAlert findListOfChildByAddress(String address);

    // OPS #3 ENDPOINT -------------------------------------------------------
    /**
     * OPS#3 - Get the list of phone numbers of all inhabitants covered by one
     * station.
     *
     * @param pStation - the fire station that we want phone numbers of all
     *                 covered inhabitants
     * @return a List<String> of phone numbers.
     */
    List<String> findAllPhoneListByStation(String pStation);

    // OPS #7 ENDPOINT -------------------------------------------------------
    /**
     * OPS#7 - Get the list of e-mail of all inhabitants of the city.
     *
     * @param pCity - the city that we want inhabitants e-mail addresses.
     * @return a List<String> - the list of phone number of all inhabitants
     *         covered by the station
     */
    List<String> findAllMailByCity(String pCity);

}