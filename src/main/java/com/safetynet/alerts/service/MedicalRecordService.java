package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.MedicalRecordRepository;
import com.safetynet.alerts.repositery.PersonRepository;

/**
 * MedicalRecordService is the class in charge of the MedicalRecord business
 * work.
 *
 * @author Thierry Schreiner
 */
@Service
public class MedicalRecordService {

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
     * PersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Class constructor - Set medicalRecordRepository and personMapping(IoC).
     *
     * @param pMedRecRepos
     * @param pPersonRepository
     */
    public MedicalRecordService(final MedicalRecordRepository pMedRecRepos,
            final PersonRepository pPersonRepository) {
        medicalRecordRepository = pMedRecRepos;
        personRepository = pPersonRepository;
    }

    /**
     * The addListMedicalRecord method allows user to save a list of medical
     * records in DB.
     *
     * @param pListMedicalRecord
     * @return a List<MedicalRecord>
     */
    public List<MedicalRecord> addListMedicalRecord(
            final List<MedicalRecord> pListMedicalRecord) {
        List<MedicalRecord> createdList = new ArrayList<MedicalRecord>();
        for (MedicalRecord medicalRecord : pListMedicalRecord) {
            MedicalRecord createdMedRec = addMedicalRecord(medicalRecord);
            createdList.add(createdMedRec);
        }
        // createdList = (List<MedicalRecord>) medicalRecordRepository
        // .saveAll(pListMedicalRecord);
        return createdList;
    }

    /**
     * The find all method allows user to get a list of all medical records
     * saved in DB.
     *
     * @return a List<MedicalRecord>
     */
    public List<MedicalRecord> findAll() {
        List<MedicalRecord> medicalRecordList;
        medicalRecordList = (List<MedicalRecord>) medicalRecordRepository
                .findAll();
        return medicalRecordList;
    }

    /**
     * The addMedicalRecord method allows user to add one medical record in DB,
     * only if the person identified by lastName & firstName in the
     * MedicalRecord exists in database persons table.
     *
     * @param pMedicalRecord
     * @return a MedicalRecord
     */
    public MedicalRecord addMedicalRecord(final MedicalRecord pMedicalRecord) {
        MedicalRecord foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        if (foundMedicalRecord == null) {
            PersonEntity personToJoin = personRepository
                    .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                            pMedicalRecord.getFirstName());
            if (personToJoin == null) {
                // TO DO message : Cannot save this medical record because its
                // owner is unknown in Alerts DB.
                return null;
            } else {
                MedicalRecord addedMedicalRecord = medicalRecordRepository
                        .save(pMedicalRecord);
                personToJoin.setMedRecId(addedMedicalRecord);
                personRepository.save(personToJoin);
                return addedMedicalRecord;
            }
        }
        return null;
    }

    /**
     * The findByLastNameAndFirstName method allows user to find a medical
     * record in DB.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecord
     * @throws MedicalRecordNotFoundException
     */
    public MedicalRecord findByLastNameAndFirstName(final String lastName,
            final String firstName) throws MedicalRecordNotFoundException {
        MedicalRecord foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (foundMedicalRecord == null) {
            throw new MedicalRecordNotFoundException();
        }
        return foundMedicalRecord;
    }

    /**
     * The updateMedicalRecord method first uses the
     * MedicalRecordRepository.findByLastNameAndFirstName method to verify if
     * the person already exists in DB. If record exists, it calls the save
     * method of CrudRepository to update it.
     *
     * @param pMedicalRecord
     * @return a MedicalRecord
     */
    public MedicalRecord updateMedicalRecord(
            final MedicalRecord pMedicalRecord) {
        MedicalRecord medicalRecordToUpdate = pMedicalRecord;
        MedicalRecord foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        if (foundMedicalRecord != null && foundMedicalRecord.getFirstName() == pMedicalRecord.getFirstName() 
                && foundMedicalRecord.getLastName() == pMedicalRecord.getLastName()) {
            medicalRecordToUpdate.setId(foundMedicalRecord.getId());
            MedicalRecord updatedMedicalRecord = medicalRecordRepository
                    .save(medicalRecordToUpdate);
            return updatedMedicalRecord;
        }
        return null;
    }

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * MedicalRecord to delete in DB and get its id to invokes the deleteById
     * method of CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecord
     */
    public MedicalRecord deleteAMedicalRecord(final String lastName,
            final String firstName) {
        MedicalRecord medicalRecordToDelete = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (medicalRecordToDelete != null) {
            medicalRecordRepository.deleteById(medicalRecordToDelete.getId());
        }
        return medicalRecordToDelete;

    }

}
