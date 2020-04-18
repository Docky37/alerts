package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.sql.Date;
import java.time.LocalDate;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.CountOfPersons;
import com.safetynet.alerts.model.CoveredPerson;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.OpsPersonService;
import com.safetynet.alerts.utils.PersonMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(OpsPersonService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpsPersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonMapping personMapping;

    private OpsPersonService opsPersonService;

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
    }

    public static List<AddressFireStation> addressFireStList = new ArrayList<>();
    static {
        addressFireStList
                .add(new AddressFireStation(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressFireStation(2L, "29_15th_St", "2"));
    }

    public static List<PersonEntity> pEntList = new ArrayList<>();

    static {
        pEntList.add(
                new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "jaboyd@email.com", null));
        pEntList.add(
                new PersonEntity(2L, "Jacob", "Boyd", addressFireStList.get(1),
                        "841-874-6513", "drk@email.com", null));
        pEntList.add(
                new PersonEntity(4L, "Tenley", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "tenz@email.com", null));
    }

    public static List<PersonEntity> pEnt2List = new ArrayList<>();

    static {
        pEnt2List.add(
                new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "jaboyd@email.com", null));
        pEnt2List.add(
                new PersonEntity(4L, "Tenley", "Boyd", addressFireStList.get(0),
                        "841-874-6515", "tenz@email.com", null));
    }

    public static List<CoveredPerson> coveredPersonList = new ArrayList<>();

    static {
        coveredPersonList.add(new CoveredPerson(1L, "John", "Boyd",
                "1509 Culver St", "841-874-6512"));
        coveredPersonList.add(new CoveredPerson(2L, "Johnathan", "Barrack",
                "29 15th St", "841-874-6513"));
        coveredPersonList.add(new CoveredPerson(3L, "Tenley", "Boyd",
                "1509 Culver St", "841-874-6512"));
    }

    public static CountOfPersons countOfPersons = new CountOfPersons(8, 3, 11);

    @Before
    public void SetUp() {
        opsPersonService = new OpsPersonService(personRepository,
                personMapping);
    }

    // OPS #1 ENDPOINT -------------------------------------------------------
    // Find all person covered by the given station.
    @Test
    @Tag("Test1a-PersonListByStation")
    @DisplayName("A1. Given a fireStation, when findAllPersonsByStation,"
            + " then returns the list of persons covered by this station.")
    public void a1_givenAStation_whenFindAllPersonByStation_thenReturnList()
            throws Exception {
        // GIVEN
        String station = "3";
        given(personRepository.findByAddressIdStation(station))
                .willReturn(pEnt2List);
        given(personMapping.convertToCoveredByStationPerson(
                Mockito.<PersonEntity>anyList())).willReturn(coveredPersonList);
        // WHEN
        List<CoveredPerson> resultingCoveredPersonList = opsPersonService
                .findListOfPersonsCoveredByStation(station);
        // THEN
        assertThat(resultingCoveredPersonList.size()).isEqualTo(3);
        assertThat(resultingCoveredPersonList.get(0))
                .isEqualTo(coveredPersonList.get(0));
    }

    @Test
    @Tag("Test1b-PersonListByStation")
    @DisplayName("A2. Given a fireStation, when countPersonsCoveredByStation,"
            + " then returns the count of persons covered by this station.")
    public void a2_givenAStation_whenCountPersonByStation_thenReturnCount()
            throws Exception {
        // GIVEN
        String station = "3";
        Date compareDate = Date.valueOf(LocalDate.now().minusYears(18));
        given(personRepository.countByAddressIdStation(station))
                .willReturn(11L);
        given(personRepository.countAdultsByAddressIdStation(station,
                compareDate)).willReturn(8L);
        // WHEN
        CountOfPersons resultingCountOfPersons = opsPersonService
                .countPersonsCoveredByStation(station);
        // THEN
        assertThat(resultingCountOfPersons.getAdultCount()).isEqualTo(countOfPersons.getAdultCount());
        assertThat(resultingCountOfPersons.getChildCount()).isEqualTo(countOfPersons.getChildCount());
        assertThat(resultingCountOfPersons.getTotal()).isEqualTo(countOfPersons.getTotal());
        
    }

    // OPS #3 ENDPOINT -------------------------------------------------------
    // GET("/phoneAlert/{station}")>>Find all person phones by station
    @Test
    @Tag("TestB-PhoneListByStation")
    @DisplayName("B. Given a fireStation, when findAllPhonesByStation,"
            + " then returns the phone list of inhabitants covered by this station.")
    public void b_givenAStation_whenFindAllPhoneByStation_thenReturnListOfPhones()
            throws Exception {
        // GIVEN
        String station = "3";
        List<String> phoneList = new ArrayList<>();
        given(personRepository.findByAddressIdStation(station))
                .willReturn(pEnt2List);
        // WHEN
        phoneList = opsPersonService.findAllPhoneListByStation(station);
        // THEN
        assertThat(phoneList.size()).isEqualTo(2);
        assertThat(phoneList.get(0)).isEqualTo(personList.get(0).getPhone());
        assertThat(phoneList.get(1)).isEqualTo(personList.get(3).getPhone());
    }

    // OPS#7 - GET ("/communityEmail/{city}")>> Find all person eMails by city
    @Test
    @Tag("TestA-EMailListByCity")
    @DisplayName("C. Given a City, when findAllEmailsByCity,"
            + " then returns the eMail list of the city.")
    public void c_givenACity_whenFindAllEmailByCity_thenReturnListOfEmails()
            throws Exception {
        // GIVEN
        String city = "Culver";
        List<String> eMailList = new ArrayList<>();
        given(personRepository.findByAddressIdCity(city)).willReturn(pEntList);
        // WHEN
        eMailList = opsPersonService.findAllMailByCity(city);
        // THEN
        assertThat(eMailList.size()).isEqualTo(3);
        assertThat(eMailList.get(0)).isEqualTo(personList.get(0).getEmail());
        assertThat(eMailList.get(1)).isEqualTo(personList.get(1).getEmail());
        assertThat(eMailList.get(2)).isEqualTo(personList.get(3).getEmail());
    }

}
