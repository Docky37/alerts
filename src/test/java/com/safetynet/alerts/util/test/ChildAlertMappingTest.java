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
import com.safetynet.alerts.DTO.ChildDTO;
import com.safetynet.alerts.DTO.OpsPersonDTO;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.MedicalRecordEntity;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.utils.OpsPersonMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(OpsPersonMapping.class)
public class ChildAlertMappingTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private OpsPersonMapping opsPersonMapping;

    public static AddressEntity addressEntity = new AddressEntity(
            1L, "1509 Culver St", "3");

    public static List<MedicalRecordEntity> medicalRecordList = new ArrayList<>();
    static {
        medicalRecordList
                .add(new MedicalRecordEntity(1L, "John", "Boyd", "03/06/1984",
                        new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                        new String[] { "nillacilan" }));
        medicalRecordList.add(new MedicalRecordEntity(
                2L, "Jacob", "Boyd", "03/06/1989", new String[] {
                        "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" },
                new String[] {}));
        medicalRecordList.add(new MedicalRecordEntity(3L, "Tenley", "Boyd",
                "02/18/2012", new String[] {}, new String[] { "peanut" }));
        medicalRecordList.add(new MedicalRecordEntity(4L, "Roger", "Boyd",
                "09/06/2018", new String[] {}, new String[] {}));
        medicalRecordList.add(new MedicalRecordEntity(4L, "Felicia", "Boyd",
                "01/08/1986", new String[] { "tetracyclaz:650mg" },
                new String[] { "xilliathal" }));
    }

    public static List<PersonEntity> pEntList = new ArrayList<>();

    static {
        pEntList.add(new PersonEntity(1L, "John", "Boyd", addressEntity,
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(0)));
        pEntList.add(new PersonEntity(2L, "Jacob", "Boyd", addressEntity,
                "841-874-6513", "drk@email.com", medicalRecordList.get(1)));
        pEntList.add(new PersonEntity(4L, "Tenley", "Boyd", addressEntity,
                "841-874-6512", "tenz@email.com", medicalRecordList.get(2)));
        pEntList.add(new PersonEntity(6L, "Roger", "Boyd", addressEntity,
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(3)));
        pEntList.add(new PersonEntity(7L, "Felicia", "Boyd", addressEntity,
                "841-874-6544", "jaboyd@email.com", medicalRecordList.get(4)));
    }

    public static ChildDTO childDTO = new ChildDTO();
    public static OpsPersonDTO child1 = new OpsPersonDTO("Tenley", "Boyd",
            "8 years old", "1509 Culver St", "841-874-6512");
    public static OpsPersonDTO child2 = new OpsPersonDTO("Roger", "Boyd",
            "20 months old", "1509 Culver St", "841-874-6512");
    public static List<OpsPersonDTO> childList = Arrays.asList(child1, child2);
    public static List<String> adultList = Arrays.asList("John Boyd",
            "Jacob Boyd", "Felicia Boyd");
    static {
        childDTO.setAddress("1509 Culver St");
        childDTO.setChildList(childList);
        childDTO.setAdultList(adultList);
    }

    /**
     * OPS#1 FIRESTATION ------------------------------------------------
     *
     * @throws Exception
     */
    @Test
    public void givenAPersonEntityList_whenConvert_thenReturnCoveredByStationPersonLisr()
            throws Exception {
        LOGGER.info("Start test: OPS#1 firestation mapping");
        // GIVEN
        // WHEN
        List<OpsPersonDTO> opsPersonDTOs = opsPersonMapping.convertToCoveredByStationPerson(pEntList);
        // THEN
        assertThat(opsPersonDTOs.get(3).getFirstName()).isEqualTo("Roger");
        assertThat(opsPersonDTOs.get(3).getAge()).isEqualTo("20 months old");
    }

    /**
     * OPS#2 CHILD ALERT
     * ---------------------------------------------------------
     *
     * @throws Exception
     */
    @Test
    public void givenTheListOfPersonsOfAnAddress_whenConvert_thenReturnChilAlert()
            throws Exception {
        LOGGER.info("Start test: ChildDTO mapping");
        // GIVEN
        // WHEN
        ChildDTO mappedChildAlert = opsPersonMapping.create(pEntList,
                "1509 Culver St");
        // THEN
        assertThat(mappedChildAlert.getAddress()).isEqualTo("1509 Culver St");
        assertThat(mappedChildAlert.getAdultList()).isEqualTo(adultList);
        assertThat(mappedChildAlert.getChildList().get(0).getAge())
                .isEqualTo("8 years old");
        assertThat(mappedChildAlert.getChildList().get(1).getAge())
                .isEqualTo("20 months old");
    }

}
