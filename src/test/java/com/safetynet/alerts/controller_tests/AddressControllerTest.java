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
import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.controller.AddressController;
import com.safetynet.alerts.controller.AddressNotFoundException;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.service.IAddressService;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private IAddressService addressService;

    public static List<AddressEntity> addressEntityList = new ArrayList<>();

    static {
        addressEntityList.add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressEntityList.add(new AddressEntity(2L, "29_15th_St", "2"));
        addressEntityList.add(new AddressEntity(3L, "834 Binoc Ave", "3"));
    }

    public static List<AddressDTO> addressDTOList = new ArrayList<>();

    static {
        addressDTOList.add(new AddressDTO("1509 Culver St", "3"));
        addressDTOList.add(new AddressDTO("29_15th_St", "2"));
        addressDTOList.add(new AddressDTO("834 Binoc Ave", "3"));
    }

    @Test // POST - Successful creation
    public void givenAnAddressFireStationListToAdd_whenPost_thenReturnsIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        given(addressService.addListAddress(Mockito.<AddressDTO>anyList()))
                .willReturn(addressDTOList);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestation/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressDTOList)))
                .andExpect(status().isCreated());
    }

    @Test // POST
    public void givenAnAddressFireStationListToAdd_whenPost_thenReturnsNoContent()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        given(addressService.addListAddress(Mockito.<AddressDTO>anyList()))
                .willReturn(new ArrayList<AddressDTO>());

        mockMVC.perform(MockMvcRequestBuilders.post("/firestation/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressDTOList)))
                .andExpect(status().isOk());
    }

    @Test // POST
    public void givenAnAddressAdd_whenPost_thenReturnsIsCreated()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AddressDTO addressDTOToAdd = new AddressDTO("644 Gershwin Cir", "1");
        given(addressService.addAddress(any(AddressDTO.class)))
                .willReturn(addressDTOToAdd);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressDTOToAdd)))
                .andExpect(status().isCreated());
    }

    @Test // POST
    public void givenAnAddressFireStationToAdd_whenPost_thenReturnsNoContent()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AddressEntity addressFireStToAdd = new AddressEntity(4L,
                "644 Gershwin Cir", "1");
        given(addressService.addAddress(any(AddressDTO.class)))
                .willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addressFireStToAdd)))
                .andExpect(status().isBadRequest());
    }

    @Test // GET
    public void givenAnAddressFireStToFind_whenGet_thenReturnsTheAddressFireStation()
            throws Exception, AddressNotFoundException {
        given(addressService.findByAddress(anyString()))
                .willReturn(new AddressDTO("644 Gershwin Cir", "1"));

        mockMVC.perform(MockMvcRequestBuilders.get("/firestation/address"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.address").value("644 Gershwin Cir"))
                .andExpect(jsonPath("$.station").value("1"));

    }

    @Test // GET
    public void givenUnknownAddress_whenGet_thenNotFoundException()
            throws Exception, AddressNotFoundException {
        given(addressService.findByAddress(anyString()))
                .willThrow(new AddressNotFoundException());

        mockMVC.perform(MockMvcRequestBuilders.get("/firestation/address"))
                .andExpect(status().isNotFound());

    }

    @Test // GET
    public void givenAllAddressFireStationToFind_whenFindAll_thenReturnsListOfAll()
            throws Exception {
        given(addressService.findAll()).willReturn(addressDTOList);
        mockMVC.perform(MockMvcRequestBuilders.get("/firestation"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers
                        .content().contentType("application/json"));
    }

    @Test // PUT
    public void givenAnAddressFireStToUpdate_whenPut_thenReturnsIsNoContent()
            throws Exception, AddressNotFoundException {

        ObjectMapper mapper = new ObjectMapper();
        AddressDTO addressDTOToUpdate = addressDTOList.get(2);
        addressDTOToUpdate.setStation("4");
        given(addressService.updateAddress(anyString(),(any(AddressDTO.class))))
                .willReturn(addressDTOToUpdate);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/firestation/" + addressDTOToUpdate.getAddress())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(addressDTOToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test // PUT
    public void givenAnAddressFireStToUpdate_whenChangeAddress_thenReturnsIs501()
            throws Exception, AddressNotFoundException {

        ObjectMapper mapper = new ObjectMapper();
        AddressDTO addressDTOToUpdate = addressDTOList.get(2);
        addressDTOToUpdate.setAddress("Change Address");
        given(addressService.updateAddress(anyString(),(any(AddressDTO.class))))
                .willReturn(null);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/firestation/" + addressDTOToUpdate.getAddress())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(addressDTOToUpdate));

        mockMVC.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotImplemented())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test // DELETE
    public void givenAnAddressFireStationToDelete_whenDelete_thenReturnsIsOk()
            throws Exception {
        AddressDTO addressFireStToDelete = addressDTOList.get(1);
        given(addressService.deleteAnAddress(anyString()))
                .willReturn(addressFireStToDelete);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/firestation/" + addressFireStToDelete.getAddress()))
                .andExpect(status().isOk());
    }

    @Test // DELETE
    public void givenAnUnknownAddressFireStationToDelete_whenDelete_thenReturnsIsNotFound()
            throws Exception {
        AddressDTO addressFireStToDelete = addressDTOList.get(1);
        given(addressService.deleteAnAddress(anyString())).willReturn(null);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/firestation/" + addressFireStToDelete.getAddress()))
                .andExpect(status().isNotFound());
    }

}
