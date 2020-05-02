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

import com.safetynet.alerts.controller.AddressFireStationNotFoundException;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.service.AddressFireStationService;
import com.safetynet.alerts.service.IAddressFireStationService;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressFireStationService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressFireStationServiceTest {

    @MockBean
    private AddressFireStationRepository addressFireStationRepository;

    private IAddressFireStationService addressFireStationService;

    public static List<AddressEntity> addressFireStList = new ArrayList<>();

    static {
        addressFireStList
                .add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressEntity(2L, "29_15th_St", "2"));
        addressFireStList.add(new AddressEntity(3L, "834 Binoc Ave", "3"));
    }

    @Before
    public void SetUp() {
        addressFireStationService = new AddressFireStationService(
                addressFireStationRepository);
    }

    // POST >>> CREATE (Add a list of AddressEntity)
    @Test
    @Tag("TestA-CreateAListOfAddressFireStations")
    @DisplayName("A. Given a list of AddressFireStations to Add, when POST list,"
            + " then AddressFireStations are created.")
    public void a_givenAListOfAddressFireStationToAdd_whenPostList_thenAddressFireStationsAreCreated()
            throws Exception {
        // GIVEN
        given(addressFireStationRepository
                .saveAll(Mockito.<AddressEntity>anyList()))
                        .willReturn(addressFireStList);
        // WHEN
        List<AddressEntity> addedList = addressFireStationService
                .addListFireStations(addressFireStList);
        // THEN
        assertThat(addedList.size()).isEqualTo(3);

    }

    // GET ("/AddressEntity")>>> READ (Find all AddressFireStations)
    @Test
    @Tag("TestB-FindAllAddressFireStations")
    @DisplayName("2. Given AddressFireStations in database, when findAll,"
            + " then returns the list of all AddressFireStations.")
    public void b_givenAllAddressFireStationsToFind_whenFindAll_thenReturnListOfAllAddressFireStations()
            throws Exception {
        // GIVEN
        given(addressFireStationRepository.findAll())
                .willReturn(addressFireStList);
        // WHEN
        List<AddressEntity> foundList = addressFireStationService
                .findAll();
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
    public void c1_givenAAddressFireStationToFind_whenGetAddressFireStationByLastNameAndFirstName_thenReturnTheAddressFireStation()
            throws Exception, AddressFireStationNotFoundException {
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(addressFireStList.get(2));

        AddressEntity addressEntity = addressFireStationService
                .findByAddress(addressFireStList.get(2).getAddress());
        assertThat(addressEntity.getAddress()).isEqualTo("834 Binoc Ave");
        assertThat(addressEntity.getStation()).isEqualTo("3");

    }

    // GET ("/AddressEntity/{lastName}/{FirstName}") >>> READ (Try to find
    // an Unknown
    // AddressEntity)
    @Test(expected = AddressFireStationNotFoundException.class)
    @Tag("TestC2-FindAAddressFireStation")
    @DisplayName("3.Given a stranger to find, when search AddressEntity by last name & firstname,"
            + " then returns null.")
    public void c2_givenAnUnknownAdress_whenFindByAddress_thenNotFoundException()
            throws Exception, AddressFireStationNotFoundException {
        // GIVEN
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(null);
        // WHEN
        AddressEntity addressEntity = addressFireStationService
                .findByAddress("3rd Unknow Ave");
        // THEN
        assertThat(addressEntity).isNull();
    }

    // POST >>> CREATE (Add a new AddressEntity)
    @Test
    @Tag("TestD-CreateAAddressFireStation")
    @DisplayName("4. Given a AddressEntity to add, when POST the AddressEntity, "
            + "then a new AddressEntity is created.")
    public void d1_givenAAddressFireStationToAdd_whenPostAddressFireStation_thenAddressFireStationIsCreated()
            throws Exception {
        // GIVEN
        AddressEntity addressFireStationToAdd = new AddressEntity(4L,
                "644 Gershwin Cir", "1");

        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(null);
        given(addressFireStationRepository.save(any(AddressEntity.class)))
                .willReturn(addressFireStationToAdd);
        // WHEN
        AddressEntity addedAddressFireStation = addressFireStationService
                .addAddressFireStation(addressFireStationToAdd);
        // THEN
        assertThat(addedAddressFireStation.getId()).isEqualTo(4);
        assertThat(addedAddressFireStation.getAddress())
                .isEqualTo("644 Gershwin Cir");
        assertThat(addedAddressFireStation.getStation()).isEqualTo("1");
    }

    // POST >>> CREATE (Add a new AddressEntity)
    @Test
    @Tag("TestD-CreateAAddressFireStation")
    @DisplayName("4. Given a AddressEntity to add, when POST the AddressEntity, "
            + "then a new AddressEntity is created.")
    public void d2_givenAnExistingAddressFireStationToAdd_whenPost_thenReturnsNull()
            throws Exception {
        // GIVEN
        AddressEntity addedAddressFireStation = addressFireStList.get(0);
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(addedAddressFireStation);
        // WHEN
        addedAddressFireStation = addressFireStationService
                .addAddressFireStation(addedAddressFireStation);
        // THEN
        verify(addressFireStationRepository,never()).save(any(AddressEntity.class));
    }

    @Test // PUT >>> UPDATE (
    @Tag("TestE-UpdateAAddressFireStation")
    @DisplayName("5. Given a AddressEntity to update, when save the AddressEntity,"
            + " then this AddressEntity is updated.")
    public void e_givenAnAddressToUpdate_whenUpdate_thenReturnUpdatedAddressFireStation()
            throws Exception {
        // GIVEN
        AddressEntity addressFireStToUpdate = addressFireStList.get(2);
        AddressEntity updatedAddressFireStation = addressFireStList.get(2);
        updatedAddressFireStation.setStation("2");
        given(addressFireStationRepository
                .findByAddress(addressFireStToUpdate.getAddress()))
                        .willReturn(addressFireStToUpdate);
        given(addressFireStationRepository.save(any(AddressEntity.class)))
                .willReturn(updatedAddressFireStation);

        // WHEN
        updatedAddressFireStation = addressFireStationService
                .updateAddress(addressFireStToUpdate);
        // THEN
        assertThat(updatedAddressFireStation.getAddress())
                .isEqualTo(addressFireStToUpdate.getAddress());
        assertThat(updatedAddressFireStation.getStation())
                .isEqualTo(addressFireStToUpdate.getStation());
    }

    @Test // DELETE
    @Tag("TestF-DeleteAAddressFireStation")
    @DisplayName("6. Given a AddressEntity to delete, when delete the AddressEntity,"
            + " then find this AddressEntity returns null.")
    public void f1_givenAAddressFireStationToDelete_whenDeleteAddressFireStation_thenReturnAddressFireStationDoesNotExist()
            throws Exception {
        // GIVEN
        AddressEntity addressFireStToDelete = addressFireStList.get(2);
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(addressFireStList.get(2));
        // WHEN
        addressFireStationService.deleteAnAddress(
                addressFireStToDelete.getAddress());
        // THEN
        verify(addressFireStationRepository).findByAddress(
                addressFireStToDelete.getAddress());
        verify(addressFireStationRepository).deleteById(any(Long.class));
    }

    @Test // DELETE
    @Tag("TestF-DeleteAAddressFireStation")
    @DisplayName("6. Given an unknown AddressEntity to delete, when delete, returns null.")
    public void f2_givenAnUnknownAddressFireStationToDelete_whenDelete_thenReturnNull()
            throws Exception {
        // GIVEN
        AddressEntity addressFireStToDelete = addressFireStList.get(2);
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(null);
        // WHEN
        addressFireStationService.deleteAnAddress(
                addressFireStToDelete.getAddress());
        // THEN
        verify(addressFireStationRepository).findByAddress(
                addressFireStToDelete.getAddress());
        verify(addressFireStationRepository,never()).deleteById(any(Long.class));
    }

}
