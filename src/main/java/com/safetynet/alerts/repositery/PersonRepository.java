package com.safetynet.alerts.repositery;

import java.util.List;

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
     * This method use keyword "findBy" associated to repository fields lastName
     * and firstName, so it is easily resolved by the CrudRepository.
     *
     * @param pLastName
     * @param pFirstName
     * @return the Person
     */
    PersonEntity findByLastNameAndFirstName(String pLastName,
            String pFirstName);

    /**
     * This method use keyword "findBy" associated to repository fields
     * AddressId, a foreign key for ManyToOne join with AdressFireStation entity
     * class that contains city field.
     *
     * @param city - the city that we want inhabitants mail addresses.
     * @return a List<PersonEntity>
     */
    List<PersonEntity> findByAddressIdCity(String city);

    /**
     * This method use keyword "findBy" associated to repository fields
     * AddressId, a foreign key for ManyToOne join with AdressFireStation entity
     * class that contains station field.
     *
     * @param pStation - the fire station that we want covered inhabitants phone
     * @return a List<PersonEntity>
     */
    List<PersonEntity> findByAddressIdStation(String pStation);

}
