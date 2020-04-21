package com.safetynet.alerts.repositery;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
     * class that contains the address field.
     *
     * @param address
     * @return a List<PersonEntity>
     */
    List<PersonEntity> findByAddressIdAddress(String address);

    /**
     * This method use keyword "findBy" associated to repository fields
     * AddressId, a foreign key for ManyToOne join with AdressFireStation entity
     * class that contains the station field.
     *
     * @param pStation - the fire station that we want covered inhabitants phone
     * @return a List<PersonEntity>
     */
    List<PersonEntity> findByAddressIdStation(String pStation);

    /**
     * This method use keyword "countBy" associated to repository fields
     * AddressId, a foreign key for ManyToOne join with AdressFireStation entity
     * class that contains the station field.
     *
     * @param pStation
     * @return a long - the count of persons covered
     */
    long countByAddressIdStation(String pStation);

    /**
     * This method perform an JPQL query to get the count of adults covered by
     * the given station.
     *
     * @param pStation
     * @param compareDate
     * @return a long - the count of adults
     */
    @Query("SELECT COUNT(p) FROM PersonEntity p RIGHT JOIN p.addressId a"
            + " JOIN p.medRecId m" + " WHERE a.station= :station"
            + " AND m.birthDate < :compareDate")
    long countAdultsByAddressIdStation(@Param("station") String pStation,
            @Param("compareDate") Date compareDate);

}
