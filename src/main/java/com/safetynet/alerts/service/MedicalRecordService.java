package com.safetynet.alerts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repositery.MedicalRecordRepository;

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
     * PersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Class constructor - Set medicalRecordRepository and personMapping(IoC).
     *
     * @param pPersonRepos
     */
    public MedicalRecordService(final MedicalRecordRepository pMedRecRepos) {
        medicalRecordRepository = pMedRecRepos;
    }

    public List<MedicalRecord> addListMedicalRecord(
            final List<MedicalRecord> pListMedicalRecord) {
        List<MedicalRecord> createdList;
        createdList = (List<MedicalRecord>) medicalRecordRepository
                .saveAll(pListMedicalRecord);
        return createdList;
    }

    public List<MedicalRecord> findAll() {
        List<MedicalRecord> medicalRecordList;
        medicalRecordList = (List<MedicalRecord>) medicalRecordRepository
                .findAll();
        return medicalRecordList;
    }

    public MedicalRecord addMedicalRecord(final MedicalRecord pMedicalRecord) {
        MedicalRecord foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        if (foundMedicalRecord == null) {
            MedicalRecord addedMedicalRecord = medicalRecordRepository
                    .save(pMedicalRecord);
            return addedMedicalRecord;
        } else {
            return null;
        }
    }

    public MedicalRecord findByLastNameAndFirstName(final String lastName,
            final String firstName) throws MedicalRecordNotFoundException {
        MedicalRecord foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (foundMedicalRecord == null) {
            throw new MedicalRecordNotFoundException();
        }
        return foundMedicalRecord;
    }

    public MedicalRecord updateMedicalRecord(
            final MedicalRecord pMedicalRecord) {
        MedicalRecord medicalRecordToUpdate = pMedicalRecord;
        MedicalRecord foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        medicalRecordToUpdate.setId(foundMedicalRecord.getId());
        MedicalRecord updatedMedicalRecord = medicalRecordRepository
                .save(medicalRecordToUpdate);
        return updatedMedicalRecord;
    }

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
