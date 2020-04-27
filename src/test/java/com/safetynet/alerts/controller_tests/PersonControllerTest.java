package com.safetynet.alerts.controller_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.service.IPersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private IPersonService personService;

    public static List<PersonDTO> personList = new ArrayList<>();

    static {
        personList.add(new PersonDTO(1L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO(2L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new PersonDTO(3L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    @Test // POST
    public void givenAPersonListToAdd_whenPostList_thenReturnIsCreated()
            throws Exception {
        LOGGER.info("Start test: POST - Add a list of PersonDTO");
        ObjectMapper mapper = new ObjectMapper();
        given(personService.addListPersons(Mockito.<PersonDTO>anyList()))
                .willReturn(personList);

        mockMVC.perform(MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personList)))
                .andExpect(status().isCreated());
    }


    @Test // POST
    public void givenAPersonListToAdd_whenPostList_thenReturnNoContent()
            throws Exception {
        LOGGER.info("Start test: POST - Add a list of PersonDTO");
        ObjectMapper mapper = new ObjectMapper();
        given(personService.addListPersons(Mockito.<PersonDTO>anyList()))
                .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personList)))
                .andExpect(status().isNoContent());
    }
    @Test // GET
    public void givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        LOGGER.info("Start test: GET - findByLastNameAndFirstName");
        given(personService.findByLastNameAndFirstName(anyString(),
                anyString()))
                        .willReturn(new PersonDTO(1L, "John", "Boyd",
                                "1509 Culver St", "Culver", "97451",
                                "841-874-6512", "jaboyd@email.com"));

        mockMVC.perform(
                MockMvcRequestBuilders.get("/person/lastName/firstName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value("Boyd"))
                .andExpect(jsonPath("$.firstName").value("John"));

    }

    @Test // GET
    public void givenAStrangerToFind_whenGetPersonByLastNameAndFirstName_thenNotFoundException()
            throws Exception {
        LOGGER.info("Start test: GET - findByLastNameAndFirstName an unknown");
        given(personService.findByLastNameAndFirstName(anyString(),
                anyString())).willThrow(new PersonNotFoundException());

        mockMVC.perform(
                MockMvcRequestBuilders.get("/person/lastName/firstName"))
                .andExpect(status().isNotFound());

    }

    @Test // GET
    public void givenAllPersonToFind_whenGetPerson_thenReturnListOfAllPerson()
            throws Exception {
        LOGGER.info("Start test: GET - findAll");
        given(personService.findAll()).willReturn(personList);

        mockMVC.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // POST
    public void givenAPersonToAdd_whenPostPerson_thenReturnNoContent()
            throws Exception {
        LOGGER.info("Start test: POST - Add one person");
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO personToAdd = new PersonDTO(4L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");
        given(personService.addPerson(any(PersonDTO.class)))
                .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personToAdd)))
                .andExpect(status().isNoContent());
    }

    @Test // POST
    public void givenAPersonToAdd_whenPostPerson_thenReturnIsCreated()
            throws Exception {
        LOGGER.info("Start test: POST - Add one person");
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO personToAdd = new PersonDTO(4L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");
        given(personService.addPerson(any(PersonDTO.class)))
                .willReturn(personToAdd);

        mockMVC.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // PUT
    public void givenAPersonToUpdate_whenPutPerson_thenReturnIsCreated()
            throws Exception {
        LOGGER.info("Start test: PUT - Update a person");
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO personToUpdate = personList.get(2);
        personToUpdate.setEmail("updated@email.com");
        personToUpdate.setPhone("0123456789");
        given(personService.updatePerson(any(PersonDTO.class)))
                .willReturn(personToUpdate);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/person/" + personToUpdate.getLastName() + "/"
                        + personToUpdate.getFirstName())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(personToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // .andExpect(MockMvcResultMatchers.content()
        // .string("PersonDTO updated."))
        // .andDo(MockMvcResultHandlers.print())
    }

    @Test // PUT
    public void givenAnUnknownToUpdate_whenPutPerson_thenNotFound()
            throws Exception {
        LOGGER.info("Start test: PUT - Update a person");
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO personToUpdate = personList.get(2);
        personToUpdate.setEmail("updated@email.com");
        personToUpdate.setPhone("0123456789");
        given(personService.updatePerson(any(PersonDTO.class)))
                .willReturn(null);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/person/" + personToUpdate.getLastName() + "/"
                        + personToUpdate.getFirstName())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(personToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        // .andExpect(MockMvcResultMatchers.content()
        // .string("PersonDTO updated."))
        // .andDo(MockMvcResultHandlers.print())
    }

    @Test // DELETE
    public void givenAPersonToDelete_whenDeletePerson_thenReturnIsOk()
            throws Exception {
        LOGGER.info("Start test: DELETE - Remove one person");
        PersonDTO personToDelete = personList.get(2);
        given(personService.deleteAPerson(anyString(), anyString()))
                .willReturn(personToDelete);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/person/" + personToDelete.getLastName() + "/"
                        + personToDelete.getFirstName()))
                .andExpect(status().isOk());
    }

    @Test // DELETE
    public void givenAnUnknownToDelete_whenDeletePerson_thenReturnNotFound()
            throws Exception {
        LOGGER.info("Start test: DELETE - Remove unknown person");
        PersonDTO personToDelete = personList.get(2);
        given(personService.deleteAPerson(anyString(), anyString()))
                .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/person/" + personToDelete.getLastName() + "/"
                        + personToDelete.getFirstName()))
                .andExpect(status().isNotFound());
    }

}
