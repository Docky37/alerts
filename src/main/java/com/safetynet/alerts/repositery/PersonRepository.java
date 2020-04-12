package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.PersonEntity;

/**
 * The interface PersonRepository allows us to use CrudRepository methods
 * without any implementation. The application uses the standard methods save,
 * saveAll, findAll, put and remove from the CrudRepository interface. It also
 * define a findByLastName and firstName method.
 *
 * @author docky
 */
@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    /**
     * The method use keyword "findBy" associated to repository fields, so it is
     * easily resolved by the CrudRepository.
     *
     * @param pLastName
     * @param pFirstName
     * @return the Person
     */
    PersonEntity findByLastNameAndFirstName(String pLastName,
            String pFirstName);
}
