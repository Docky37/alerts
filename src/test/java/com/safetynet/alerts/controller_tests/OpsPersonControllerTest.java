package com.safetynet.alerts.controller_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.safetynet.alerts.DTO.ChildDTO;
import com.safetynet.alerts.DTO.CoveredPopulationDTO;
import com.safetynet.alerts.DTO.OpsPersonDTO;
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.controller.OpsPersonController;
import com.safetynet.alerts.service.IOpsPersonService;
import com.safetynet.alerts.model.MedicalRecord;

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
    private IOpsPersonService opsPersonService;

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
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
        personList.add(new PersonDTO(4L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6515", "tenz@email.com"));
        personList.add(new PersonDTO(5L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new PersonDTO(6L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO(7L, "Felicia", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
    }

    public static List<OpsPersonDTO> coveredPersonList = new ArrayList<>();

    static {
        coveredPersonList.add(new OpsPersonDTO("John", "Boyd", "36 years old",
                "1509 Culver St", "841-874-6512"));
        coveredPersonList.add(new OpsPersonDTO("Johnathan", "Marrack", "31 years old", 
                "29 15th St", "841-874-6513"));
        coveredPersonList.add(new OpsPersonDTO("Tenley", "Boyd", "8 years old",
                "1509 Culver St", "841-874-6512"));
    }

    public static List<String> eMailList = new ArrayList<>();

    static {
        for (PersonDTO person : personList) {
            if (person.getCity() == "Culver") {
                eMailList.add(person.getEmail());
            }
        }
    }

    public static CoveredPopulationDTO coveredPopulationDTO = new CoveredPopulationDTO(8, 3, 11, coveredPersonList);

    public static List<String> phoneList = new ArrayList<>();
    static {
        for (PersonDTO person : personList) {
            if (person.getCity() == "Culver") {
                eMailList.add(person.getEmail());
            }
        }
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
                "09/06/2017", new String[] {}, new String[] {}));
        medicalRecordList.add(new MedicalRecord(4L, "Felicia", "Boyd",
                "01/08/1986", new String[] { "tetracyclaz:650mg" },
                new String[] { "xilliathal" }));
    }
    public static ChildDTO childDTO = new ChildDTO();
    public static OpsPersonDTO child1 = new OpsPersonDTO("Tenley", "Boyd", "8 years old","1509 Culver St","841-874-6512");
    public static OpsPersonDTO child2 = new OpsPersonDTO("Roger", "Boyd", "19 months old ", "1509 Culver St","841-874-6512");
    public static List<OpsPersonDTO> childList = Arrays.asList(child1, child2);
    public static List<String> adultList = Arrays.asList("John Boyd",
            "Jacob Boyd", "Felicia Boyd");
    static {
        childDTO.setChildList(childList);
        childDTO.setAdultList(adultList);
    }


    @Test // GET (OPS 1 Adult & OpsPersonDTO counts by the given station)
    public void ops1_givenAFireStation_whenGetPopulationCoveredByStation_thenReturnCountAndList()
            throws Exception {
        LOGGER.info(
                "Start test: OPS 1 population covered by the given station");
        given(opsPersonService.populationCoveredByStation(anyString()))
                .willReturn(coveredPopulationDTO);
        mockMVC.perform(MockMvcRequestBuilders.get("/firestation/stationNumber/3"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // GET (OPS 2 childDTO)
    public void ops2_givenAnAddress_whenGetChildAlert_thenReturnChilAlertObject()
            throws Exception {
        LOGGER.info("Start test: OPS 3 chidAlert by address");
        given(opsPersonService.findListOfChildByAddress(anyString()))
                .willReturn(childDTO);
        mockMVC.perform(MockMvcRequestBuilders.get("/childAlert/address"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // GET (OPS 3 phoneAlert by station)
    public void ops3_givenAFireStation_whenGetPhoneListByFireStation_thenReturnPhoneListOfStation()
            throws Exception {
        LOGGER.info("Start test: OPS 3 phoneAlert by station");
        given(opsPersonService.findAllPhoneListByStation(anyString()))
                .willReturn(phoneList);
        mockMVC.perform(MockMvcRequestBuilders.get("/phoneAlert/station"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // GET (OPS 7 communityEmail by city)
    public void ops7_givenACity_whenGetEMailListByCity_thenReturnEmailList()
            throws Exception {
        LOGGER.info("Start test: OPS 7 communityEmail by city");
        given(opsPersonService.findAllMailByCity(anyString()))
                .willReturn(eMailList);
        mockMVC.perform(MockMvcRequestBuilders.get("/communityEmail/city"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }
}
