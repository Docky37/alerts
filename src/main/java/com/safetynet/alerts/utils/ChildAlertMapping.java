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
import com.safetynet.alerts.model.PersonFLA;
import com.safetynet.alerts.model.ChildAlert;
import com.safetynet.alerts.model.PersonEntity;

/**
 * This class performs data mapping of a List of PersonEntity to create a
 * ChildAlert object.
 *
 * * @author Thierry Schreiner
 */
@Component
public class ChildAlertMapping {
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

    /**
     * The createChildAlert method loop the given List of PersonEntity to group
     * all children in a child list (with firstName, lastName and age) and list
     * all adults in a simple String list ( only with firstName +" "+ lastName).
     * Then it create a new ChilAlert object and sets its fields with the given
     * address and the two previous list, before it returns the ChildAlert.
     *
     * @param pEntList
     * @param address
     * @return a ChildAlert object
     */
    public ChildAlert create(final List<PersonEntity> pEntList,
            final String address) {
        ChildAlert childAlert = new ChildAlert();
        long dateInterval;
        String unit;
        List<String> adultList = new ArrayList<>();
        List<PersonFLA> childList = new ArrayList<>();
        LOGGER.info("pEntList= {}", pEntList);

        for (PersonEntity personEntity : pEntList) {
            LocalDate birthdate = LocalDate.parse(
                    personEntity.getMedRecId().getBirthdate(), formatter);
            LOGGER.info("First Name= {} and Birthdate = {}",
                    personEntity.getFirstName(), birthdate);
            dateInterval = birthdate.until(LocalDate.now(), ChronoUnit.YEARS);

            if (dateInterval > DIX_HUIT_YEARS) { // The person is an adult
                adultList.add(personEntity.getFirstName() + " "
                        + personEntity.getLastName());
                LOGGER.info("Adult list = {}", adultList);

            } else { // ---------------------------  The person is a child
                if (dateInterval < 2) {
                    dateInterval = birthdate.until(LocalDate.now(),
                            ChronoUnit.MONTHS);
                    unit = " months old";
                } else {
                    unit = " years old";
                }
                childList.add(new PersonFLA(personEntity.getFirstName(),
                        personEntity.getLastName(),
                        Long.toString(dateInterval) + unit));
                LOGGER.info("PersonFLA list = {}", childList.toString());
            }
        }
        childAlert.setAddress(address);
        childAlert.setChildList(childList);
        childAlert.setAdultList(adultList);
        LOGGER.info("ChildAlert = {}", childAlert.toString());
        return childAlert;
    }

}
