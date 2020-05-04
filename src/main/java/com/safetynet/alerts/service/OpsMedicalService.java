package com.safetynet.alerts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.HouseholdDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.utils.OpsMedicalMapping;

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
     * The OpsMedicalMapping class performs data mapping of a List of PersonEntity
     * to create a HouseholdDTO object.
     */
    @Autowired
    private OpsMedicalMapping opsMedicalMapping;

    /**
     * Class constructor - Set personRepository & personMapping (IoC).
     *
     * @param pPersonRepos
     * @param pMedicalMapping
     */
    public OpsMedicalService(final PersonRepository pPersonRepos,
            final OpsMedicalMapping pMedicalMapping) {
        personRepository = pPersonRepos;
        opsMedicalMapping = pMedicalMapping;
    }

    // OPS #4 ENDPOINT -------------------------------------------------------
    /**
     * OPS#4 fire returns a ChildDTO class that contains the list of children
     * (with age) and adults living in a given address. Each children is an
     * PersonInfoDTO object, so the List<PersonInfoDTO> is mapped in OPS#6
     * mapping method
     *
     * @param address
     * @return a ChildDTO object
     */
    @Override
    public HouseholdDTO fireByAddress(final String address) {
        List<PersonEntity> pEntList = personRepository
                .findByAddressIdAddress(address);
        LOGGER.debug("OpsMedicalService OPS#4 >>> PersonList: {}",
                pEntList.toString());

        HouseholdDTO householdDTO = opsMedicalMapping.mapFire(pEntList, address);
        LOGGER.info("OpsMedicalService OPS#4 >>> HouseholdDTO: {} - {}",
                householdDTO.getAddressEntity(),
                householdDTO.getPersonList());
        return householdDTO;

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
        List<PersonEntity> pEntList = (List<PersonEntity>) personRepository
                .findAllGroupByAddress(pStationList);
        LOGGER.debug("OpsMedicalService OPS#5 >>> PersonList: {}",
                pEntList.toString());

        List<FloodDTO> floodDTOList = opsMedicalMapping.mapFlood(pEntList);
        LOGGER.debug("OpsMedicalService OPS#5 >>> FloodDTO list: {}",
                floodDTOList.toString());

        return floodDTOList;
    }

    // OPS #6 ENDPOINT -------------------------------------------------------
    /**
     * OPS6 - GET request "personInfo" that gives . Contains medical
     * confidential data.
     *
     * @param pLastName
     * @param pFirstName
     * @return a List<PersonInfoDTO>
     */
    @Override
    public List<PersonInfoDTO> personInfo(final String pFirstName,
            final String pLastName) {
        LOGGER.info("OpsMedicalService OPS #6 >>> personInfo -");
        List<PersonEntity> pEntList = (List<PersonEntity>) personRepository
                .findByFirstNameAndLastName(pFirstName, pLastName);
        List<PersonInfoDTO> personInfoDTOList = opsMedicalMapping
                .mapPersonInfoList(pEntList);
        return personInfoDTOList;
    }

}
