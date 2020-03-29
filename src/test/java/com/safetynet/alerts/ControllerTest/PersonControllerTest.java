package com.safetynet.alerts.ControllerTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.alerts.Controller.PersonController;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @Test
    public void getPerson_shouldReturnPerson() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders.get("http://localhost:8080/Persons/Boyd"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
   //     .andExpect(jsonPath (expression: "lastName").value(expectedValue:"Boyd"));
        
    }
}
