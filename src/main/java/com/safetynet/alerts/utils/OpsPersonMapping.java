package com.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.DTO.ChildDTO;
import com.safetynet.alerts.DTO.OpsPersonDTO;
import com.safetynet.alerts.model.PersonEntity;

/**
 * This class performs data mapping of a List of PersonEntity to create a
 * ChildDTO object.
 *
 * * @author Thierry Schreiner
 */
@Component
public class OpsPersonMapping {
    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(OpsPersonMapping.class);

    /**
     * Date format used to convert String parameter to LocalDate.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("MM/dd/yyyy");

    /**
     * For OPS1, this method is used to transform a list of PersonEntity to a
     * list of OpsPersonDTO, using the next method to convert each PersonEntity
     * to OpsPersonDTO.
     *
     * @param listPE - the list of PersonEntity
     * @return a List<OpsPersonDTO> object
     */
    public List<OpsPersonDTO> convertToCoveredByStationPerson(
            final List<PersonEntity> listPE) {
        List<OpsPersonDTO> listPersons = new ArrayList<OpsPersonDTO>();
        OpsPersonDTO coveredPerson;
        for (PersonEntity pEnt : listPE) {
            coveredPerson = convertToCoveredPerson(pEnt);
            listPersons.add(coveredPerson);
        }
        LOGGER.debug("OPS#1 >>> OpsPersonDTO list: {}",
                listPersons.toString());

        return (listPersons);
    }

    /**
     * For OPS1, this method convert a PersonEntity to a OpsPersonDTO.
     *
     * @param pEnt - the PersonEntity object to convert
     * @return a OpsPersonDTO object
     */
    public OpsPersonDTO convertToCoveredPerson(final PersonEntity pEnt) {
        OpsPersonDTO coveredPerson = new OpsPersonDTO();
        String unit;
        coveredPerson.setFirstName(pEnt.getFirstName());
        coveredPerson.setLastName(pEnt.getLastName());
        int age = ageCalculation(pEnt.getMedRecId().getBirthdate());
        if (age < 2) {
            age = ageCalculationInMonth(pEnt.getMedRecId().getBirthdate());
            unit = " months old";
        } else {
            unit = " years old";
        }
        coveredPerson.setAge(Long.toString(age) + unit);
        coveredPerson.setAddress(pEnt.getAddressFireSt().getAddress());
        coveredPerson.setPhone(pEnt.getPhone());

        return coveredPerson;
    }

    /**
     * This final value of 18 years is subtracted to the day date to make a
     * comparison date to recognize adult persons using their birth date.
     */
    static final long DIX_HUIT_YEARS = 18;

    /**
     * Fors OPS#2 CHILD ALERT, the createChildAlert method loop the given List
     * of PersonEntity to group all children in a child list (with firstName,
     * lastName and age) and list all adults in a simple String list ( only with
     * firstName +" "+ lastName). Then it create a new ChilAlert object and sets
     * its fields with the given address and the two previous list, before it
     * returns the ChildDTO.
     *
     * @param pEntList
     * @param address
     * @return a ChildDTO object
     */
    public ChildDTO create(final List<PersonEntity> pEntList,
            final String address) {
        ChildDTO childDTO = new ChildDTO();
        String unit;
        List<String> adultList = new ArrayList<>();
        List<OpsPersonDTO> childList = new ArrayList<>();

        for (PersonEntity personEntity : pEntList) {
            int age = ageCalculation(personEntity.getMedRecId().getBirthdate());

            if (age > DIX_HUIT_YEARS) { // The person is an adult
                adultList.add(personEntity.getFirstName() + " "
                        + personEntity.getLastName());
            } else { // --------------------------- The person is a child
                if (age < 2) {
                    age = ageCalculationInMonth(
                            personEntity.getMedRecId().getBirthdate());
                    unit = " months old";
                } else {
                    unit = " years old";
                }
                childList.add(new OpsPersonDTO(personEntity.getFirstName(),
                        personEntity.getLastName(), Long.toString(age) + unit,
                        personEntity.getAddressFireSt().getAddress(),
                        personEntity.getPhone()));
            }
        }
        LOGGER.debug("OPS#2 >>> Address = {}", address);
        LOGGER.debug("OPS#2 >>> OpsPersonDTO list = {}",
                childList.toString());
        LOGGER.debug("OPS#2 >>> Adult list = {}", adultList);

        childDTO.setAddress(address);
        childDTO.setChildList(childList);
        childDTO.setAdultList(adultList);
        return childDTO;
    }

    /**
     *
     * The ageCalculation method calculate age in full years between the
     * birthdate and today.
     *
     * @param pBirthdate
     * @return a int value
     */
    private int ageCalculation(final String pBirthdate) {
        LocalDate birthdate = LocalDate.parse(pBirthdate, formatter);
        long dateInterval;
        dateInterval = birthdate.until(LocalDate.now(), ChronoUnit.YEARS);
        return (int) dateInterval;
    }

    /**
     * The ageCalculationInMonth method calculate age in full months between the
     * birthdate and today.
     *
     * @param pBirthdate
     * @return a int value
     */
    private int ageCalculationInMonth(final String pBirthdate) {
        LocalDate birthdate = LocalDate.parse(pBirthdate, formatter);
        long dateInterval;
        dateInterval = birthdate.until(LocalDate.now(), ChronoUnit.MONTHS);
        return (int) dateInterval;
    }

}
