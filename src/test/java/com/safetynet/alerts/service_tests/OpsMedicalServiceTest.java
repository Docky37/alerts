package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.HouseholdDTO;
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.IOpsMedicalService;
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

    private IOpsMedicalService OpsMedicalService;

    public static List<PersonDTO> personList = new ArrayList<>();
    static {
        personList.add(new PersonDTO(1L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO(2L, "Johnathan", "Barrack", "29 15th St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new PersonDTO(3L, "Doc", "Spring",
                "1515 Java St - Beverly Hills", "Los Angeles", "90211",
                "123-456-7890", "Doc.Spring@email.com"));
        personList.add(new PersonDTO(4L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6515", "tenz@email.com"));
        personList.add(new PersonDTO(5L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new PersonDTO(6L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO(7L, "Felicia", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
    }

    public static List<AddressEntity> addressFireStList = new ArrayList<>();
    static {
        addressFireStList
                .add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressEntity(2L, "29_15th_St", "2"));
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

    public static List<PersonEntity> pEnt = new ArrayList<>();
    static {
        pEnt.add(new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(0)));
        pEnt.add(new PersonEntity(2L, "John", "Boyd", addressFireStList.get(0),
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(3)));
    }

    public static HouseholdDTO mappedFireAlert = new HouseholdDTO();
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

    public static List<PersonInfoDTO> myPersonInfoDTOList = new ArrayList<>();
    static {
        myPersonInfoDTOList.add(new PersonInfoDTO(p.get(0).getFirstName(),
                p.get(0).getLastName(), "36 years old",
                p.get(0).getMedRecId().getMedications(),
                p.get(0).getMedRecId().getAllergies(), p.get(0).getPhone()));
        myPersonInfoDTOList.add(new PersonInfoDTO(p.get(0).getFirstName(),
                p.get(4).getLastName(), "19 months old",
                p.get(4).getMedRecId().getMedications(),
                p.get(4).getMedRecId().getAllergies(), p.get(4).getPhone()));
    }

    static {
        mappedFireAlert.setAddressEntity(addressFireStList.get(0));
        mappedFireAlert.setPersonList(myFireList);
    }
    
    static List<String> stationList = new ArrayList<>();
    static {
        stationList.add("3");
        stationList.add("2");
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
            + " then returns the HouseholdDTO object.")
    public void ops4_givenAnAddress_WhenFindFireListByaddress_thenReturnFireAlertObject()
            throws Exception {
        LOGGER.info("Start test: OPS #4 Fire Alert");
        // GIVEN
        String address = "1509 Culver St";
        HouseholdDTO householdDTO = new HouseholdDTO();
        given(personRepository.findByAddressIdAddress(address)).willReturn(p);
        given(medicalMapping.mapFire(p, address)).willReturn(mappedFireAlert);
        // WHEN
        householdDTO = OpsMedicalService.fireByAddress(address);
        // THEN
        assertThat(householdDTO.getAddressEntity().toString()).isEqualTo(
                "FireStations [id=1, address=1509 Culver St, city=Culver, zip code=97451, station=3]");
        assertThat(householdDTO.getPersonList().toString()).isEqualTo(
                "[PersonInfoDTO [firstName=John, lastName=Boyd, age=36 years old, medications=[aznol:350mg, hydrapermazol:100mg], allergies=[nillacilan], phone=841-874-6512], PersonInfoDTO [firstName=Jacob, lastName=Boyd, age=31 years old, medications=[pharmacol:5000mg, terazine:10mg, noznazol:250mg], allergies=[], phone=841-874-6513], PersonInfoDTO [firstName=Tenley, lastName=Boyd, age=31 years old, medications=[], allergies=[peanut], phone=841-874-6512]]");
    }

    // OPS #5 - FlOOD  ---------------------------------------------------
    @Test
    @Tag("Test-Flood")
    @DisplayName("Given a Stion List, when search a list of Persons by address,"
            + " then returns the HouseholdDTO object.")
    public void ops5_givenAStationList_WhenFindFireListByaddress_thenReturnFireAlertObject()
            throws Exception {
        LOGGER.info("Start test: OPS #5 Flood");
        // GIVEN
        List<FloodDTO> floodDTOList = new ArrayList<>();
        given(personRepository.findAllGroupByAddress(stationList)).willReturn(p);
        given(medicalMapping.mapFlood(p)).willReturn(floodDTOList);
        // WHEN
        floodDTOList = OpsMedicalService.floodByStation(stationList);
        // THEN
        verify(personRepository).findAllGroupByAddress(stationList);
        verify(medicalMapping).mapFlood(p);
    }

    // OPS #6 - PERSON INFO ---------------------------------------------------
    @Test
    @Tag("Test-PersonInfo")
    @DisplayName("Given a Address, when search a list of Persons by address,"
            + " then returns the HouseholdDTO object.")
    public void ops6_givenAPersonToFind_WhenFindByName_thenReturnAPersonInfoDTOList()
            throws Exception {
        LOGGER.info("Start test: OPS #6 PersonInfo");
        // GIVEN
        given(personRepository.findByFirstNameAndLastName(anyString(), anyString())).willReturn(pEnt);
        LOGGER.info("myPersonInfoDTOList = {}",myPersonInfoDTOList);
        given(medicalMapping.mapPersonInfoList(Mockito.<PersonEntity>anyList())).willReturn(myPersonInfoDTOList);
        // WHEN
        List<PersonInfoDTO> personInfoDTOList = OpsMedicalService.personInfo("john", "boyd");
        // THEN
        verify(medicalMapping).mapPersonInfoList(Mockito.<PersonEntity>anyList());
        assertThat(personInfoDTOList.toString()).isEqualTo(
                "[PersonInfoDTO [firstName=John, lastName=Boyd, age=36 years old, medications=[aznol:350mg, hydrapermazol:100mg], allergies=[nillacilan], phone=841-874-6512], PersonInfoDTO [firstName=John, lastName=Boyd, age=19 months old, medications=[tetracyclaz:650mg], allergies=[xilliathal], phone=841-874-6544]]");
    }
}
