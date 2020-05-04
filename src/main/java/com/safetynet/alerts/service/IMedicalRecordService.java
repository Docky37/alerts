package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecordDTO;

public interface IMedicalRecordService {

    /**
     * The addListMedicalRecord method allows user to save a list of medical
     * records in DB.
     *
     * @param pListMedicalRecord
     * @return a List<MedicalRecordDTO>
     */
    List<MedicalRecordDTO> addListMedicalRecord(
            List<MedicalRecordDTO> pListMedicalRecord);

    /**
     * The find all method allows user to get a list of all medical records
     * saved in DB.
     *
     * @return a List<MedicalRecordDTO>
     */
    List<MedicalRecordDTO> findAll();

    /**
     * The addMedicalRecord method allows user to add one medical record in DB,
     * only if the person identified by lastName & firstName in the
     * MedicalRecordDTO exists in database persons table.
     *
     * @param pMedicalRecord
     * @return a MedicalRecordDTO
     */
    MedicalRecordDTO addMedicalRecord(MedicalRecordDTO pMedicalRecord);

    /**
     * The findByLastNameAndFirstName method allows user to find a medical
     * record in DB.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordDTO
     * @throws MedicalRecordNotFoundException
     */
    MedicalRecordDTO findByLastNameAndFirstName(String lastName,
            String firstName) throws MedicalRecordNotFoundException;

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
    MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO pMedicalRecord)
            throws MedicalRecordNotFoundException;

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * MedicalRecordDTO to delete in DB and get its id to invokes the deleteById
     * method of CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordDTO
     */
    MedicalRecordDTO deleteAMedicalRecord(String lastName, String firstName);

}
