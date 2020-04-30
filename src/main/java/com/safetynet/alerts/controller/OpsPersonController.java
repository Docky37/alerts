package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.ChildDTO;
import com.safetynet.alerts.DTO.CoveredPopulationDTO;
import com.safetynet.alerts.service.IOpsPersonService;

/**
 * OpsPersonsController class, used to provide operational endpoints.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class OpsPersonController {
    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * The service class used to manage person administrative CRUD operations.
     */
    @Autowired
    private IOpsPersonService opsPersonService;

    /**
     * Class constructor - Set personService (IoC).
     *
     * @param pOpsPersonService
     */
    public OpsPersonController(final IOpsPersonService pOpsPersonService) {
        opsPersonService = pOpsPersonService;
    }

    // OPS #1 ENDPOINT -------------------------------------------------------
    /**
     * OPS1 - GET request to count the number of children & adults covered by
     * the station.
     *
     * @param stationNumber
     * @return a CoveredPopulationDTO object
     */
    @GetMapping(value = "/firestation/stationNumber")
    public CoveredPopulationDTO adultAndChildCountByStation(
            @RequestParam final String stationNumber) {
        LOGGER.info("NEW HTML REQUEST OPS#1 firestation/stationNumber");
        LOGGER.info(
                "OpsPersonController OPS#1 >>> RequestParam: stationNumber= {}",
                stationNumber);
        return opsPersonService.populationCoveredByStation(stationNumber);
    }

    // OPS #2 ENDPOINT -------------------------------------------------------
    /**
     * OPS2 - GET request to list the child.
     *
     * @param address - the given address
     * @return a ChildDTO object
     */
    @GetMapping(value = "/childAlert")
    public ChildDTO childAlertByAddress(@RequestParam final String address) {
        LOGGER.info("NEW HTML REQUEST OPS#2 chilAlert");
        LOGGER.info("OpsPersonController OPS#2 >>> RequestParam: address= {}",
                address);
        return opsPersonService.findListOfChildByAddress(address);
    }

    // OPS #3 ENDPOINT -------------------------------------------------------
    /**
     * OPS3 - GET request to list the phone number off all inhabitants covered
     * by one station.
     *
     * @param station - the number of the fire station
     * @return a List of persons in DB covered by this station
     */
    @GetMapping(value = "/phoneAlert")
    public List<String> getPhoneListByStation(
            @RequestParam final String station) {
        LOGGER.info("NEW HTML REQUEST OPS#3 phoneAlert");
        LOGGER.info("OpsPersonController OPS#3 >>> RequestParam: station= {}",
                station);
        return opsPersonService.findAllPhoneListByStation(station);
    }

    // OPS #7 ENDPOINT -------------------------------------------------------
    /**
     * OPS7 - GET request to list the e-mail address off all city inhabitants.
     *
     * @param city - the city that we want e-mail address off all inhabitants
     * @return a List<String> - list of e-mail of all persons of the city
     */
    @GetMapping(value = "/communityEmail")
    public List<String> getEmailListByCity(@RequestParam final String city) {
        LOGGER.info("NEW HTML REQUEST OPS#7 communityEmail");
        LOGGER.info("OpsPersonController OPS#7 >>> RequestParam: city = {}",
                city);
        return opsPersonService.findAllMailByCity(city);
    }

}
