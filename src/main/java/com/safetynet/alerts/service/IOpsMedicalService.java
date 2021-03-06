package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.HouseholdDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;

public interface IOpsMedicalService {

    // OPS #4 ENDPOINT -------------------------------------------------------
    /**
     * OPS#4 - ChildDTO: the list of children (with age) and adults living in a
     * given address.
     *
     * @param address
     * @return a ChildDTO object
     */
    HouseholdDTO fireByAddress(String address);

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

    // OPS #6 ENDPOINT -------------------------------------------------------
    /**
     * OPS6 - GET request "personInfo" that gives . Contains medical
     * confidential data.
     *
     * @param lastName
     * @param firstName
     * @return a List<PersonInfoDTO>
     */
    List<PersonInfoDTO> personInfo(String lastName, String firstName);
}
