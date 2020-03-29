package com.safetynet.alerts.ControllerTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private PersonService personService;

    @Test
    public void getPerson_shouldReturnPerson() throws Exception {
        given(personService.getPersonDetails(anyString()))
                .willReturn(new Person(1, "John", "Boyd", "1509 Culver St",
                        "Culver", "97451", "841-874-6512", "jaboyd@email.com"));

        mockMVC.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/Person/lastName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.lastname").value("Boyd"));

    }

    @Test
    public void getPersonByFirstname_shouldReturnPerson() throws Exception {
        given(personService.getPersonDetails(anyString()))
                .willReturn(new Person(1, "John", "Boyd", "1509 Culver St",
                        "Culver", "97451", "841-874-6512", "jaboyd@email.com"));

        mockMVC.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/Person/firstName"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"))
                .andExpect(jsonPath("$.firstname").value("John"));

    }

    @Test
    public void getAllPerson_shouldReturnListOfPerson() throws Exception {
        given(personService.getPersonDetails(anyString()))
                .willReturn(new Person(1, "John", "Boyd", "1509 Culver St",
                        "Culver", "97451", "841-874-6512", "jaboyd@email.com"));

        mockMVC.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/Person/firstName"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"))
                .andExpect(jsonPath("$.firstname").value("John"));

    }

}
