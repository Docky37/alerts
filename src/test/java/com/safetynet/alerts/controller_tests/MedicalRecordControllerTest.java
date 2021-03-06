package com.safetynet.alerts.controller_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.format.DateTimeFormatter;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.MedicalRecordDTO;
import com.safetynet.alerts.controller.MedicalRecordController;
import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.service.IMedicalRecordService;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private IMedicalRecordService medicalRecordService;

    static DateTimeFormatter Formatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    public static List<MedicalRecordDTO> medicalRecordList = new ArrayList<>();

    static {
        medicalRecordList.add(new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                new String[] { "nillacilan" }));
        medicalRecordList.add(new MedicalRecordDTO(
                "Jacob", "Boyd", "03/06/1989", new String[] {
                        "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" },
                new String[] {}));
        medicalRecordList.add(new MedicalRecordDTO("Tenley", "Boyd",
                "03/06/1989", new String[] {}, new String[] { "peanut" }));

    }

    @Test // GET
    public void givenAMedicalRecordToFind_whenGetMedicalRecordByLastNameAndFirstName_thenReturnTheMedicalRecord()
            throws Exception {
        LOGGER.info("Start test: GET - findByLastNameAndFirstName");
        given(medicalRecordService.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(
                        new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                                new String[] { "aznol:350mg",
                                        "hydrapermazol:100mg" },
                                new String[] { "nillacilan" }));

        mockMVC.perform(
                MockMvcRequestBuilders.get("/medicalRecord/lastName/firstName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.birthdate").value("03/06/1984"))
                .andExpect(jsonPath("$.firstName").value("John"));

    }

    @Test // POST
    public void givenAMedicalRecordListToAdd_whenPostList_thenReturnIsCreated()
            throws Exception {
        LOGGER.info("Start test: POST - A list of MedicalRecords");
        ObjectMapper mapper = new ObjectMapper();
        given(medicalRecordService
                .addListMedicalRecord(Mockito.<MedicalRecordDTO>anyList()))
                        .willReturn(medicalRecordList);

        mockMVC.perform(MockMvcRequestBuilders.post("/medicalRecord/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecordList)))
                .andExpect(status().isCreated());
    }

    @Test // POST
    public void givenAMedicalRecordListToAdd_whenPostList_thenReturnNoContent()
            throws Exception {
        LOGGER.info("Start test: POST - A list of MedicalRecords");
        ObjectMapper mapper = new ObjectMapper();
        given(medicalRecordService
                .addListMedicalRecord(Mockito.<MedicalRecordDTO>anyList()))
                        .willReturn(new ArrayList<MedicalRecordDTO>());

        mockMVC.perform(MockMvcRequestBuilders.post("/medicalRecord/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecordList)))
                .andExpect(status().isOk());
    }

    @Test // GET
    public void givenAStrangerToFind_whenGetMedicalRecordByLastNameAndFirstName_thenNotFoundException()
            throws Exception {
        LOGGER.info("Start test: GET - findByLastNameAndFirstName an unknown");
        given(medicalRecordService.findByLastNameAndFirstName(anyString(),
                anyString())).willThrow(new MedicalRecordNotFoundException());

        mockMVC.perform(
                MockMvcRequestBuilders.get("/medicalRecord/lastName/firstName"))
                .andExpect(status().isNotFound());

    }

    @Test // GET
    public void givenAllMedicalRecordToFind_whenGetMedicalRecord_thenReturnListOfAllMedicalRecord()
            throws Exception {
        LOGGER.info("Start test: GET - findAll");
        given(medicalRecordService.findAll()).willReturn(medicalRecordList);

        mockMVC.perform(MockMvcRequestBuilders.get("/medicalRecord"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // POST
    public void givenAMedicalRecordToAdd_whenPostMedicalRecord_thenReturnIsCreated()
            throws Exception {
        LOGGER.info("Start test: POST - Add one MedicalRecord");
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecordDTO personToAdd = new MedicalRecordDTO("Roger", "Boyd",
                "09/06/2017", new String[] {}, new String[] {});
        given(medicalRecordService
                .addMedicalRecord(any(MedicalRecordDTO.class)))
                        .willReturn(personToAdd);

        mockMVC.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // POST - Orphan
    public void givenAnUnknownMedicalRecordToAdd_whenPostMedicalRecord_thenReturnNoContent()
            throws Exception {
        LOGGER.info("Start test: POST - Add one medicalRecord");
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecordDTO personToAdd = new MedicalRecordDTO("Roger", "Boyd",
                "09/06/2017", new String[] {}, new String[] {});
        given(medicalRecordService
                .addMedicalRecord(any(MedicalRecordDTO.class)))
                        .willReturn(new MedicalRecordDTO("", "", "", new String[] {},
                                new String[] {}));

        mockMVC.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personToAdd)))
                .andExpect(status().isBadRequest());
    }

    @Test // PUT
    public void givenAMedicalRecordToUpdate_whenPutMedicalRecord_thenReturnIsCreated()
            throws Exception {
        LOGGER.info("Start test: PUT - Update a medicalRecord");
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecordDTO medicalRecordToUpdate = medicalRecordList.get(2);
        // medicalRecordToUpdate.setEmail("updated@email.com");
        // medicalRecordToUpdate.setPhone("0123456789");
        given(medicalRecordService.updateMedicalRecord(anyString(), anyString(),
                any(MedicalRecordDTO.class))).willReturn(medicalRecordToUpdate);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/medicalRecord/" + medicalRecordToUpdate.getLastName()
                        + "/" + medicalRecordToUpdate.getFirstName())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(medicalRecordToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test // PUT
    public void givenAnUnknownMedicalRecordToUpdate_whenPutMedicalRecord_thenReturnNotFound()
            throws Exception {
        LOGGER.info("Start test: PUT - Update a medicalRecord");
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecordDTO medicalRecordToUpdate = medicalRecordList.get(2);
        given(medicalRecordService.updateMedicalRecord(anyString(), anyString(),
                any(MedicalRecordDTO.class))).willReturn(null);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/medicalRecord/" + medicalRecordToUpdate.getLastName()
                        + "/" + medicalRecordToUpdate.getFirstName())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(medicalRecordToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotImplemented());
    }

    @Test // DELETE
    public void givenAMedicalRecordToDelete_whenDeleteMedicalRecord_thenReturnIsOk()
            throws Exception {
        LOGGER.info("Start test: DELETE - Remove a medicalRecord");
        MedicalRecordDTO medicalRecordToDelete = medicalRecordList.get(2);
        given(medicalRecordService.deleteAMedicalRecord(anyString(),
                anyString())).willReturn(medicalRecordToDelete);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/medicalRecord/" + medicalRecordToDelete.getLastName()
                        + "/" + medicalRecordToDelete.getFirstName()))
                .andExpect(status().isOk());
    }

    @Test // DELETE
    public void givenAnUnknownMedicalRecordToDelete_whenDeleteMedicalRecord_thenReturnNotFound()
            throws Exception {
        LOGGER.info("Start test: DELETE - Remove one person");
        MedicalRecordDTO medicalRecordToDelete = medicalRecordList.get(2);
        given(medicalRecordService.deleteAMedicalRecord(anyString(),
                anyString())).willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/medicalRecord/" + medicalRecordToDelete.getLastName()
                        + "/" + medicalRecordToDelete.getFirstName()))
                .andExpect(status().isNotFound());
    }

}
