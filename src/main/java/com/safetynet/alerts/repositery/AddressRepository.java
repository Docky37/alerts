package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.alerts.model.AddressEntity;

/**
 * The interface AddressRepository allows us to use CrudRepository
 * methods without any implementation. The application uses the standard methods
 * save, saveAll, findAll, put and remove from the CrudRepository interface. It
 * also define a findByAddress method.
 *
 * @author Thierry Schreiner
 */
public interface AddressRepository
        extends CrudRepository<AddressEntity, Long> {

    /**
     * The findByAddress method use keyword "findBy" associated to a repository
     * field, so it is easily resolved by the CrudRepository.
     *
     * @param pAddressFireStation
     * @return an AddressEntity instance
     */
    AddressEntity findByAddress(String pAddressFireStation);

}
