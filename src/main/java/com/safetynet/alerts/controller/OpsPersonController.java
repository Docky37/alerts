package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.ChildAlert;
import com.safetynet.alerts.model.CountOfPersons;
import com.safetynet.alerts.model.CoveredPerson;
import com.safetynet.alerts.service.OpsPersonService;

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
    private OpsPersonService opsPersonService;

    /**
     * Class constructor - Set personService (IoC).
     *
     * @param pOpsPersonService
     */
    public OpsPersonController(final OpsPersonService pOpsPersonService) {
        opsPersonService = pOpsPersonService;
    }

    // OPS #1 ENDPOINT -------------------------------------------------------
    /**
     * OPS1 - GET request to list the persons covered by one station.
     *
     * @param station - the number of the fire station
     * @return a List of persons in DB covered by this station
     */
    @GetMapping(value = "firestation/stationNumber/{station}")
    public List<CoveredPerson> listOfPersonsCoveredByStation(
            @PathVariable final String station) {
        return opsPersonService.findListOfPersonsCoveredByStation(station);
    }

    /**
     * OPS1 - GET request to count the number of children & adults covered by
     * the station.
     *
     * @param station
     * @return a CountOfPersons object
     */
    @GetMapping(value = "firestation/count/{station}")
    public CountOfPersons adultAndChildCountByStation(
            @PathVariable final String station) {
        return opsPersonService.countPersonsCoveredByStation(station);
    }

    // OPS #2 ENDPOINT -------------------------------------------------------
    /**
     * OPS2 - GET request to list the child.
     *
     * @param address - the given address
     * @return a ChildAlert object
     */
    @GetMapping(value = "childAlert/{address}")
    public ChildAlert childAlertByAddress(
            @PathVariable final String address) {
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
    @GetMapping(value = "phoneAlert/{station}")
    public List<String> getPhoneListByStation(
            @PathVariable final String station) {
        return opsPersonService.findAllPhoneListByStation(station);
    }

    // OPS #7 ENDPOINT -------------------------------------------------------
    /**
     * OPS7 - GET request to list the e-mail address off all city inhabitants.
     *
     * @param city - the city that we want e-mail address off all inhabitants
     * @return a List<String> - list of e-mail of all persons of the city
     */
    @GetMapping(value = "communityEmail/{city}")
    public List<String> getEmailListByCity(@PathVariable final String city) {
        return opsPersonService.findAllMailByCity(city);
    }

}
