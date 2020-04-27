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
import com.safetynet.alerts.model.ChildAlert;
import com.safetynet.alerts.model.CoveredPopulation;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.ChildAlertMapping;

/**
 * OpsPersonService is the class in charge of the OPSperson business work.
 *
 * @author Thierry Schreiner
 */
@Service
public class OpsPersonService implements IOpsPersonService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * This final value of 18 years is subtracted to the day date to make a
     * comparison date to recognize adult persons using their birth date.
     */
    static final long DIX_HUIT_YEARS = 18;

    /**
     * OpsPersonRepository is an Interface that extends CrudRepository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * The ChildAlertMapping class performs data mapping of a List of
     * PersonEntity to create aChildAlert object.
     *
     */
    @Autowired
    private ChildAlertMapping childAlertMapping;

    /**
     * Class constructor - Set personRepository & personMapping (IoC).
     *
     * @param pPersonRepos
     * @param pPersonMapping
     * @param pChildAlertMapping
     */
    public OpsPersonService(final PersonRepository pPersonRepos,
            final ChildAlertMapping pChildAlertMapping) {
        personRepository = pPersonRepos;
        childAlertMapping = pChildAlertMapping;
    }

    /**
     * OPS#1 - Get the count of children and adults and the list of all persons
     * covered by the given station.
     *
     * @param pStation
     * @return a CoveredPopulation object
     */
    @Override
    public CoveredPopulation populationCoveredByStation(final String pStation) {
        Date compareDate = Date
                .valueOf(LocalDate.now().minusYears(DIX_HUIT_YEARS));
        CoveredPopulation coveredPopulation = new CoveredPopulation();
        coveredPopulation
                .setTotal(personRepository.countByAddressIdStation(pStation));
        coveredPopulation.setAdultCount(personRepository
                .countAdultsByAddressIdStation(pStation, compareDate));
        coveredPopulation.setChildCount(coveredPopulation.getTotal()
                - coveredPopulation.getAdultCount());

        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findByAddressIdStationOrderByAddressIdStation(pStation);
        coveredPopulation.setCoveredPersons(
                childAlertMapping.convertToCoveredByStationPerson(listPE));
        return coveredPopulation;
    }

    // OPS #2 ENDPOINT -------------------------------------------------------
    /**
     * OPS#2 - ChildAlert: the list of children (with age) and adults living in
     * a given address.
     *
     * @param address
     * @return a ChildAlert object
     */
    @Override
    public ChildAlert findListOfChildByAddress(final String address) {
        List<PersonEntity> pEntList = personRepository
                .findByAddressIdAddress(address);
        ChildAlert childAlert = childAlertMapping.create(pEntList, address);
        return childAlert;
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
    @Override
    public List<String> findAllPhoneListByStation(final String pStation) {
        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findByAddressIdStationOrderByAddressIdStation(pStation);
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
    @Override
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
