package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecordEntity;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.MedicalRecordRepository;
import com.safetynet.alerts.repositery.PersonRepository;

/**
 * MedicalRecordService is the class in charge of the MedicalRecordEntity business
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
     * @return a List<MedicalRecordEntity>
     */
    @Override
    public List<MedicalRecordEntity> addListMedicalRecord(
            final List<MedicalRecordEntity> pListMedicalRecord) {
        List<MedicalRecordEntity> createdList = new ArrayList<MedicalRecordEntity>();
        for (MedicalRecordEntity medicalRecordEntity : pListMedicalRecord) {
            MedicalRecordEntity createdMedRec = addMedicalRecord(medicalRecordEntity);
            createdList.add(createdMedRec);
        }
        // createdList = (List<MedicalRecordEntity>) medicalRecordRepository
        // .saveAll(pListMedicalRecord);
        return createdList;
    }

    /**
     * The find all method allows user to get a list of all medical records
     * saved in DB.
     *
     * @return a List<MedicalRecordEntity>
     */
    @Override
    public List<MedicalRecordEntity> findAll() {
        List<MedicalRecordEntity> medicalRecordList;
        medicalRecordList = (List<MedicalRecordEntity>) medicalRecordRepository
                .findAll();
        return medicalRecordList;
    }

    /**
     * The addMedicalRecord method allows user to add one medical record in DB,
     * only if the person identified by lastName & firstName in the
     * MedicalRecordEntity exists in database persons table.
     *
     * @param pMedicalRecord
     * @return a MedicalRecordEntity
     */
    @Override
    public MedicalRecordEntity addMedicalRecord(final MedicalRecordEntity pMedicalRecord) {
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
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
                MedicalRecordEntity addedMedicalRecord = medicalRecordRepository
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
     * @return a MedicalRecordEntity
     * @throws MedicalRecordNotFoundException
     */
    @Override
    public MedicalRecordEntity findByLastNameAndFirstName(final String lastName,
            final String firstName) throws MedicalRecordNotFoundException {
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
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
     * @return a MedicalRecordEntity
     */
    @Override
    public MedicalRecordEntity updateMedicalRecord(
            final MedicalRecordEntity pMedicalRecord) {
        MedicalRecordEntity medicalRecordToUpdate = pMedicalRecord;
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        if (foundMedicalRecord != null
                && foundMedicalRecord.getFirstName()
                        .contentEquals(pMedicalRecord.getFirstName())
                && foundMedicalRecord.getLastName()
                        .contentEquals(pMedicalRecord.getLastName())) {
            medicalRecordToUpdate.setId(foundMedicalRecord.getId());
            MedicalRecordEntity updatedMedicalRecord = medicalRecordRepository
                    .save(medicalRecordToUpdate);
            return updatedMedicalRecord;
        }
        return null;
    }

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * MedicalRecordEntity to delete in DB and get its id to invokes the deleteById
     * method of CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordEntity
     */
    @Override
    public MedicalRecordEntity deleteAMedicalRecord(final String lastName,
            final String firstName) {
        MedicalRecordEntity medicalRecordToDelete = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (medicalRecordToDelete != null) {
            medicalRecordRepository.deleteById(medicalRecordToDelete.getId());
        }
        return medicalRecordToDelete;

    }

}
