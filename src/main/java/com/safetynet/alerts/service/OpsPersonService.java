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
import com.safetynet.alerts.DTO.ChildDTO;
import com.safetynet.alerts.DTO.CoveredPopulationDTO;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.OpsPersonMapping;

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
     * The OpsPersonMapping class performs data mapping of a List of
     * PersonEntity to create aChildAlert object.
     *
     */
    @Autowired
    private OpsPersonMapping opsPersonMapping;

    /**
     * Class constructor - Set personRepository & personMapping (IoC).
     *
     * @param pPersonRepos
     * @param pOpsPersonMapping
     */
    public OpsPersonService(final PersonRepository pPersonRepos,
            final OpsPersonMapping pOpsPersonMapping) {
        personRepository = pPersonRepos;
        opsPersonMapping = pOpsPersonMapping;
    }

    /**
     * OPS#1 - Get the count of children and adults and the list of all persons
     * covered by the given station.
     *
     * @param pStation
     * @return a CoveredPopulationDTO object
     */
    @Override
    public CoveredPopulationDTO populationCoveredByStation(
            final String pStation) {
        Date compareDate = Date
                .valueOf(LocalDate.now().minusYears(DIX_HUIT_YEARS));
        CoveredPopulationDTO coveredPopulationDTO = new CoveredPopulationDTO();
        coveredPopulationDTO
                .setTotal(personRepository.countByAddressIdStation(pStation));
        coveredPopulationDTO.setAdultCount(personRepository
                .countAdultsByAddressIdStation(pStation, compareDate));
        coveredPopulationDTO.setChildCount(coveredPopulationDTO.getTotal()
                - coveredPopulationDTO.getAdultCount());
        LOGGER.debug(
                "OpsPersonService OPS#1 >>> Covered population: {} children"
                        + " + {} Adults = {}",
                coveredPopulationDTO.getChildCount(),
                coveredPopulationDTO.getAdultCount(),
                coveredPopulationDTO.getTotal());

        List<PersonEntity> listPE = (List<PersonEntity>) personRepository
                .findByAddressIdStationOrderByAddressIdStation(pStation);
        LOGGER.debug("OpsPersonService OPS#1 >>> Covered Person list: {}",
                listPE.toString());

        coveredPopulationDTO.setCoveredPersons(
                opsPersonMapping.convertToCoveredByStationPerson(listPE));
        LOGGER.debug("OpsPersonService OPS#1 >>> coveredPopulationDTO: {}",
                coveredPopulationDTO.toString());

        return coveredPopulationDTO;
    }

    // OPS #2 ENDPOINT -------------------------------------------------------
    /**
     * OPS#2 - ChildDTO: the list of children (with age) and adults living in a
     * given address.
     *
     * @param address
     * @return a ChildDTO object
     */
    @Override
    public ChildDTO findListOfChildByAddress(final String address) {
        List<PersonEntity> pEntList = personRepository
                .findByAddressIdAddress(address);
        LOGGER.debug("OpsPersonService OPS#2 >>> PersonEntity list = {}",
                pEntList.toString());
        ChildDTO childDTO = opsPersonMapping.create(pEntList, address);
        LOGGER.debug("OpsPersonService OPS#2 >>> ChildDTO = {}",
                childDTO.toString());

        return childDTO;
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
        LOGGER.debug("OpsPersonService OPS#3 >>> PersonEntity list = {}",
                listPE.toString());
        List<String> phoneList = new ArrayList<>();
        for (PersonEntity p : listPE) {
            phoneList.add(p.getPhone());
        }
        LOGGER.debug("OpsPersonService OPS#3 >>> Phone list = {}",
                phoneList.toString());

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
        LOGGER.debug("OpsPersonService OPS#7 >>> PersonEntity list = {}",
                listPE.toString());
        List<String> eMailList = new ArrayList<>();
        for (PersonEntity p : listPE) {
            eMailList.add(p.getEmail());
        }
        LOGGER.debug("OpsPersonService OPS#7 >>> eMail list = {}",
                eMailList.toString());
        return eMailList;
    }

}
