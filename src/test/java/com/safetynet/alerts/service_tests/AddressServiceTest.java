package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.controller.AddressNotFoundException;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.repositery.AddressRepository;
import com.safetynet.alerts.service.AddressService;
import com.safetynet.alerts.service.IAddressService;
import com.safetynet.alerts.utils.AddressMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressServiceTest {

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AddressMapping addressMapping;

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

    @Before
    public void SetUp() {
        addressService = new AddressService(addressRepository, addressMapping);
    }

    // POST >>> CREATE (Add a list of AddressEntity)
    @Test
    @Tag("TestA-CreateAListOfAddressDTO")
    @DisplayName("A. Given a list of AddressDTO to Add, when POST list,"
            + " then AddressDTO are created.")
    public void a_givenAListOfAddressDTOToAdd_whenPostList_thenAddressDTOAreCreated()
            throws Exception {
        // GIVEN
        given(addressRepository.findByAddress(anyString())).willReturn(null);
        given(addressMapping.convertToAddressEntity(any(AddressDTO.class)))
                .willReturn(addressEntityList.get(0), addressEntityList.get(1),
                        addressEntityList.get(2));
        given(addressRepository.save(any(AddressEntity.class)))
        .willReturn(addressEntityList.get(0), addressEntityList.get(1),
                addressEntityList.get(2));
        given(addressMapping.convertToAddressDTO(any(AddressEntity.class)))
        .willReturn(addressDTOList.get(0), addressDTOList.get(1),
                addressDTOList.get(2));

        // WHEN
        List<AddressDTO> addedList = addressService
                .addListAddress(addressDTOList);
        // THEN
        assertThat(addedList.size()).isEqualTo(3);
        assertThat(addedList).isEqualTo(addressDTOList);

    }

    // GET ("/AddressEntity")>>> READ (Find all Address)
    @Test
    @Tag("TestB-FindAllAddress")
    @DisplayName("2. Given Address in database, when findAll,"
            + " then returns the list of all Address.")
    public void b_givenAllAddressToFind_whenFindAll_thenReturnListOfAllAddress()
            throws Exception {
        // GIVEN
        given(addressRepository.findAll()).willReturn(addressEntityList);
        given(addressMapping
                .convertToAddressDTO(Mockito.<AddressEntity>anyList()))
                        .willReturn(addressDTOList);
        // WHEN
        List<AddressDTO> foundList = addressService.findAll();
        // THEN
        assertThat(foundList.get(0).getAddress()).isEqualTo("1509 Culver St");
        assertThat(foundList.get(1).getStation()).isEqualTo("2");
    }

    // GET ("/AddressEntity/{lastName}/{FirstName}") >>> READ (Find a
    // AddressEntity)
    @Test
    @Tag("TestC-FindAAddressFireStation")
    @DisplayName("3.Given a AddressEntity to find, when search AddressEntity by last name & firstname,"
            + " then returns the AddressEntity.")
    public void c1_givenAnAddressDTOToFind_whenGetAddress_thenReturnTheAddressDTO()
            throws Exception, AddressNotFoundException {
        given(addressRepository.findByAddress(anyString()))
                .willReturn(addressEntityList.get(2));
        given(addressMapping.convertToAddressDTO(any(AddressEntity.class)))
                .willReturn(addressDTOList.get(2));
        AddressDTO addressDTO = addressService
                .findByAddress(addressDTOList.get(2).getAddress());
        assertThat(addressDTO.getAddress()).isEqualTo("834 Binoc Ave");
        assertThat(addressDTO.getStation()).isEqualTo("3");

    }

    // GET ("/AddressEntity/{lastName}/{FirstName}") >>> READ (Try to find
    // an Unknown
    // AddressEntity)
    @Test(expected = AddressNotFoundException.class)
    @Tag("TestC2-FindAAddress")
    @DisplayName("3.Given a stranger to find, when search AddressEntity by last name & firstname,"
            + " then returns null.")
    public void c2_givenAnUnknownAdress_whenFindByAddress_thenNotFoundException()
            throws Exception, AddressNotFoundException {
        // GIVEN
        given(addressRepository.findByAddress(anyString())).willReturn(null);
        // WHEN
        AddressDTO addressDTO = addressService.findByAddress("3rd Unknow Ave");
        // THEN
        assertThat(addressDTO).isNull();
    }

    // POST >>> CREATE (Add a new AddressEntity)
    @Test
    @Tag("TestD-CreateAnAddress")
    @DisplayName("4. Given a AddressEntity to add, when POST the AddressEntity, "
            + "then a new AddressEntity is created.")
    public void d1_givenAAddressToAdd_whenPostAddress_thenAddressIsCreated()
            throws Exception {
        // GIVEN
        AddressDTO addressDTOToAdd = new AddressDTO("644 Gershwin Cir", "1");
        AddressEntity addedAddressEntity = new AddressEntity(4L,
                "644 Gershwin Cir", "1");
        given(addressRepository.findByAddress(anyString())).willReturn(null);
        given(addressMapping.convertToAddressEntity(any(AddressDTO.class)))
                .willReturn(addedAddressEntity);
        given(addressRepository.save(any(AddressEntity.class)))
                .willReturn(addedAddressEntity);
        given(addressMapping.convertToAddressDTO(addedAddressEntity))
                .willReturn(addressDTOToAdd);
        // WHEN
        AddressDTO addedAddressDTO = addressService.addAddress(addressDTOToAdd);
        // THEN
        assertThat(addedAddressDTO.getAddress()).isEqualTo("644 Gershwin Cir");
        assertThat(addedAddressDTO).isEqualTo(addressDTOToAdd);
    }

    // POST >>> CREATE (Add a new AddressEntity)
    @Test
    @Tag("TestD-TryToCreateAnExistingAddressDTO")
    @DisplayName("4. Given an existing AddressDTO to add, when POST, "
            + "then returns null.")
    public void d2_givenAnExistingAddressDTOToAdd_whenPost_thenReturnsNull()
            throws Exception {
        // GIVEN
        AddressEntity addedAddressEntity = addressEntityList.get(0);
        given(addressRepository.findByAddress(anyString()))
                .willReturn(addedAddressEntity);
        // WHEN
        AddressDTO addedAddressDTO = addressService
                .addAddress(addressDTOList.get(0));
        // THEN
        assertThat(addedAddressDTO).isNull();
        verify(addressRepository, never()).save(any(AddressEntity.class));
    }

    @Test // PUT >>> UPDATE (
    @Tag("TestE-UpdateAnAddress")
    @DisplayName("5. Given a AddressEntity to update, when save the AddressEntity,"
            + " then this AddressEntity is updated.")
    public void e_givenAnAddressToUpdate_whenUpdate_thenReturnUpdatedAddressFireStation()
            throws Exception, AddressNotFoundException {
        // GIVEN
        AddressDTO addressDTOToUpdate = addressDTOList.get(2);
        AddressEntity updatedAddressEntity = addressEntityList.get(2);
        addressDTOToUpdate.setStation("2");
        updatedAddressEntity.setStation("2");
        given(addressRepository.findByAddress(anyString()))
                .willReturn(updatedAddressEntity);
        given(addressRepository.save(any(AddressEntity.class)))
                .willReturn(updatedAddressEntity);
        given(addressMapping.convertToAddressDTO(updatedAddressEntity))
                .willReturn(addressDTOToUpdate);

        // WHEN
        AddressDTO updatedAddressDTO = addressService.updateAddress(
                addressDTOToUpdate.getAddress(), addressDTOToUpdate);
        // THEN
        assertThat(updatedAddressDTO.getAddress())
                .isEqualTo(addressDTOToUpdate.getAddress());
        assertThat(updatedAddressDTO.getStation())
                .isEqualTo(addressDTOToUpdate.getStation());
    }

    @Test // DELETE
    @Tag("TestF-DeleteAnAddress")
    @DisplayName("6. Given a AddressEntity to delete, when delete the AddressEntity,"
            + " then find this AddressEntity returns null.")
    public void f1_givenAAddressFireStationToDelete_whenDeleteAddressFireStation_thenReturnAddressFireStationDoesNotExist()
            throws Exception {
        // GIVEN
        given(addressRepository.findByAddress(anyString()))
                .willReturn(addressEntityList.get(2));
        // WHEN
        addressService.deleteAnAddress(addressDTOList.get(2).getAddress());
        // THEN
        verify(addressRepository)
                .findByAddress(addressEntityList.get(2).getAddress());
        verify(addressRepository).deleteById(any(Long.class));
    }

    @Test // DELETE
    @Tag("TestF-DeleteAAddressFireStation")
    @DisplayName("6. Given an unknown AddressEntity to delete, when delete, returns null.")
    public void f2_givenAnUnknownAddressFireStationToDelete_whenDelete_thenReturnNull()
            throws Exception {
        // GIVEN
        given(addressRepository.findByAddress(anyString())).willReturn(null);
        // WHEN
        addressService.deleteAnAddress(addressDTOList.get(2).getAddress());
        // THEN
        verify(addressRepository)
                .findByAddress(addressEntityList.get(2).getAddress());
        verify(addressRepository, never()).deleteById(any(Long.class));
    }

}
