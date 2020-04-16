package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;

/**
 * OpsPersonService is the class in charge of the person business work.
 *
 * @author Thierry Schreiner
 */
@Service
public class OpsPersonService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * OpsPersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Class constructor - Set personRepository (IoC).
     *
     * @param pPersonRepos
     */
    public OpsPersonService(final PersonRepository pPersonRepos) {
        personRepository = pPersonRepos;
    }

    /**
     * OPS#7 - Get the list of e-mail of all inhabitants of the city.
     *
     * @param pCity - the city that we want inhabitants e-mail addresses.
     * @return a List<String> - the list of phone number of all inhabitants
     *         covered by the station
     */
    public List<String> findAllMailByCity(final String pCity) {
        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findByAddressIdCity(pCity);
        List<String> eMailList = new ArrayList<>();
        for (PersonEntity p : listPE) {
            eMailList.add(p.getEmail());
        }
        return eMailList;
    }

    /**
     * OPS#3 - Get the list of phone numbers of all inhabitants covered by one
     * station.
     *
     * @param pStation - the fire station that we want phone numbers of all
     *                 covered inhabitants
     * @return a List<String> of phone numbers.
     */
    public List<String> findAllPhoneListByStation(final String pStation) {
        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findByAddressIdStation(pStation);
        List<String> phoneList = new ArrayList<>();
        for (PersonEntity p : listPE) {
            phoneList.add(p.getPhone());
        }

        return phoneList;
    }

}
