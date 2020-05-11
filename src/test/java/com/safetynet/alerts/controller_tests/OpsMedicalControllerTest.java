package com.safetynet.alerts.controller_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.HouseholdDTO;
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.controller.OpsMedicalController;
import com.safetynet.alerts.service.IOpsMedicalService;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.MedicalRecordEntity;
import com.safetynet.alerts.model.PersonEntity;

@RunWith(SpringRunner.class)
@WebMvcTest(OpsMedicalController.class)
public class OpsMedicalControllerTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private IOpsMedicalService opsMedicalService;

    public static List<PersonDTO> personList = new ArrayList<>();
    static {
        personList.add(new PersonDTO("John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO("Felicia", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
    }

    public static List<AddressEntity> addressFireStList = new ArrayList<>();
    static {
        addressFireStList.add(new AddressEntity(1L, "1509 Culver St", "3"));
    }

    public static List<MedicalRecordEntity> medicalRecordList = new ArrayList<>();
    static {
        medicalRecordList
                .add(new MedicalRecordEntity(1L, "John", "Boyd", "03/06/1984",
                        new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                        new String[] { "nillacilan" }));
        medicalRecordList.add(new MedicalRecordEntity(2L, "Felicia", "Boyd",
                "01/08/1986", new String[] { "tetracyclaz:650mg" },
                new String[] { "xilliathal" }));
    }

    public static List<PersonEntity> p = new ArrayList<>();
    static {
        p.add(new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                "841-874-6512", "jaboyd@email.com", medicalRecordList.get(0)));
        p.add(new PersonEntity(2L, "Felicia", "Boyd", addressFireStList.get(0),
                "841-874-6544", "jaboyd@email.com", medicalRecordList.get(1)));
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
    }
    static {
        mappedFireAlert.setAddressDTO(
                new AddressDTO(addressFireStList.get(0).getAddress(),
                        addressFireStList.get(0).getStation()));
        mappedFireAlert.setPersonList(myFireList);
    }

    @Test // OPS 4 FIRE
    public void ops4_givenAnAddress_whenGetFire_thenReturnHouseHoldDTO()
            throws Exception {
        LOGGER.info("Start test: OPS #4 fire by address");
        given(opsMedicalService.fireByAddress(anyString()))
                .willReturn(mappedFireAlert);
        mockMVC.perform(
                MockMvcRequestBuilders.get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // OPS 5 FLOOD
    public void ops5_givenAStationList_whenGetFlood_thenReturnAFloodDTO()
            throws Exception {
        LOGGER.info("Start test: OPS #5 flood by stationList");
        List<FloodDTO> floodDTOList = new ArrayList<>();
        given(opsMedicalService.floodByStation(Mockito.<String>anyList()))
                .willReturn(floodDTOList);
        mockMVC.perform(
                MockMvcRequestBuilders.get("/flood/stations?stationList=3"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // OPS 6 PERSON INFO
    public void ops6_givenAName_whenGetPersonInfo_thenReturnAPersonInfoDTOList()
            throws Exception {
        LOGGER.info("Start test: OPS #6 personInfoList by stationList");
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
        given(opsMedicalService.personInfo(anyString(), anyString()))
                .willReturn(personInfoDTOList);
        mockMVC.perform(MockMvcRequestBuilders
                .get("/personInfo?firstName=firstName&lastName=lastName"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

}
