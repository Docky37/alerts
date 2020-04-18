package com.safetynet.alerts.controller_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.controller.OpsPersonController;
import com.safetynet.alerts.model.CountOfPersons;
import com.safetynet.alerts.model.CoveredPerson;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.OpsPersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(OpsPersonController.class)
public class OpsPersonControllerTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private MockMvc mockMVC;

    @MockBean
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
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
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

    public static List<String> eMailList = new ArrayList<>();

    static {
        for (Person person : personList) {
            if (person.getCity() == "Culver") {
                eMailList.add(person.getEmail());
            }
        }
    }

    public static CountOfPersons countOfPersons = new CountOfPersons(8,3,11);
    
    public static List<String> phoneList = new ArrayList<>();

    static {
        for (Person person : personList) {
            if (person.getCity() == "Culver") {
                eMailList.add(person.getEmail());
            }
        }
    }

    @Test // GET (OPS 1 list of persons covered by the given station)
    public void givenAFireStation_whenGetListOfPersonsCoveredByStation_thenReturnList()
            throws Exception {
        LOGGER.info("Start test: GET - findPhoneListByFireStation");
        given(opsPersonService.findListOfPersonsCoveredByStation(anyString()))
                .willReturn(coveredPersonList);
        mockMVC.perform(
                MockMvcRequestBuilders.get("/firestation/stationNumber/3"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // GET (OPS 1 list of persons covered by the given station)
    public void givenAFireStation_whenGetAdultAndChildCountByStation_thenReturnCount()
            throws Exception {
        LOGGER.info("Start test: GET - findPhoneListByFireStation");
        given(opsPersonService.countPersonsCoveredByStation(anyString()))
                .willReturn(countOfPersons);
        mockMVC.perform(
                MockMvcRequestBuilders.get("/firestation/count/3"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // GET (OPS 3 phoneAlert by station)
    public void givenAFireStation_whenGetPhoneListByFireStation_thenReturnPhoneListOfStation()
            throws Exception {
        LOGGER.info("Start test: GET - findPhoneListByFireStation");
        given(opsPersonService.findAllPhoneListByStation(anyString()))
                .willReturn(phoneList);
        mockMVC.perform(MockMvcRequestBuilders.get("/phoneAlert/station"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // GET (OPS 7 communityEmail by city)
    public void givenACity_whenGetEMailListByCity_thenReturnEmailList()
            throws Exception {
        LOGGER.info("Start test: GET - findEmailListBy City");
        given(opsPersonService.findAllMailByCity(anyString()))
                .willReturn(eMailList);
        mockMVC.perform(MockMvcRequestBuilders.get("/communityEmail/city"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }
}
