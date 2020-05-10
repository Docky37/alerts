package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.MedicalRecordDTO;
import com.safetynet.alerts.service.IMedicalRecordService;

/**
 * MedicalRecordController class, is used to create administrative endpoints for
 * CRUD operations on MedicalRecords.
 *
 * @author Thierry Schreiner
 */
@RestController
public class MedicalRecordController {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * The Status code 501 Not implemented.
     */
    static final int CODE_501 = 501;

    /**
     * The service class used to manage MedicalRecords CRUD operations.
     */
    private IMedicalRecordService medicalRecordService;

    /**
     * Class constructor - Set MedicalRecordService (IoC).
     *
     * @param pMedicalRecordService
     */
    public MedicalRecordController(
            final IMedicalRecordService pMedicalRecordService) {
        medicalRecordService = pMedicalRecordService;
    }

    /**
     * POST request to create MedicalRecords in DataBase from a list of
     * MedicalRecords.
     *
     * @param pListMedicalRecord
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "medicalRecord/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(
            @RequestBody final List<MedicalRecordDTO> pListMedicalRecord) {
        LOGGER.info("NEW HTML ADMINISTRATIVE POST REQUEST"
                + " on http://localhost:8080/medicalRecord/batch");
        LOGGER.info(
                " MedicalRecordController  >>> Import a MedicalRecord list");
        LOGGER.info(" => List = {}", pListMedicalRecord.toString());

        List<MedicalRecordDTO> listMedRecAdded = medicalRecordService
                .addListMedicalRecord(pListMedicalRecord);

        if (listMedRecAdded.isEmpty()) {
            LOGGER.info("END of HTML administrative POST Request"
                    + " with Status 200 OK");
            return new ResponseEntity<Object>(
                    medicalRecordService.getBalanceSheet(), new HttpHeaders(),
                    HttpStatus.OK);
        }

        LOGGER.info("END of HTML administrative POST Request"
                + " with Status 201 Created");
        return new ResponseEntity<Object>(
                medicalRecordService.getBalanceSheet(), new HttpHeaders(),
                HttpStatus.CREATED);

    }

    /**
     * GET request to find all MedicalRecords in DataBase.
     *
     * @return a List<MedicalRecordDTO>
     */
    @GetMapping(value = "medicalRecord")
    public List<MedicalRecordDTO> findAll() {
        LOGGER.info("NEW HTML ADMINISTRATIVE GET REQUEST"
                + " on http://localhost:8080/medicalRecord");
        LOGGER.info(" MedicalRecordController  >>> Get all medicalRecords.");
        List<MedicalRecordDTO> resultList = medicalRecordService.findAll();
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 200 OK ---");
        return resultList;
    }

