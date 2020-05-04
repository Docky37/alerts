package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @PostMapping(value = "medicalRecords")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final List<MedicalRecordDTO> pListMedicalRecord) {

        List<MedicalRecordDTO> listMedRecAdded = medicalRecordService
                .addListMedicalRecord(pListMedicalRecord);

        if (listMedRecAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("medicalRecords/")
                .buildAndExpand(listMedRecAdded.get(1).getFirstName(),
                        listMedRecAdded.get(1).getLastName())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    /**
     * GET request to find all address - fire station associations in DataBase.
     *
     * @return a List of all address - fire station associations in DB
     */
    @GetMapping(value = "medicalRecord")
    public List<MedicalRecordDTO> findAll() {
        return medicalRecordService.findAll();
    }

    /**
     * POST request to add a new MedicalRecordDTO in DB.
     *
     * @param pMedicalRecord - The association to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "medicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final MedicalRecordDTO pMedicalRecord) {

        MedicalRecordDTO medicalRecordAdded = medicalRecordService
                .addMedicalRecord(pMedicalRecord);

        if (medicalRecordAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("medicalRecord/{address}")
                .buildAndExpand(medicalRecordAdded.getFirstName(),
                        medicalRecordAdded.getLastName())
                .toUri();

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
        return medicalRecordService.findByLastNameAndFirstName(lastName,
                firstName);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void addressFireStationNotFoundHandler(
            final MedicalRecordNotFoundException e) {

    }

    /**
     * PUT request to update an address - fire station association from
     * DataBase.
     *
     * @param pMedicalRecord
     * @return ResponseEntity<Void>
     * @throws Throwable 
     */
    @PutMapping(value = "medicalRecord/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(
            @RequestBody final MedicalRecordDTO pMedicalRecord) throws Throwable {

        MedicalRecordDTO medicalRecordUpdated = medicalRecordService
                .updateMedicalRecord(pMedicalRecord);

        if (medicalRecordUpdated == null) {
            return ResponseEntity.status(CODE_501).build();
        }
        return ResponseEntity.noContent().build();


    }

    /**
     * DELETE request to remove an address - fire station association from
     * DataBase.
     *
     * @param lastName
     * @param firstName
     * @return ResponseEntity<Void>
     */
    @DeleteMapping(value = "medicalRecord/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteMedicalRecord(
            @PathVariable final String lastName,
            @PathVariable final String firstName) {
        MedicalRecordDTO medicalRecordToDelete = null;
        medicalRecordToDelete = medicalRecordService
                .deleteAMedicalRecord(lastName, firstName);
        if (medicalRecordToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
