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
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressRepository;
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
    private AddressRepository addressRepository;

    public static List<PersonDTO> personList = new ArrayList<>();
    static {
        personList.add(new PersonDTO("John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public static List<AddressEntity> addressFireStList = new ArrayList<>();
    static {
        addressFireStList
                .add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressEntity(2L, "29_15th_St", "2"));
        addressFireStList.add(new AddressEntity(3L, "834 Binoc Ave", "3"));
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

    @Test
    public void givenAPerson_whenConvert_thenReturnPersonEntity()
            throws Exception {
        LOGGER.info("Start test: PersonDTO mapping to PersonneEntity");
        // GIVEN
        given(addressRepository.findByAddress(anyString()))
                .willReturn(addressFireStList.get(0));
        // WHEN
        PersonEntity pEnt = personMapping
                .convertToPersonEntity(personList.get(0));
        // THEN
        assertThat(pEnt.getAddressFireSt())
                .isEqualTo(pEntList.get(0).getAddressFireSt());
        assertThat(pEnt.getFirstName())
                .isEqualTo(pEntList.get(0).getFirstName());
    }

    @Test
    public void givenAListOfPersonEntity_whenConvert_thenReturnPersonList()
            throws Exception {
        LOGGER.info("Start test: PersonEntityList mapping to PersonDTO");
        // GIVEN
        // WHEN
        List<PersonDTO> mappedList = personMapping.convertToPerson(pEntList);
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
