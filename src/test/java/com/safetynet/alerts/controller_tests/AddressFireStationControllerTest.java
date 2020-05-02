package com.safetynet.alerts.controller_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.safetynet.alerts.controller.AddressFireStationController;
import com.safetynet.alerts.controller.AddressFireStationNotFoundException;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.service.IAddressService;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressFireStationController.class)
public class AddressFireStationControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private IAddressService addressService;

    public static List<AddressEntity> addressFireStationList = new ArrayList<>();

    static {
        addressFireStationList
                .add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressFireStationList
                .add(new AddressEntity(2L, "29_15th_St", "2"));
        addressFireStationList
                .add(new AddressEntity(3L, "834 Binoc Ave", "3"));
    }

    @Test // POST
    public void givenAnAddressFireStationListToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        given(addressService.addListFireStations(Mockito.<AddressEntity>anyList()))
                        .willReturn(addressFireStationList);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressFireStationList)))
                .andExpect(status().isCreated());
    }

    @Test // POST
    public void givenAnAddressFireStationListToAdd_whenPost_thenReturnsNoContent()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        given(addressService.addListFireStations(Mockito.<AddressEntity>anyList()))
                        .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressFireStationList)))
                .andExpect(status().isNoContent());
    }

     @Test // POST
    public void givenAnAddressFireStationToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AddressEntity addressFireStToAdd = new AddressEntity(4L,
                "644 Gershwin Cir", "1");
        given(addressService
                .addAddressFireStation(any(AddressEntity.class)))
                        .willReturn(addressFireStToAdd);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressFireStToAdd)))
                .andExpect(status().isCreated());
    }

     @Test // POST
    public void givenAnAddressFireStationToAdd_whenPost_thenReturnsNoContent()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AddressEntity addressFireStToAdd = new AddressEntity(4L,
                "644 Gershwin Cir", "1");
        given(addressService
                .addAddressFireStation(any(AddressEntity.class)))
                        .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressFireStToAdd)))
                .andExpect(status().isNoContent());
    }

    @Test // GET
    public void givenAnAddressFireStToFind_whenGet_thenReturnsTheAddressFireStation()
            throws Exception, AddressFireStationNotFoundException {
        given(addressService.findByAddress(anyString())).willReturn(
                new AddressEntity(4L, "644 Gershwin Cir", "1"));

        mockMVC.perform(MockMvcRequestBuilders.get("/firestation/address"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.address").value("644 Gershwin Cir"))
                .andExpect(jsonPath("$.station").value("1"));

    }

    @Test // GET
    public void givenUnknownAddress_whenGet_thenNotFoundException()
            throws Exception, AddressFireStationNotFoundException {
        given(addressService.findByAddress(anyString()))
                .willThrow(new AddressFireStationNotFoundException());

        mockMVC.perform(MockMvcRequestBuilders.get("/firestation/address"))
                .andExpect(status().isNotFound());

    }

    @Test // GET
    public void givenAllAddressFireStationToFind_whenFindAll_thenReturnsListOfAll()
            throws Exception {
        given(addressService.findAll())
                .willReturn(addressFireStationList);
        mockMVC.perform(MockMvcRequestBuilders.get("/firestation"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // PUT
    public void givenAnAddressFireStToUpdate_whenPut_thenReturnsIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AddressEntity addressFireStToUpdate = addressFireStationList
                .get(2);
        addressFireStToUpdate.setStation("4");
        given(addressService
                .updateAddress(any(AddressEntity.class)))
                        .willReturn(addressFireStToUpdate);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/firestation/" + addressFireStToUpdate.getAddress())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(addressFireStToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test // DELETE
    public void givenAnAddressFireStationToDelete_whenDelete_thenReturnsIsOk()
            throws Exception {
        AddressEntity addressFireStToDelete = addressFireStationList
                .get(1);
        given(addressService.deleteAnAddress(anyString()))
                .willReturn(addressFireStToDelete);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/firestation/" + addressFireStToDelete.getAddress()))
                .andExpect(status().isOk());
    }

    @Test // DELETE
    public void givenAnUnknownAddressFireStationToDelete_whenDelete_thenReturnsIsNotFound()
            throws Exception {
        AddressEntity addressFireStToDelete = addressFireStationList
                .get(1);
        given(addressService.deleteAnAddress(anyString()))
                .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/firestation/" + addressFireStToDelete.getAddress()))
                .andExpect(status().isNotFound());
    }

}
