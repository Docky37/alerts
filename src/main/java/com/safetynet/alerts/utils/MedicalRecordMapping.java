package com.safetynet.alerts.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.DTO.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecordEntity;

/**
 * This class is in charge of conversion between MedicalRecordDTO and
 * MedicalRecordEntity classes.
 *
 * @author Thierry Schreiner
 */
@Component
public class MedicalRecordMapping {

    /**
     * This method convert a MedicalRecordDTO to MedicalRecordEntity.
     *
     * @param medRec - the MedicalRecordDTO object to convert
     * @return a MedicalRecordEntity object
     */
    public MedicalRecordEntity convertToMedicalRecordEntity(
            final MedicalRecordDTO medRec) {
        MedicalRecordEntity medRecEntity = new MedicalRecordEntity();
        medRecEntity.setFirstName(medRec.getFirstName());
        medRecEntity.setLastName(medRec.getLastName());
        medRecEntity.setBirthdate(medRec.getBirthdate());
        medRecEntity.setMedications(medRec.getMedications());
        medRecEntity.setAllergies(medRec.getAllergies());
        return medRecEntity;
    }

    /**
     * This method is used to transform a list of MedicalRecordEntity to a list
     * of MedicalRecordDTO, using the next method to convert each
     * MedicalRecordEntity to MedicalRecordDTO.
     *
     * @param listPE - the list of MedicalRecordEntity
     * @return a List<MedicalRecordDTO> object
     */
    public List<MedicalRecordDTO> convertToMedicalRecordDTO(
            final List<MedicalRecordEntity> listPE) {
        List<MedicalRecordDTO> listPersons = new ArrayList<MedicalRecordDTO>();
        MedicalRecordDTO medicalRecordDTO;
        for (MedicalRecordEntity medRecEntity : listPE) {
            medicalRecordDTO = convertToMedicalRecordDTO(medRecEntity);
            listPersons.add(medicalRecordDTO);
        }

        return (listPersons);
    }

    /**
     * This method convert a MedicalRecordEntity to a MedicalRecordDTO.
     *
     * @param medRecEntity - the MedicalRecordEntity object to convert
     * @return a MedicalRecordDTO object
     */
    public MedicalRecordDTO convertToMedicalRecordDTO(
            final MedicalRecordEntity medRecEntity) {
        MedicalRecordDTO medRecDTO = new MedicalRecordDTO();
        medRecDTO.setFirstName(medRecEntity.getFirstName());
        medRecDTO.setLastName(medRecEntity.getLastName());
        medRecDTO.setBirthdate(medRecEntity.getBirthdate());
        medRecDTO.setMedications(medRecEntity.getMedications());
        medRecDTO.setAllergies(medRecEntity.getAllergies());
        return medRecDTO;
    }

}
