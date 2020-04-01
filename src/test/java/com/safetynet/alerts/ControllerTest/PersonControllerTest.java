package com.safetynet.alerts.ControllerTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private PersonService personService;

    public static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person(1, "John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(2, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(3, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    @Test // GET
    public void givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        given(personService.findByLastNameAndFirstName(anyString(),
                anyString()))
                        .willReturn(new Person(1, "John", "Boyd",
                                "1509 Culver St", "Culver", "97451",
                                "841-874-6512", "jaboyd@email.com"));

        mockMVC.perform(
                MockMvcRequestBuilders.get("/Person/lastName/firstName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value("Boyd"))
                .andExpect(jsonPath("$.firstName").value("John"));

    }

    @Test // GET
    public void givenAStrangerToFind_whenGetPersonByLastNameAndFirstName_thenNotFoundException()
            throws Exception {
        given(personService.findByLastNameAndFirstName(anyString(),
                anyString())).willThrow(new PersonNotFoundException());

        mockMVC.perform(
                MockMvcRequestBuilders.get("/Person/lastName/firstName"))
                .andExpect(status().isNotFound());

    }

    @Test // GET
    public void givenAllPersonToFind_whenGetPerson_thenReturnListOfAllPerson()
            throws Exception {

        given(personService.findAll()).willReturn(personList);

        mockMVC.perform(MockMvcRequestBuilders.get("/Person"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
        // .andExpect(jsonPath("$.firstName").value("John"))
    }

    @Test // POST
    public void givenAPersonToAdd_whenPostPerson_thenReturnIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Person personToAdd = new Person(4, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");
        given(personService.addPerson(any(Person.class))).willReturn(personToAdd);

        mockMVC.perform(MockMvcRequestBuilders.post("/Person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // PUT
    public void givenAPersonToUpdate_whenPutPerson_thenReturnIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Person personToUpdate = personList.get(2);
        personToUpdate.setEmail("updated@email.com");
        personToUpdate.setPhone("0123456789");
        given(personService.updatePerson(any(Person.class)))
                .willReturn(personToUpdate);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/Person/" + personToUpdate.getLastName() + "/"
                        + personToUpdate.getFirstName())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(personToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // .andExpect(MockMvcResultMatchers.content()
                // .string("Article created."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test // DELETE
    public void givenAPersonToDelete_whenDeletePerson_thenReturnIsOk()
            throws Exception {
        Person personToDelete = personList.get(2);
        mockMVC.perform(MockMvcRequestBuilders
                .delete("/Person/" + personToDelete.getLastName() + "/"
                        + personToDelete.getFirstName()))
                .andExpect(status().isOk());
    }

}