    /**
     * POST request to add a new MedicalRecordDTO in DB.
     *
     * @param pMedicalRecord - The association to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "medicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(
            @RequestBody final MedicalRecordDTO pMedicalRecord) {
        LOGGER.info("NEW HTML ADMINISTRATIVE POST REQUEST"
                + " on http://localhost:8080/medicalRecord");
        LOGGER.info(" MedicalRecordController  >>> Add the medical record '{}'",
                pMedicalRecord);

        MedicalRecordDTO medicalRecordAdded = medicalRecordService
                .addMedicalRecord(pMedicalRecord);

        if (medicalRecordAdded == null) {
            LOGGER.info("END of HTML administrative POST Request"
                    + " with Status 400 Bad Request");
            return new ResponseEntity<Object>(
                    "Cannot create this medical record,"
                            + " there is already one registred for '"
                            + pMedicalRecord.getFirstName() + " "
                            + pMedicalRecord.getLastName()
                            + "'. Consider PUT request"
                            + " if you want to update it.",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else if (medicalRecordAdded.getLastName().isEmpty()) {
            LOGGER.info("END of HTML administrative POST Request"
                    + " with Status 400 Bad Request");
            return new ResponseEntity<Object>(
                    "Cannot create a orphan medical Record, its owner ("
                            + pMedicalRecord.getFirstName() + " "
                            + pMedicalRecord.getLastName()
                            + ") is not registred!",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{lastName}/{firstName}")
                .buildAndExpand(medicalRecordAdded.getLastName(),
                        medicalRecordAdded.getFirstName())
                .toUri();
        LOGGER.info("END of HTML administrative POST Request"
                + " with Status 201 Created");
        return ResponseEntity.created(location).build();
    }

    /**
     * GET request to find one MedicalRecordDTO.
     *
     * @param lastName
     * @param firstName
     * @return MedicalRecordDTO
     * @throws MedicalRecordNotFoundException
     */
    @GetMapping(value = "medicalRecord/{lastName}/{firstName}")
    public MedicalRecordDTO findMedicalRecordByName(
            @PathVariable final String lastName,
            @PathVariable final String firstName)
            throws MedicalRecordNotFoundException {
        LOGGER.info("NEW HTML ADMINISTRATIVE GET REQUEST"
                + " on http://localhost:8080/medicalRecord");
        LOGGER.info(" PersonController  >>> Get the MedicalRecord of '{} {}'.",
                firstName, lastName);
        MedicalRecordDTO resultMedRec = medicalRecordService
                .findByLastNameAndFirstName(lastName, firstName);
        LOGGER.info(
                "END of HTML administrative GET Request with Status 200 OK");
        return resultMedRec;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void addressFireStationNotFoundHandler(
            final MedicalRecordNotFoundException e) {
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 404 NOT FOUND");
    }

    /**
     * PUT request to update a MedicalRecord from DataBase.
     *
     * @param lastName
     * @param firstName
     * @param pMedicalRecord
     * @return ResponseEntity<Void>
     * @throws Throwable
     */
    @PutMapping(value = "medicalRecord/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> update(@PathVariable final String lastName,
            @PathVariable final String firstName,
            @RequestBody final MedicalRecordDTO pMedicalRecord)
            throws Throwable {
        LOGGER.info("NEW HTML ADMINISTRATIVE PUT REQUEST"
                + " on http://localhost:8080/medicalRecord");
        LOGGER.info("MedicalRecordController >>> Update the person '{} {}',",
                firstName, lastName);
        LOGGER.info(" with content: '{}'.", pMedicalRecord.toString());

        MedicalRecordDTO medicalRecordUpdated = medicalRecordService
                .updateMedicalRecord(lastName, firstName, pMedicalRecord);

        if (medicalRecordUpdated == null) {
            LOGGER.info("END of HTML administrative PUT Request"
                    + " with Status 501 Not Implemented");
            return new ResponseEntity<Object>(
                    "It is not allowed to change the medical record owner!",
                    new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED);
        }
        LOGGER.info("END of HTML administrative PUT Request"
                + " with Status 204 No Content");
        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE request to remove an address - fire station association from
     * DataBase.
     *
     * @param lastName
     * @param firstName
     * @return ResponseEntity<Void>
     * @throws PersonNotFoundException
     * @throws MedicalRecordNotFoundException
     */
    @DeleteMapping(value = "medicalRecord/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteMedicalRecord(
            @PathVariable final String lastName,
            @PathVariable final String firstName)
            throws MedicalRecordNotFoundException {
        LOGGER.info("NEW HTML ADMINISTRATIVE DELETE REQUEST"
                + " on http://localhost:8080/medicalRecord");
        LOGGER.info("MedicalRecordController >>>"
                + " Delete the medicalRecord of '{} {}',",
                firstName, lastName);
        MedicalRecordDTO medicalRecordToDelete = null;
        medicalRecordToDelete = medicalRecordService
                .deleteAMedicalRecord(lastName, firstName);
        if (medicalRecordToDelete != null) {
            LOGGER.info("END of HTML administrative DELETE Request"
                    + " with Status 200 OK");
            return ResponseEntity.ok().build();
        }
        LOGGER.error(" |   PERSON NOT FOUND!");
        LOGGER.debug(" | End of PersonService 'Delete a person.' ---");
        throw new MedicalRecordNotFoundException();
    }

}
