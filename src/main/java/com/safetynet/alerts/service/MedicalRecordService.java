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
     * The balanceSheet is a report of the batch import of person list.
     */
    private String balanceSheet;

    /**
     * Getter of balanceSheet.
     *
     * @return the balanceSheet
     */
    @Override
    public String getBalanceSheet() {
        return balanceSheet;
    }

    /**
     * Used to add line break in String.
     */
    private String newLine = System.getProperty("line.separator");

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
        LOGGER.debug(
                " | MedicalRecordService 'Find all medicalRecords' start -->");
        List<MedicalRecordEntity> medicalRecordList =
                (List<MedicalRecordEntity>) medicalRecordRepository
                .findAll();
        List<MedicalRecordDTO> foundMedicalRecordList = medicalRecordMapping
                .convertToMedicalRecordDTO(medicalRecordList);
        if (foundMedicalRecordList.size() > 0) {
            for (MedicalRecordDTO medicalRecordDTO : foundMedicalRecordList) {
                LOGGER.debug(" |  {}", medicalRecordDTO.toString());

            }
        } else {
            LOGGER.error(" | !   NO MEDICAL RECORDS IN DATABASE!");
        }
        LOGGER.debug(" | End of MedicalRecordService"
                + " 'Find all medicalRecords.' ---");
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
        LOGGER.debug(
                " | PersonService 'Add a list of medicalRecords' start -->");
        balanceSheet = "";
        List<MedicalRecordDTO> createdList = new ArrayList<MedicalRecordDTO>();
        MedicalRecordDTO createdMedRecDTO;
        int countOfCreatedMedRec = 0;
        int countOfRejectedMedRec = 0;
        for (MedicalRecordDTO medicalRecordDTO : pMedicalRecordList) {
            createdMedRecDTO = addMedicalRecord(medicalRecordDTO);
            if (createdMedRecDTO == null) { // MedicalRecord not created.
                countOfRejectedMedRec++;
                if (balanceSheet.isEmpty()) {
                    balanceSheet = "These registred medicalRecords have not"
                            + " been created, to avoid duplicates: " + newLine;
                }
                balanceSheet = balanceSheet.concat(
                        "   -  " + medicalRecordDTO.toString()) + newLine;
            } else {
                createdList.add(createdMedRecDTO);
                countOfCreatedMedRec++;
            }
        }
        balanceSheet = balanceSheet
                .concat(newLine + "Balance sheet: " + countOfCreatedMedRec
                        + " medical records have been created and "
                        + countOfRejectedMedRec + " rejected.");
        LOGGER.info(
                "Balance sheet:  {} medical records created and {} rejected.",
                countOfCreatedMedRec, countOfRejectedMedRec);

        if (createdList.size() > 0) {
            return createdList;
        }
        return new ArrayList<MedicalRecordDTO>();
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
        LOGGER.debug(
                " | MedicalRecordService 'Add a medicalRecord ' start -->");
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                        pMedicalRecord.getFirstName());
        if (foundMedicalRecord == null) {
            PersonEntity personToJoin = personRepository
                    .findByLastNameAndFirstName(pMedicalRecord.getLastName(),
                            pMedicalRecord.getFirstName());
            if (personToJoin == null) {
                LOGGER.error(
                        " | !   Cannot create a orphan medical Record,"
                                + "its owner ({} {}) is not registred !",
                        pMedicalRecord.getFirstName(),
                        pMedicalRecord.getLastName());
                LOGGER.debug(" | End of MedicalRecordService"
                        + " 'Add a medicalRecord.' ---");
                return new MedicalRecordDTO("", "", "", new String[] {},
                        new String[] {});
            } else {
                MedicalRecordEntity addedMedicalRecord = medicalRecordMapping
                        .convertToMedicalRecordEntity(pMedicalRecord);
                addedMedicalRecord = medicalRecordRepository
                        .save(addedMedicalRecord);
                personToJoin.setMedRecId(addedMedicalRecord);
                personRepository.save(personToJoin);

                LOGGER.debug(" |   + Medical record created for {} {}.",
                        pMedicalRecord.getFirstName(),
                        pMedicalRecord.getLastName());
                LOGGER.debug(
                        " | End of MedicalRecordService"
                        + " 'Add a medicalRecord.' ---");
                return medicalRecordMapping
                        .convertToMedicalRecordDTO(addedMedicalRecord);
            }
        } else {
            LOGGER.error(
                    " | !   Cannot create a medical Record for {} {},"
                    + " it already exits!",
                    pMedicalRecord.getFirstName(),
                    pMedicalRecord.getLastName());
            LOGGER.debug(
                    " | End of MedicalRecordService"
                    + " 'Add a medicalRecord.' ---");
            return null;
        }
    }

    /**
     * The findByLastNameAndFirstName method allows user to find a medical
     * record in DB.
     *
     * @param pLastName
     * @param pFirstName
     * @return a MedicalRecordDTO
     * @throws MedicalRecordNotFoundException
     */
    @Override
    public MedicalRecordDTO findByLastNameAndFirstName(final String pLastName,
            final String pFirstName) throws MedicalRecordNotFoundException {
        LOGGER.debug(
                " | MedicalRecordService 'Find a medicalRecord ({} {})"
                + "' start -->",
                pFirstName, pLastName);
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (foundMedicalRecord == null) {
            LOGGER.error(" | !  MEDICAL RECORD NOT FOUND!");
            LOGGER.debug(
                    " | End of MedicalRecordService"
                    + " 'Find a medicalRecord.' ---");
            throw new MedicalRecordNotFoundException();
        }
        MedicalRecordDTO foundMedRec = medicalRecordMapping
                .convertToMedicalRecordDTO(foundMedicalRecord);
        LOGGER.debug(" |  {}", foundMedRec.toString());
        LOGGER.debug(" | End of MedicalRecordService 'Find a person.' ---");
        return foundMedRec;
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
        LOGGER.debug(
                " | MedicalRecord 'Update the MedicalRecord of {} {}"
                + "' start -->", pFirstName, pLastName);
        MedicalRecordDTO medicalRecordToUpdate = pMedicalRecord;
        MedicalRecordEntity foundMedicalRecord = medicalRecordRepository
                .findByLastNameAndFirstName(pLastName, pFirstName);
        if (foundMedicalRecord == null) { // Unregistred medical record
            LOGGER.error(
                    " | !   Cannot update this unregistered medical record"
                    + " ({} {})!", pFirstName, pLastName);
            LOGGER.debug(
                    " | End of PersonService 'Update a medicalRecord'. ---");
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
            MedicalRecordDTO updatedMedRec = medicalRecordMapping
                    .convertToMedicalRecordDTO(updatedMedicalRecord);
            LOGGER.debug(" |   OK now this medicalRecord is updated: {}.",
                    updatedMedRec.toString());
            LOGGER.debug(" | End of PersonService"
                    + " 'Update a medicalRecord'. ---");
            return updatedMedRec;
        }
        LOGGER.error(
                    " | !   Cannot change the owner of medical record"
                    + " ({} {})!", pFirstName, pLastName);
        LOGGER.debug(" | End of PersonService 'Update a medicalRecord'. ---");
        return null;
    }

    /**
     * Delete method that uses first findByLastNameAndFirstName to find the
     * MedicalRecordDTO to delete in DB and get its id to invokes the
     * deleteById method of CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecordDTO
     * @throws MedicalRecordNotFoundException
     */
    @Override
    public MedicalRecordDTO deleteAMedicalRecord(final String lastName,
            final String firstName) throws MedicalRecordNotFoundException {
        LOGGER.debug(" | MedicalRecordService -"
                + " Delete the medicalRecord of '{} {}' start -->",
                lastName, firstName);
        MedicalRecordEntity medicalRecordToDelete = medicalRecordRepository
                .findByLastNameAndFirstName(lastName, firstName);
        if (medicalRecordToDelete != null) {
            MedicalRecordDTO deletedMedicalRecordDTO = medicalRecordMapping
                    .convertToMedicalRecordDTO(medicalRecordToDelete);
            PersonEntity medRecOwner =
                    personRepository.findByLastNameAndFirstName(lastName,
                            firstName);
            medRecOwner.setMedRecId(null);
            medicalRecordRepository.deleteById(medicalRecordToDelete.getId());
            LOGGER.debug(" |   OK now medicalRecord '{}' is deleted.'",
                    deletedMedicalRecordDTO.toString());
            LOGGER.debug(" | End of MedicalRecordService"
                    + " 'Delete a medicalRecord.' ---");
            return deletedMedicalRecordDTO;
        }
        LOGGER.error(" |   MEDICAL RECORD NOT FOUND!");
        LOGGER.debug(" | End of MedicalRecordService"
                + " 'Delete a medicalRecord.' ---");
        throw new MedicalRecordNotFoundException();
    }

}
