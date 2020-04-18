package com.safetynet.alerts.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.CountOfPersons;
import com.safetynet.alerts.model.CoveredPerson;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.PersonMapping;

/**
 * OpsPersonService is the class in charge of the OPSperson business work.
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
     * This final value of 18 years is subtracted to the day date to make
     * a comparison date to recognize adult persons using their birth date.
     */
    static final long DIX_HUIT_YEARS = 18;

    /**
     * OpsPersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * PersonMapping instance is used.
     */
    @Autowired
    private PersonMapping personMapping;

    /**
     * Class constructor - Set personRepository & personMapping (IoC).
     *
     * @param pPersonRepos
     * @param pPersonMapping
     */
    public OpsPersonService(final PersonRepository pPersonRepos,
            final PersonMapping pPersonMapping) {
        personRepository = pPersonRepos;
        personMapping = pPersonMapping;
    }

    // OPS #1 ENDPOINT -------------------------------------------------------
    /**
     * OPS#1 - Get the list of persons covered by the given station.
     *
     * @param pStation - the fire station that we want the list of all covered
     *                 inhabitants
     * @return a List<String> of persons.
     */
    public List<CoveredPerson> findListOfPersonsCoveredByStation(
            final String pStation) {
        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findByAddressIdStation(pStation);
        List<CoveredPerson> coveredPersonList = personMapping
                .convertToCoveredByStationPerson(listPE);
        return coveredPersonList;
    }

    /**
     * OPS#1 - Count of children and adults covered by a given station.
     *
     * @param pStation
     * @return a CountOfPersons object
     */
    public CountOfPersons countPersonsCoveredByStation(final String pStation) {
        Date compareDate = Date
                .valueOf(LocalDate.now().minusYears(DIX_HUIT_YEARS));
        CountOfPersons countOfPersons = new CountOfPersons();
        countOfPersons
                .setTotal(personRepository.countByAddressIdStation(pStation));
        countOfPersons.setAdultCount(personRepository
                .countAdultsByAddressIdStation(pStation, compareDate));
        countOfPersons.setChildCount(
                countOfPersons.getTotal() - countOfPersons.getAdultCount());
        return countOfPersons;
    }

    // OPS #3 ENDPOINT -------------------------------------------------------
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

    // OPS #7 ENDPOINT -------------------------------------------------------
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

}
