package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.AlertsApplication;
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

    /**
     * GET request to list the e-mail address off all city inhabitants.
     *
     * @param city - the city that we want e-mail address off all inhabitants
     * @return a List<String> - list of e-mail of all persons of the city
     */
    @GetMapping(value = "communityEmail/{city}")
    public List<String> getEmailListByCity(@PathVariable final String city) {
        return opsPersonService.findAllMailByCity(city);
    }

    /**
     * GET request to list the phone number off all inhabitants covered by one
     * station.
     *
     * @param station - the number of the fire station
     * @return a List of persons in DB covered by this station
     */
    @GetMapping(value = "phoneAlert/{station}")
    public List<String> getPhoneListByStation(
            @PathVariable final String station) {
        return opsPersonService.findAllPhoneListByStation(station);
    }

}
