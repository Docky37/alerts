package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.MedicalMapping;

/**
 * OpsMedicalService is the class in charge of the OPS medical business work.
 *
 * @author Thierry Schreiner
 */
@Service
public class OpsMedicalService implements IOpsMedicalService {

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
     * The MedicalMapping class performs data mapping of a List of PersonEntity
     * to create a Household object.
     */
    @Autowired
    private MedicalMapping medicalMapping;

    /**
     * Class constructor - Set personRepository & personMapping (IoC).
     *
     * @param pPersonRepos
     * @param pMedicalMapping
     */
    public OpsMedicalService(final PersonRepository pPersonRepos,
            final MedicalMapping pMedicalMapping) {
        personRepository = pPersonRepos;
        medicalMapping = pMedicalMapping;
    }

    // OPS #4 ENDPOINT -------------------------------------------------------
    /**
     * OPS#4 - ChildAlert: the list of children (with age) and adults living in
     * a given address.
     *
     * @param address
     * @return a ChildAlert object
     */
    @Override
    public Household fireByAddress(final String address) {
        List<PersonEntity> pEntList = personRepository
                .findByAddressIdAddress(address);
        LOGGER.info("PersonList: {}", pEntList.toArray());

        Household household = medicalMapping.mapFire(pEntList, address);
        LOGGER.info("Household: {} - {}", household.getAddressFireStation(),
                household.getPersonList());
        return household;

    }

    // OPS #5 ENDPOINT -------------------------------------------------------
    /**
     * OPS#5 - Get the a list of persons covered by the given station list. This
     * list is ordered by station and address.
     *
     * @param pStationList - the list of fire stations that we want the list of
     *                     all covered inhabitants
     * @return a List<FloodDTO> object.
     */
    @Override
    public List<FloodDTO> floodByStation(final List<String> pStationList) {
        LOGGER.info("OPS #5 - flood -");
        List<Integer> stationList = new ArrayList<Integer>();
        for (String station : pStationList) {
            stationList.add(Integer.parseInt(station));
        }
        LOGGER.info(stationList.toString());
        List<PersonEntity> pEntList = (List<PersonEntity>) personRepository
                .findAllGroupByAddress(pStationList);

        List<FloodDTO> floodDTOList = medicalMapping.mapFlood(pEntList);

        return floodDTOList;
    }

}
