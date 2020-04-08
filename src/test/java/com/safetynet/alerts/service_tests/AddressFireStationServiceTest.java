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
import static org.mockito.Mockito.verify;

import com.safetynet.alerts.controller.AddressFireStationNotFoundException;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.service.AddressFireStationService;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressFireStationService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressFireStationServiceTest {

    @MockBean
    private AddressFireStationRepository addressFireStationRepository;

    private AddressFireStationService addressFireStationService;

    public static List<AddressFireStation> addressFireStList = new ArrayList<>();

    static {
        addressFireStList
                .add(new AddressFireStation(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressFireStation(2L, "29_15th_St", "2"));
        addressFireStList.add(new AddressFireStation(3L, "834 Binoc Ave", "3"));
    }

    @Before
    public void SetUp() {
        addressFireStationService = new AddressFireStationService(
                addressFireStationRepository);
    }

    // POST >>> CREATE (Add a list of AddressFireStation)
    @Test
    @Tag("TestA-CreateAListOfAddressFireStations")
    @DisplayName("A. Given a list of AddressFireStations to Add, when POST list,"
            + " then AddressFireStations are created.")
    public void a_givenAListOfAddressFireStationToAdd_whenPostList_thenAddressFireStationsAreCreated()
            throws Exception {
        // GIVEN
        given(addressFireStationRepository
                .saveAll(Mockito.<AddressFireStation>anyList()))
                        .willReturn(addressFireStList);
        // WHEN
        List<AddressFireStation> addedList = addressFireStationService
                .addListFireStations(addressFireStList);
        // THEN
        assertThat(addedList.size()).isEqualTo(3);

    }

    // GET ("/AddressFireStation")>>> READ (Find all AddressFireStations)
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
        List<AddressFireStation> foundList = addressFireStationService
                .findAll();
        // THEN
        assertThat(foundList.get(0).getAddress()).isEqualTo("1509 Culver St");
        assertThat(foundList.get(1).getStation()).isEqualTo("2");
    }

    // GET ("/AddressFireStation/{lastName}/{FirstName}") >>> READ (Find a
    // AddressFireStation)
    @Test
    @Tag("TestC-FindAAddressFireStation")
    @DisplayName("3.Given a AddressFireStation to find, when search AddressFireStation by last name & firstname,"
            + " then returns the AddressFireStation.")
    public void c1_givenAAddressFireStationToFind_whenGetAddressFireStationByLastNameAndFirstName_thenReturnTheAddressFireStation()
            throws Exception, AddressFireStationNotFoundException {
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(addressFireStList.get(2));

        AddressFireStation addressFireStation = addressFireStationService
                .findByAddress(addressFireStList.get(2).getAddress());
        assertThat(addressFireStation.getAddress()).isEqualTo("834 Binoc Ave");
        assertThat(addressFireStation.getStation()).isEqualTo("3");

    }

    // GET ("/AddressFireStation/{lastName}/{FirstName}") >>> READ (Try to find
    // an Unknown
    // AddressFireStation)
    @Test(expected = AddressFireStationNotFoundException.class)
    @Tag("TestC2-FindAAddressFireStation")
    @DisplayName("3.Given a stranger to find, when search AddressFireStation by last name & firstname,"
            + " then returns null.")
    public void c2_givenAnUnknownAdress_whenFindByAddress_thenNotFoundException()
            throws Exception, AddressFireStationNotFoundException {
        // GIVEN
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(null);
        // WHEN
        AddressFireStation addressFireStation = addressFireStationService
                .findByAddress("3rd Unknow Ave");
        // THEN
        assertThat(addressFireStation).isNull();
    }

    // POST >>> CREATE (Add a new AddressFireStation)
    @Test
    @Tag("TestD-CreateAAddressFireStation")
    @DisplayName("4. Given a AddressFireStation to add, when POST the AddressFireStation, "
            + "then a new AddressFireStation is created.")
    public void d_givenAAddressFireStationToAdd_whenPostAddressFireStation_thenAddressFireStationIsCreated()
            throws Exception {
        // GIVEN
        AddressFireStation addressFireStationToAdd = new AddressFireStation(4L,
                "644 Gershwin Cir", "1");

        given(addressFireStationRepository.save(any(AddressFireStation.class)))
                .willReturn(addressFireStationToAdd);
        // WHEN
        AddressFireStation addedAddressFireStation = addressFireStationService
                .addAddressFireStation(addressFireStationToAdd);
        // THEN
        assertThat(addedAddressFireStation.getId()).isEqualTo(4);
        assertThat(addedAddressFireStation.getAddress())
                .isEqualTo("644 Gershwin Cir");
        assertThat(addedAddressFireStation.getStation()).isEqualTo("1");

    }

    @Test // PUT >>> UPDATE (
    @Tag("TestE-UpdateAAddressFireStation")
    @DisplayName("5. Given a AddressFireStation to update, when save the AddressFireStation,"
            + " then this AddressFireStation is updated.")
    public void e_givenAnAddressToUpdate_whenUpdate_thenReturnUpdatedAddressFireStation()
            throws Exception {
        // GIVEN
        AddressFireStation addressFireStToUpdate = addressFireStList.get(2);
        AddressFireStation updatedAddressFireStation = addressFireStList.get(2);
        updatedAddressFireStation.setStation("2");
        given(addressFireStationRepository
                .findByAddress(addressFireStToUpdate.getAddress()))
                        .willReturn(addressFireStToUpdate);
        given(addressFireStationRepository.save(any(AddressFireStation.class)))
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
    @DisplayName("6. Given a AddressFireStation to delete, when delete the AddressFireStation,"
            + " then find this AddressFireStation returns null.")
    public void f_givenAAddressFireStationToDelete_whenDeleteAddressFireStation_thenReturnAddressFireStationDoesNotExist()
            throws Exception {
        // GIVEN
        AddressFireStation addressFireStToDelete = addressFireStList.get(2);
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

}
