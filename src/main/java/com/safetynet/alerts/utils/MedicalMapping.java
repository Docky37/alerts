package com.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.model.PersonEntity;

/**
 * This class performs data mapping of a List of PersonEntity to create a
 * Household object.
 *
 * * @author Thierry Schreiner
 */
@Component
public class MedicalMapping {
    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

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
     * The Household method loop the given List of PersonEntity to create a new
     * Household object and sets its fields with the given address and the list
     * of PersonInfoDTO covered, before it returns the Household.
     *
     * @param pEntList
     * @param address
     * @return a Household object
     */
    public Household mapFire(final List<PersonEntity> pEntList,
            final String address) {
        Household household = new Household();
        List<PersonInfoDTO> personList = new ArrayList<>();
        LOGGER.info("pEntList= {}", pEntList);

        for (PersonEntity personEntity : pEntList) {
            String ageString = ageCalculation(
                    personEntity.getMedRecId().getBirthdate());
            personList.add(new PersonInfoDTO(personEntity.getFirstName(),
                    personEntity.getLastName(), ageString,
                    personEntity.getMedRecId().getMedications(),
                    personEntity.getMedRecId().getAllergies(),
                    personEntity.getPhone()));

            LOGGER.info("PersonInfo list = {}", personList.toString());
        }
        household.setAddressFireStation(pEntList.get(0).getAddressFireSt());
        household.setPersonList(personList);

        LOGGER.info("Household = {} - {}", household.getAddressFireStation(),
                household.getPersonList().toArray());
        return household;
    }

    // OPS #5 - FLOOD ALERT ---------------------------------------------------
    /**
     * The mapFlood method loop the given ordered list of PersonEntity to create
     * the covered Household list of each station.
     *
     * @param pEntList
     * @return a List<FloodDTO> object
     */
    public List<FloodDTO> mapFlood(final List<PersonEntity> pEntList) {
        List<FloodDTO> floodDTOList = new ArrayList<>();
        List<Household> householdList = new ArrayList<>();
        LOGGER.info("FLOOD---pEntList= {}", pEntList);
        // for (String station : orderedStationList) {
        Household household = new Household();
        List<PersonInfoDTO> personList = new ArrayList<>();
        String currentStation = null;
        AddressFireStation currentAddress = null;
        for (PersonEntity p : pEntList) {
            if (currentStation == null) {
                currentStation = p.getAddressFireSt().getStation();
            }
            if (currentAddress == null) {
                currentAddress = p.getAddressFireSt();
            }
            if (p.getAddressFireSt() != currentAddress) {
                household.setAddressFireStation(currentAddress);
                household.setPersonList(personList);
                personList = new ArrayList<>();
                householdList.add(household);
                household = new Household();
                currentAddress = p.getAddressFireSt();
            }
            if (p.getAddressFireSt().getStation() != currentStation) {
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
        household.setAddressFireStation(currentAddress);
        household.setPersonList(personList);
        householdList.add(household);
        floodDTOList.add(new FloodDTO(currentStation, householdList));

        LOGGER.info("FloodAlert = {}", household.toString());
        // }
        return floodDTOList;

    }

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
