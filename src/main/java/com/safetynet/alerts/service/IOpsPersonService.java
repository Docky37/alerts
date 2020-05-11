package com.safetynet.alerts.service;

import java.util.List;
import java.util.Set;

import com.safetynet.alerts.DTO.ChildDTO;
import com.safetynet.alerts.DTO.CoveredPopulationDTO;

public interface IOpsPersonService {

    /**
     * OPS#1 - Get the count of children and adults and the list of all persons
     * covered by the given station.
     *
     * @param pStation
     * @return a CoveredPopulationDTO object
     */
    CoveredPopulationDTO populationCoveredByStation(String pStation);

    // OPS #2 ENDPOINT -------------------------------------------------------
    /**
     * OPS#2 - ChildDTO: the list of children (with age) and adults living in
     * a given address.
     *
     * @param address
     * @return a ChildDTO object
     */
    ChildDTO findListOfChildByAddress(String address);

    // OPS #3 ENDPOINT -------------------------------------------------------
    /**
     * OPS#3 - Get the list of phone numbers of all inhabitants covered by one
     * station.
     *
     * @param pStation - the fire station that we want phone numbers of all
     *                 covered inhabitants
     * @return a Set<String> of phone numbers.
     */
    Set<String> findAllPhoneListByStation(String pStation);

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
