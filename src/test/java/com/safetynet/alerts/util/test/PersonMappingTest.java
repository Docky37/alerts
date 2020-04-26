package com.safetynet.alerts.util.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.CoveredPerson;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.utils.PersonMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonMapping.class)
public class PersonMappingTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private PersonMapping personMapping;

    @MockBean
    private AddressFireStationRepository addressFireStationRepository;

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
                .add(new AddressFireStation(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressFireStation(2L, "29_15th_St", "2"));
        addressFireStList.add(new AddressFireStation(3L, "834 Binoc Ave", "3"));
    }

    public static List<PersonEntity> pEntList = new ArrayList<>();
    static {
        pEntList.add(
                new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "jaboyd@email.com", null));
        pEntList.add(
                new PersonEntity(3L, "Tenley", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "tenz@email.com", null));
    }

    public static List<CoveredPerson> coveredPersonList = new ArrayList<>();
    static {
        coveredPersonList.add(new CoveredPerson("John", "Boyd",
                "1509 Culver St", "841-874-6512"));
        coveredPersonList.add(new CoveredPerson("Johnathan", "Barrack",
                "29 15th St", "841-874-6513"));
        coveredPersonList.add(new CoveredPerson("Tenley", "Boyd",
                "1509 Culver St", "841-874-6512"));
    }

    @Test
    public void givenAListOfPerson_whenConvert_thenReturnPersonEntityList()
            throws Exception {
        LOGGER.info("Start test: Person list mapping to PersonneEntity list");
        // GIVEN
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(addressFireStList.get(0));
        // WHEN
        List<PersonEntity> mappedList = personMapping
                .convertToPersonEntity(personList);
        // THEN
        assertThat(mappedList.get(0).getAddressFireSt())
                .isEqualTo(pEntList.get(0).getAddressFireSt());
        assertThat(mappedList.get(0).getFirstName())
                .isEqualTo(pEntList.get(0).getFirstName());
        assertThat(mappedList.get(1).getAddressFireSt())
                .isEqualTo(pEntList.get(1).getAddressFireSt());
        assertThat(mappedList.get(1).getFirstName())
                .isEqualTo(pEntList.get(1).getFirstName());
    }

    @Test
    public void givenAListOfPersonEntity_whenConvert_thenReturnPersonList()
            throws Exception {
        LOGGER.info("Start test: PersonEntityList mapping to Person");
        // GIVEN
        // WHEN
        List<Person> mappedList = personMapping.convertToPerson(pEntList);
        // THEN
        assertThat(mappedList.get(0).getAddress())
                .isEqualTo(personList.get(0).getAddress());
        assertThat(mappedList.get(0).getFirstName())
                .isEqualTo(personList.get(0).getFirstName());
        assertThat(mappedList.get(1).getAddress())
                .isEqualTo(personList.get(1).getAddress());
        assertThat(mappedList.get(1).getFirstName())
                .isEqualTo(personList.get(1).getFirstName());
    }

    // OPS #1 ENDPOINT -------------------------------------------------------
    @Test
    public void givenAListOfPersonEntity_whenConvert_thenReturnCoveredPersonList()
            throws Exception {
        LOGGER.info("Start test: PersonEntityList mapping to Person");
        // GIVEN
        // WHEN
        List<CoveredPerson> mappedList = personMapping
                .convertToCoveredByStationPerson(pEntList);
        // THEN
        assertThat(mappedList.get(0).getAddress())
                .isEqualTo(personList.get(0).getAddress());
        assertThat(mappedList.get(0).getFirstName())
                .isEqualTo(personList.get(0).getFirstName());
        assertThat(mappedList.get(1).getAddress())
                .isEqualTo(personList.get(1).getAddress());
        assertThat(mappedList.get(1).getFirstName())
                .isEqualTo(personList.get(1).getFirstName());
    }
}
