package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecordEntity;

public interface IMedicalRecordService {

    /**
     * The addListMedicalRecord method allows user to save a list of medical
     * records in DB.
     *
     * @param pListMedicalRecord
     * @return a List<MedicalRecordEntity>
     */
    List<MedicalRecordEntity> addListMedicalRecord(
            List<MedicalRecordEntity> pListMedicalRecord);

    /**
     * The find all method allows user to get a list of all medical records
     * saved in DB.
     *
     * @return a List<MedicalRecordEntity>
     */
    List<MedicalRecordEntity> findAll();

    /**
     * The addMedicalRecord method allows user to add one medical record in DB,
     * only if the person identified by lastName & firstName in the
     * MedicalRecordEntity exists in database persons table.
     *
     * @param pMedicalRecord
     * @return a MedicalRecordEntity
     */
    MedicalRecordEntity addMedicalRecord(MedicalRecordEntity pMedicalRecord);

    /**
     * The findByLastNameAndFirstName method allows user to find a medical
     * record in DB.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordEntity
     * @throws MedicalRecordNotFoundException
     */
    MedicalRecordEntity findByLastNameAndFirstName(String lastName, String firstName)
            throws MedicalRecordNotFoundException;

    /**
     * The updateMedicalRecord method first uses the
     * MedicalRecordRepository.findByLastNameAndFirstName method to verify if
     * the person already exists in DB. If record exists, it calls the save
     * method of CrudRepository to update it.
     *
     * @param pMedicalRecord
     * @return a MedicalRecordEntity
     */
    MedicalRecordEntity updateMedicalRecord(MedicalRecordEntity pMedicalRecord);

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * MedicalRecordEntity to delete in DB and get its id to invokes the deleteById
     * method of CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordEntity
     */
    MedicalRecordEntity deleteAMedicalRecord(String lastName, String firstName);

}
