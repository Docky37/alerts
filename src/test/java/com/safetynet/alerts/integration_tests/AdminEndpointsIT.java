package com.safetynet.alerts.integration_tests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.Tag;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.MedicalRecordEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminEndpointsIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    // Data creation for tests
    // ADDRESSES ----------------------------------------------------
    public static List<AddressEntity> addressEntityList = new ArrayList<>();
    static {
        addressEntityList
                .add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressEntityList
                .add(new AddressEntity(2L, "29 15th St", "2"));
        addressEntityList
                .add(new AddressEntity(3L, "834 Binoc Ave", "3"));
    }
    // PERSONS ------------------------------------------------------
    public static List<PersonDTO> personList = new ArrayList<>();
    static {
        personList.add(new PersonDTO("John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new PersonDTO("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }
    // MEDICAL RECORDS -----------------------------------------------
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
                "03/06/1989", new String[] {}, new String[] { "peanut" }));

    }

    // -------------------------------------------------------------------------------

    @Test // POST
    @Tag("Test_SaveAllAddress")
    @Order(1)
    @Rollback(false)
    public void givenAnAddressFireStationListToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressEntityList)))
                .andExpect(status().isCreated());
    }

    @Test // GET
    @Tag("Test_FindAllAddress")
    @Order(2)
    @Rollback(false)
    public void givenAddressFireStationExists_whenGetAll_thenReturnsAll()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"station\":\"3\"},{\"address\":\"29 15th St\",\"city\":\"Culver\",\"zip\":\"97451\",\"station\":\"2\"},{\"address\":\"834 Binoc Ave\",\"city\":\"Culver\",\"zip\":\"97451\",\"station\":\"3\"}]"))
                .andExpect(status().isOk());
    }

    @Test // POST
    @Tag("Test_Save")
    @Order(3)
    @Rollback(false)
    public void givenAnAddressFireStationToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddressEntity addressFireStToAdd = new AddressEntity();
        addressFireStToAdd.setAddress("644 Gershwin Cir");
        addressFireStToAdd.setStation("1");
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressFireStToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // GET
    @Tag("Test_FindByAddress")
    @Order(4)
    @Rollback(false)
    public void givenAnAddressFireStationToFind_whenGetByAddress_thenReturnsIt()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/644 Gershwin Cir"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":\"97451\",\"station\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test // POST
    @Tag("Test_SaveAllPersons")
    @Order(5)
    @Rollback(false)
    public void givenAPersonListToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/person/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personList)))
                .andExpect(status().isCreated());
    }

    @Test // GET
    @Tag("Test_FindAllPersons")
    @Order(6)
    @Rollback(false)
    public void givenPersonExists_whenGetAll_thenReturnsAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"}]"))
                .andExpect(status().isOk());
    }

    @Test // POST
    @Tag("Test_SavePerson")
    @Order(7)
    @Rollback(false)
    public void givenAPersonToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO personToAdd = new PersonDTO("Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // GET
    @Tag("Test_FindByLastNameAndFirstName")
    @Order(8)
    @Rollback(false)
    public void givenAPersonToFind_whenFinByLastNameAndFirstName_thenReturnsIt()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person/Boyd/Roger"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"}"))
                .andExpect(status().isOk());
    }

    @Test // POST
    @Tag("Test_SaveAllPersons")
    @Order(9)
    @Rollback(false)
    public void givenAMedicalRecordListToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecordList)))
                .andExpect(status().isCreated());
    }

    @Test // GET
    @Tag("Test_FindAllMedicalRecords")
    @Order(10)
    @Rollback(false)
    public void givenMedicalRecordExists_whenGetAll_thenReturnsAll()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1984\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1989\",\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1989\",\"medications\":[],\"allergies\":[\"peanut\"]}]"))
                .andExpect(status().isOk());
    }

    @Test // POST
    @Tag("Test_Save_MedicalRecord")
    @Order(11)
    @Rollback(false)
    public void givenAMedicalRecordToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecordEntity medRecToAdd = new MedicalRecordEntity(0L, "Roger", "Boyd",
                "09/06/2017", new String[] {}, new String[] { "Peanuts" });
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medRecToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // GET
    @Tag("Test_Find_MedicalRecord_ByLastNameAndFirstName")
    @Order(12)
    @Rollback(false)
    public void givenAMedicalRecordToFind_whenFinByLastNameAndFirstName_thenReturnsIt()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord/Boyd/Roger"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"birthdate\":\"09/06/2017\",\"medications\":[],\"allergies\":[\"Peanuts\"]}"))
                .andExpect(status().isOk());
    }

}
