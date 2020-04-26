package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.Household;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.OpsMedicalService;
import com.safetynet.alerts.utils.MedicalMapping;
import com.safetynet.alerts.utils.PersonMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(OpsMedicalService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpsMedicalServiceTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonMapping personMapping;

    @MockBean
    private MedicalMapping medicalMapping;

    private OpsMedicalService OpsMedicalService;

    public static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person(1L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(2L, "Johnathan", "Barrack", "29 15th St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(3L, "Doc", "Spring",
                "1515 Java St - Beverly Hills", "Los Angeles", "90211",
                "123-456-7890", "Doc.Spring@email.com"));
        personList.add(new Person(4L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6515", "tenz@email.com"));
        personList.add(new Person(5L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(6L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(7L, "Felicia", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
    }

    public static List<AddressFireStation> addressFireStList = new ArrayList<>();
    static {
        addressFireStList
                .add(new AddressFireStation(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressFireStation(2L, "29_15th_St", "2"));
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
        p.add(new PersonEntity(2L, "Jacob", "Boyd", addressFireStList.get(1),
                "841-874-6513", "drk@email.com", medicalRecordList.get(1)));
        p.add(new PersonEntity(4L, "Tenley", "Boyd", addressFireStList.get(0),
                "841-874-6512", "tenz@email.com", medicalRecordList.get(2)));
        p.add(new PersonEntity(6L, "Roger", "Boyd", addressFireStList.get(0),
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(3)));
        p.add(new PersonEntity(7L, "Felicia", "Boyd", addressFireStList.get(0),
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
    }
    static {
        mappedFireAlert.setAddressFireStation(addressFireStList.get(0));
        mappedFireAlert.setPersonList(myFireList);
    }

    @Before
    public void SetUp() {
        OpsMedicalService = new OpsMedicalService(personRepository,
                medicalMapping);
    }

    // OPS #4 - FIRE ALERT ---------------------------------------------------
    @Test
    @Tag("Test-Fire Alert")
    @DisplayName("Given an Address, when search a list of Persons by address,"
            + " then returns the Household object.")
    public void ops4_givenAnAddress_WhenFindFireListByaddress_thenReturnFireAlertObject()
            throws Exception {
        LOGGER.info("Start test: OPS #4 Fire Alert");
        // GIVEN
        String address = "1509 Culver St";
        Household household = new Household();
        given(personRepository.findByAddressIdAddress(address)).willReturn(p);
        given(medicalMapping.mapFire(p, address)).willReturn(mappedFireAlert);
        // WHEN
        household = OpsMedicalService.fireByAddress(address);
        // THEN
        assertThat(household.getAddressFireStation().toString()).isEqualTo(
                "FireStations [id=1, address=1509 Culver St, city=Culver, zip code=97451, station=3]");
        assertThat(household.getPersonList().toString()).isEqualTo(
                "[PersonInfoDTO [firstName=John, lastName=Boyd, age=36 years old, medications=[aznol:350mg, hydrapermazol:100mg], allergies=[nillacilan], phone=841-874-6512], PersonInfoDTO [firstName=Jacob, lastName=Boyd, age=31 years old, medications=[pharmacol:5000mg, terazine:10mg, noznazol:250mg], allergies=[], phone=841-874-6513], PersonInfoDTO [firstName=Tenley, lastName=Boyd, age=31 years old, medications=[], allergies=[peanut], phone=841-874-6512]]");
    }

}
