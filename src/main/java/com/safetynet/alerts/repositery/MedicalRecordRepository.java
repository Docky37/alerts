package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * The interface MedicalRecordRepository allows us to use CrudRepository methods
 * without any implementation. The application uses the standard methods save,
 * saveAll, findAll, put and remove from the CrudRepository interface. It also
 * define a findByLastName and firstName method.
 *
 * @author Thierry Schreiner
 */
public interface MedicalRecordRepository
        extends CrudRepository<MedicalRecord, Long> {

    /**
     * This method use keyword "findBy" associated to repository fields lastName
     * and firstName, so it is easily resolved by the CrudRepository.
     *
     * @param lastName
     * @param firstName
     * @return a MedicalRecord
     */
    MedicalRecord findByLastNameAndFirstName(String lastName, String firstName);

}
