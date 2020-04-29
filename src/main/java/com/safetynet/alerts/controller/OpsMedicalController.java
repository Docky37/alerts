package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.HouseholdDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.service.IOpsMedicalService;

/**
 * OpsMedicalController class, used to provide operational endpoints with
 * confidential medical data.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class OpsMedicalController {
    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * The service class used to manage person administrative CRUD operations.
     */
    @Autowired
    private IOpsMedicalService opsMedicalService;

    /**
     * Class constructor - Set personService (IoC).
     *
     * @param pOpsMedicalService
     */
    public OpsMedicalController(final IOpsMedicalService pOpsMedicalService) {
        opsMedicalService = pOpsMedicalService;
    }

    // OPS #4 ENDPOINT -------------------------------------------------------
    /**
     * OPS4 - GET request "fire" that lists inhabitant by address. Contains
     * medical confidential data.
     *
     * @param address - the given address
     * @return a FireList object
     */
    @GetMapping(value = "/fire")
    public HouseholdDTO fireByAddress(@RequestParam final String address) {
        return opsMedicalService.fireByAddress(address);
    }

    // OPS #5 ENDPOINT -------------------------------------------------------
    /**
     * OPS5 - GET request flood to list the persons group by address covered by
     * a list of station.
     *
     * @param stationList - a list of the fire station
     * @return a List<FloodDTO> object
     */
    @GetMapping(value = "/flood/stations")
    public List<FloodDTO> listOfPersonsCoveredByStation(
            @RequestParam final List<String> stationList) {
        return opsMedicalService.floodByStation(stationList);
    }

    // OPS #6 ENDPOINT -------------------------------------------------------
    /**
     * OPS6 - GET request "personInfo" that gives . Contains medical
     * confidential data.
     *
     * @param lastName
     * @param firstName
     * @return a List<PersonInfoDTO>
     */
    @GetMapping(value = "/personInfo")
    public List<PersonInfoDTO> personInfo(@RequestParam final String firstName,
            @RequestParam final String lastName) {
        return opsMedicalService.personInfo(firstName, lastName);
    }

}
