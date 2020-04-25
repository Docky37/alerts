package com.safetynet.alerts.util.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.PersonFLA;
import com.safetynet.alerts.model.ChildAlert;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.utils.ChildAlertMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(ChildAlertMapping.class)
public class ChilAlertMappingTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private ChildAlertMapping childAlertMapping;

    public static AddressFireStation addressFireStation = new AddressFireStation(
            1L, "1509 Culver St", "3");

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

    public static List<PersonEntity> pEntList = new ArrayList<>();

    static {
        pEntList.add(new PersonEntity(1L, "John", "Boyd", addressFireStation,
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(0)));
        pEntList.add(new PersonEntity(2L, "Jacob", "Boyd", addressFireStation,
                "841-874-6513", "drk@email.com", medicalRecordList.get(1)));
        pEntList.add(new PersonEntity(4L, "Tenley", "Boyd", addressFireStation,
                "841-874-6512", "tenz@email.com", medicalRecordList.get(2)));
        pEntList.add(new PersonEntity(6L, "Roger", "Boyd", addressFireStation,
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(3)));
        pEntList.add(new PersonEntity(7L, "Felicia", "Boyd", addressFireStation,
                "841-874-6544", "jaboyd@email.com", medicalRecordList.get(4)));
    }

    public static ChildAlert childAlert = new ChildAlert();
    public static PersonFLA child1 = new PersonFLA("Tenley", "Boyd", "8 years old");
    public static PersonFLA child2 = new PersonFLA("Roger", "Boyd", "19 months old");
    public static List<PersonFLA> childList = Arrays.asList(child1, child2);
    public static List<String> adultList = Arrays.asList("John Boyd",
            "Jacob Boyd", "Felicia Boyd");
    static {
        childAlert.setAddress("1509 Culver St");
        childAlert.setChildList(childList);
        childAlert.setAdultList(adultList);
    }

    @Test
    public void givenTheListOfPersonsOfAnAddress_whenConvert_thenReturnChilAlert()
            throws Exception {
        LOGGER.info("Start test: ChildAlert mapping");
        // GIVEN
        // WHEN
        ChildAlert mappedChildAlert = childAlertMapping
                .create(pEntList, "1509 Culver St");
        // THEN
        assertThat(mappedChildAlert.toString())
                .isEqualTo(childAlert.toString());
    }

}
