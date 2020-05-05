package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecordEntity;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.MedicalRecordRepository;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.MedicalRecordMapping;

/**
 * MedicalRecordService is the class in charge of the MedicalRecord business
 * work.
 *
 * @author Thierry Schreiner
 */
@Service
public class MedicalRecordService implements IMedicalRecordService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * MedicalRecordRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * AddressMapping is an utility that provides bidirectional conversion
     * between AddressDTO and AddressEntity.
     */
    @Autowired
    private MedicalRecordMapping medicalRecordMapping;

    /**
     * PersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Class constructor - Set medicalRecordRepository and personMapping(IoC).
     *
     * @param pMedRecRepos
     * @param pPersonRepository
     * @param pMedicalRecordMapping
     */
    public MedicalRecordService(final MedicalRecordRepository pMedRecRepos,
            final PersonRepository pPersonRepository,
            final MedicalRecordMapping pMedicalRecordMapping) {
        medicalRecordRepository = pMedRecRepos;
        personRepository = pPersonRepository;
        medicalRecordMapping = pMedicalRecordMapping;
    }

    /**
     * The find all method allows user to get a list of all medical records
     * saved in DB.
     *
     * @return a List<MedicalRecordDTO>
     */
    @Override
    public List<MedicalRecordDTO> findAll() {
        List<MedicalRecordEntity> medicalRecordList =
                (List<MedicalRecordEntity>) medicalRecordRepository
                .findAll();
        List<MedicalRecordDTO> foundMedicalRecordList = medicalRecordMapping
                .convertToMedicalRecordDTO(medicalRecordList);
        return foundMedicalRecordList;
    }

    /**
     * The addListMedicalRecord method allows user to save a list of medical
     * records in DB.
     *
     * @param pMedicalRecordList
     * @return a List<MedicalRecordDTO>
     */
    @Override
    public List<MedicalRecordDTO> addListMedicalRecord(
            final List<MedicalRecordDTO> pMedicalRecordList) {
        List<MedicalRecordDTO> createdList = new ArrayList<MedicalRecordDTO>();
        MedicalRecordDTO createdMedRecDTO;
        int countOfCreatedMedRec = 0;
        int countOfRejectedMedRec = 0;
        for (MedicalRecordDTO medicalRecordDTO : pMedicalRecordList) {
            createdMedRecDTO = addMedicalRecord(medicalRecordDTO);
            if (createdMedRecDTO == null) { // MedicalRecord not created.
                countOfRejectedMedRec++;
            } else {
                createdList.add(createdMedRecDTO);
                countOfCreatedMedRec++;
            }
        }
        LOGGER.info(
                "Balance sheet:  {} medical records created and {} rejected.",
                countOfCreatedMedRec, countOfRejectedMedRec);

        if (createdList.size() > 0) {
            return createdList;
        }
        return null;
    }

    /**
     * The addMedicalRecord method allows user to add one medical record in DB,
     * only if the person identified by lastName & firstName in the
     * MedicalRecordDTO exists in database persons table.
     *
     * @param pMedicalRecord
     * @return a MedicalRecordDTO
     */
    @Override
    public MedicalRecordDTO addMedicalRecord(
            final MedicalRecordDTO pMedicalRecord) {
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        if (foundMedicalRecord == null) {
            PersonEntity personToJoin = personRepository
                    .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                            pMedicalRecord.getFirstName());
            if (personToJoin == null) {
                LOGGER.info(
                        "Cannot create a orphan medical Record,"
                                + "its owner ({} {}) is not registred !",
                        pMedicalRecord.getFirstName(),
                        pMedicalRecord.getLastName());
                // TO DO message : Cannot save this medical record because its
                // owner is unknown in Alerts DB.
                return null;
            } else {
                MedicalRecordEntity addedMedicalRecord = medicalRecordMapping
                        .convertToMedicalRecordEntity(pMedicalRecord);
                addedMedicalRecord = medicalRecordRepository
                        .save(addedMedicalRecord);
                personToJoin.setMedRecId(addedMedicalRecord);
                personRepository.save(personToJoin);
                LOGGER.info("Medical record created for {} {}.",
                        pMedicalRecord.getFirstName(),
                        pMedicalRecord.getLastName());
                return medicalRecordMapping
                        .convertToMedicalRecordDTO(addedMedicalRecord);
            }
        }
        LOGGER.info(
                "Cannot create a medical Record for {} {}, it already exits!",
                pMedicalRecord.getFirstName(), pMedicalRecord.getLastName());
        return null;
    }

    /**
     * The findByLastNameAndFirstName method allows user to find a medical
     * record in DB.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordDTO
     * @throws MedicalRecordNotFoundException
     */
    @Override
    public MedicalRecordDTO findByLastNameAndFirstName(final String lastName,
            final String firstName) throws MedicalRecordNotFoundException {
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (foundMedicalRecord == null) {
            throw new MedicalRecordNotFoundException();
        }
        return medicalRecordMapping
                .convertToMedicalRecordDTO(foundMedicalRecord);
    }

    /**
     * The updateMedicalRecord method first uses the
     * MedicalRecordRepository.findByLastNameAndFirstName method to verify if
     * the person already exists in DB. If record exists, it calls the save
     * method of CrudRepository to update it.
     *
     * @param pMedicalRecord
     * @return a MedicalRecordDTO
     * @throws MedicalRecordNotFoundException
     */
    @Override
    public MedicalRecordDTO updateMedicalRecord(final String pLastName,
            final String pFirstName, final MedicalRecordDTO pMedicalRecord)
            throws MedicalRecordNotFoundException {
        MedicalRecordDTO medicalRecordToUpdate = pMedicalRecord;
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (foundMedicalRecord == null) {
            throw new MedicalRecordNotFoundException();
        } else if (foundMedicalRecord.getFirstName()
                .contentEquals(pMedicalRecord.getFirstName())
                && foundMedicalRecord.getLastName()
                        .contentEquals(pMedicalRecord.getLastName())) {
            MedicalRecordEntity updatedMedicalRecord = medicalRecordMapping
                    .convertToMedicalRecordEntity(medicalRecordToUpdate);
            updatedMedicalRecord.setId(foundMedicalRecord.getId());
            updatedMedicalRecord = medicalRecordRepository
                    .save(updatedMedicalRecord);
            return medicalRecordMapping
                    .convertToMedicalRecordDTO(updatedMedicalRecord);
        }
        return null;
    }

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * MedicalRecordDTO to delete in DB and get its id to invokes the deleteById
     * method of CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordDTO
     */
    @Override
    public MedicalRecordDTO deleteAMedicalRecord(final String lastName,
            final String firstName) {
        MedicalRecordEntity medicalRecordToDelete = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (medicalRecordToDelete != null) {
            medicalRecordRepository.deleteById(medicalRecordToDelete.getId());
            return medicalRecordMapping
                    .convertToMedicalRecordDTO(medicalRecordToDelete);
        }
        return null;
    }

}
