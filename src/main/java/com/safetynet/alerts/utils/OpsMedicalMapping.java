package com.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.HouseholdDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.PersonEntity;

/**
 * This class performs data mapping of a List of PersonEntity to create a
 * HouseholdDTO object.
 *
 * * @author Thierry Schreiner
 */
@Component
public class OpsMedicalMapping {
    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(OpsMedicalMapping.class);

    /**
     * Date format used to convert String parameter to LocalDate.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("MM/dd/yyyy");

    /**
     * This final value of 18 years is subtracted to the day date to make a
     * comparison date to recognize adult persons using their birth date.
     */
    static final long DIX_HUIT_YEARS = 18;

    // OPS #4 - FIRE ALERT ---------------------------------------------------
    /**
     * The mapFire method creates an empty new HouseholdDTO object, then calls
     * the OPS#6 mapPersonInfoList method (with the given List of PersonEntity
     * as parameter) to create the covered list of PersonInfoDTO. Finally it
     * sets HouseholdDTO fields with the given address and the
     * List<PersonInfoDTO> before it returns the HouseholdDTO.
     *
     * @param pEntList
     * @param address
     * @return a HouseholdDTO object
     */
    public HouseholdDTO mapFire(final List<PersonEntity> pEntList,
            final String address) {
        HouseholdDTO householdDTO = new HouseholdDTO();
        List<PersonInfoDTO> personList = mapPersonInfoList(pEntList);
        LOGGER.debug("OPS#4 >>> PersonInfoDTO list: {}",
                personList.toString());
        householdDTO.setAddressEntity(pEntList.get(0).getAddressFireSt());
        householdDTO.setPersonList(personList);

        LOGGER.info("OPS#4 >>> HouseholdDTO = {} - {}",
                householdDTO.getAddressEntity(),
                householdDTO.getPersonList().toArray());
        return householdDTO;
    }

    // OPS #5 - FLOOD ALERT ---------------------------------------------------
    /**
     * The mapFlood method loop the given ordered list of PersonEntity to create
     * the covered HouseholdDTO list of each station.
     *
     * @param pEntList
     * @return a List<FloodDTO> object
     */
    public List<FloodDTO> mapFlood(final List<PersonEntity> pEntList) {
        List<FloodDTO> floodDTOList = new ArrayList<>();
        List<HouseholdDTO> householdList = new ArrayList<>();
        HouseholdDTO householdDTO = new HouseholdDTO();
        List<PersonInfoDTO> personList = new ArrayList<>();
        String currentStation = null;
        AddressEntity currentAddress = null;
        for (PersonEntity p : pEntList) {
            if (currentStation == null) {
                currentStation = p.getAddressFireSt().getStation();
            }
            if (currentAddress == null) {
                currentAddress = p.getAddressFireSt();
            }
            if (!p.getAddressFireSt().getAddress()
                    .equals(currentAddress.getAddress())) {
                householdDTO.setAddressEntity(currentAddress);
                householdDTO.setPersonList(personList);
                personList = new ArrayList<>();
                householdList.add(householdDTO);
                householdDTO = new HouseholdDTO();
                currentAddress = p.getAddressFireSt();
            }
            if (!p.getAddressFireSt().getStation().equals(currentStation)) {
                floodDTOList.add(new FloodDTO(currentStation, householdList));
                householdList = new ArrayList<>();
                currentStation = p.getAddressFireSt().getStation();
                // currentAddress = p.getAddressFireSt();
            }
            String ageString = ageCalculation(p.getMedRecId().getBirthdate());
            personList.add(new PersonInfoDTO(p.getFirstName(), p.getLastName(),
                    ageString, p.getMedRecId().getMedications(),
                    p.getMedRecId().getAllergies(), p.getPhone()));

        }

        householdDTO.setAddressEntity(currentAddress);
        householdDTO.setPersonList(personList);
        householdList.add(householdDTO);
        floodDTOList.add(new FloodDTO(currentStation, householdList));

        LOGGER.info("OPS#5 >>> FloodAlert = {}",
                householdDTO.toString());
        // }
        return floodDTOList;

    }

    // OPS #6 - PERSON INFO ---------------------------------------------------
    /**
     * Used by by OPS#6 and by OPS4 (as a sub-method of mapFire). The
     * mapPersonInfoList method loop the given List of PersonEntity to create a
     * the list of PersonInfoDTO. This method call ageCalculation method to
     * calculate the age of each person.
     *
     * @param pEntList
     * @return a List<PersonInfoDTO>
     */
    public List<PersonInfoDTO> mapPersonInfoList(
            final List<PersonEntity> pEntList) {
        List<PersonInfoDTO> personInfoList = new ArrayList<>();

        for (PersonEntity personEntity : pEntList) {
            String ageString = ageCalculation(
                    personEntity.getMedRecId().getBirthdate());
            personInfoList.add(new PersonInfoDTO(personEntity.getFirstName(),
                    personEntity.getLastName(), ageString,
                    personEntity.getMedRecId().getMedications(),
                    personEntity.getMedRecId().getAllergies(),
                    personEntity.getPhone()));

        }
        LOGGER.info("OPS#6 >>> PersonInfo list = {}",
                personInfoList.toString());

        return personInfoList;
    }

    /**
     * The ageCalculation method calculate age in full years between the
     * birthdate and today.
     *
     * @param pBirthdate
     * @return a int value
     */
    public String ageCalculation(final String pBirthdate) {
        LocalDate birthdate = LocalDate.parse(pBirthdate, formatter);
        long dateInterval;
        String ageString;
        dateInterval = birthdate.until(LocalDate.now(), ChronoUnit.YEARS);

        if (dateInterval < 2) {
            dateInterval = birthdate.until(LocalDate.now(), ChronoUnit.MONTHS);
            ageString = dateInterval + " months old";
        } else {
            ageString = dateInterval + " years old";
        }
        return ageString;
    }

}
