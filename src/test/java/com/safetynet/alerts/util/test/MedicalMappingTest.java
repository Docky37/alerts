package com.safetynet.alerts.util.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.utils.MedicalMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalMapping.class)
public class MedicalMappingTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private MedicalMapping medicalMapping;

    public static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person(1L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(3L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public static List<AddressFireStation> addressFireStList = new ArrayList<>();
    static {
        addressFireStList
                .add(new AddressFireStation(1L, "1509 Culver St", "1"));
        addressFireStList.add(new AddressFireStation(2L, "29_15th_St", "1"));
        addressFireStList.add(new AddressFireStation(3L, "834 Binoc Ave", "3"));
    }

    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    static {
        medicalRecordList
                .add(new MedicalRecord(1L, "John", "Boyd", "03/06/1984",
                        new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                        new String[] { "nillacilan" }));
        medicalRecordList.add(new MedicalRecord(
                2L, "Jacob", "Boyd", "03/06/1989", new String[] {
                        "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" },
                new String[] {}));
        medicalRecordList.add(new MedicalRecord(3L, "Tenley", "Boyd",
                "02/18/2012", new String[] {}, new String[] { "peanut" }));
        medicalRecordList.add(new MedicalRecord(4L, "Roger", "Boyd",
                "09/06/2018", new String[] {}, new String[] {}));
        medicalRecordList.add(new MedicalRecord(4L, "Felicia", "Boyd",
                "01/08/1986", new String[] { "tetracyclaz:650mg" },
                new String[] { "xilliathal" }));
    }

    public static List<PersonEntity> p = new ArrayList<>();

    static {
        p.add(new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(0)));
        p.add(new PersonEntity(2L, "Jacob", "Boyd", addressFireStList.get(0),
                "841-874-6513", "drk@email.com", medicalRecordList.get(1)));
        p.add(new PersonEntity(4L, "Tenley", "Boyd", addressFireStList.get(1),
                "841-874-6512", "tenz@email.com", medicalRecordList.get(2)));
        p.add(new PersonEntity(6L, "Roger", "Boyd", addressFireStList.get(1),
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(3)));
        p.add(new PersonEntity(7L, "Felicia", "Boyd", addressFireStList.get(2),
                "841-874-6544", "jaboyd@email.com", medicalRecordList.get(4)));
    }

    public static Household mappedFireAlert = new Household();
    public static List<PersonInfoDTO> myFireList = new ArrayList<>();
    static {
        myFireList.add(new PersonInfoDTO(p.get(0).getFirstName(),
                p.get(0).getLastName(), "36 years old",
                p.get(0).getMedRecId().getMedications(),
                p.get(0).getMedRecId().getAllergies(), p.get(0).getPhone()));
        myFireList.add(new PersonInfoDTO(p.get(1).getFirstName(),
                p.get(1).getLastName(), "31 years old",
                p.get(1).getMedRecId().getMedications(),
                p.get(1).getMedRecId().getAllergies(), p.get(1).getPhone()));
        myFireList.add(new PersonInfoDTO(p.get(2).getFirstName(),
                p.get(2).getLastName(), "31 years old",
                p.get(2).getMedRecId().getMedications(),
                p.get(2).getMedRecId().getAllergies(), p.get(2).getPhone()));
        
        mappedFireAlert.setAddressFireStation(addressFireStList.get(0));
        mappedFireAlert.setPersonList(myFireList);
    }

    // OPS #4 - FIRE ALERT ---------------------------------------------------
    @Test
    public void givenAListOfPerson_whenMapFire_thenReturnPersonEntityList()
            throws Exception {
        LOGGER.info("Start test: Person list mapping to FireAlertt");
        // GIVEN
        String address = addressFireStList.get(0).getAddress();
        // WHEN
        Household household = medicalMapping.mapFire(p, address);
        // THEN
        assertThat(household.getAddressFireStation()).isEqualTo(
                mappedFireAlert.getAddressFireStation());
    }

    // OPS #5 - FLOOD ALERT ---------------------------------------------------
    @Test
    public void givenAListOfPerson_whenMapFlood_thenReturnPersonEntityList()
            throws Exception {
        LOGGER.info("Start test: Person list mapping to PersonneEntity list");
        // GIVEN
        // WHEN
        List<FloodDTO> floodList = medicalMapping.mapFlood(p);
        // THEN
        
    }
}
