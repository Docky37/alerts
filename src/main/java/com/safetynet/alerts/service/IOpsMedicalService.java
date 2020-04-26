package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.model.Household;

public interface IOpsMedicalService {

    // OPS #4 ENDPOINT -------------------------------------------------------
    /**
     * OPS#4 - ChildAlert: the list of children (with age) and adults living in
     * a given address.
     *
     * @param address
     * @return a ChildAlert object
     */
    Household fireByAddress(String address);

    // OPS #5 ENDPOINT -------------------------------------------------------
    /**
     * OPS#5 - Get the a list of persons covered by the given station list. This
     * list is ordered by station and address.
     *
     * @param pStationList - the list of fire stations that we want the list of
     *                     all covered inhabitants
     * @return a List<FloodDTO> object.
     */
    List<FloodDTO> floodByStation(List<String> pStationList);

}