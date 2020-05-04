package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

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

import com.safetynet.alerts.model.MedicalRecordEntity;
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
     * The service class used to manage address - fire station association CRUD
     * operations.
     */
    private IMedicalRecordService medicalRecordService;

    /**
     * Class constructor - Set addressFireStationService (IoC).
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
            @RequestBody final List<MedicalRecordEntity> pListMedicalRecord) {

        List<MedicalRecordEntity> listMedRecAdded = medicalRecordService
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
    public List<MedicalRecordEntity> findAll() {
        return medicalRecordService.findAll();
    }

    /**
     * POST request to add a new MedicalRecordEntity in DB.
     *
     * @param pMedicalRecord - The association to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "medicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final MedicalRecordEntity pMedicalRecord) {

        MedicalRecordEntity medicalRecordAdded = medicalRecordService
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
     * GET request to find one MedicalRecordEntity.
     *
     * @param lastName
     * @param firstName
     * @return MedicalRecordEntity
     * @throws MedicalRecordNotFoundException
     */
    @GetMapping(value = "medicalRecord/{lastName}/{firstName}")
    public MedicalRecordEntity findMedicalRecordByName(
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
     */
    @PutMapping(value = "medicalRecord/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> update(
            @RequestBody final MedicalRecordEntity pMedicalRecord) {

        MedicalRecordEntity medicalRecordUpdated = medicalRecordService
                .updateMedicalRecord(pMedicalRecord);

        if (medicalRecordUpdated != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("medicalRecord/{lastName}/{firstName}")
                    .buildAndExpand(medicalRecordUpdated.getFirstName(),
                            medicalRecordUpdated.getLastName())
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
        MedicalRecordEntity medicalRecordToDelete = null;
        medicalRecordToDelete = medicalRecordService
                .deleteAMedicalRecord(lastName, firstName);
        if (medicalRecordToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
