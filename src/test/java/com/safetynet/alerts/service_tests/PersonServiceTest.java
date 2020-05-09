package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.utils.PersonMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonServiceTest {

    @MockBean
    private PersonMapping personMapping;
    @MockBean
    private PersonRepository personRepository;

    private IPersonService personService;

    public static List<PersonDTO> personList = new ArrayList<>();
    static {
        personList.add(new PersonDTO("John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new PersonDTO("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new PersonDTO("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public static List<AddressEntity> addressFireStList = new ArrayList<>();
    static {
        addressFireStList.add(new AddressEntity(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressEntity(2L, "29_15th_St", "2"));
        addressFireStList.add(new AddressEntity(3L, "834 Binoc Ave", "3"));
    }

    public static List<PersonEntity> pEntList = new ArrayList<>();

    static {
        pEntList.add(
                new PersonEntity(1L, "John", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "jaboyd@email.com", null));
        pEntList.add(
                new PersonEntity(2L, "Jacob", "Boyd", addressFireStList.get(0),
                        "841-874-6513", "drk@email.com", null));
        pEntList.add(
                new PersonEntity(3L, "Tenley", "Boyd", addressFireStList.get(0),
                        "841-874-6512", "tenz@email.com", null));
    }

    @Before
    public void SetUp() {
        personService = new PersonService(personRepository, personMapping);
    }

    // POST >>> CREATE (Add a list of PersonDTO)
    @Test
    @Tag("TestA-CreateAListOfPersons")
    @DisplayName("A. Given a list of persons to Add, when POST list,"
            + " then persons are created.")
    public void a_givenAListOfPersonToAdd_whenPostList_thenPersonsAreCreated()
            throws Exception {
        // GIVEN
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null, pEntList.get(0),null, pEntList.get(1),null, pEntList.get(2));
        given(personMapping.convertToPersonEntity(any(PersonDTO.class)))
                .willReturn(pEntList.get(0), pEntList.get(1), pEntList.get(2));
        given(personRepository.save(any(PersonEntity.class)))
                .willReturn(pEntList.get(0), pEntList.get(1), pEntList.get(2));
        given(personMapping.convertToPerson(any(PersonEntity.class)))
                .willReturn(personList.get(1),personList.get(1),personList.get(2));

        // WHEN
        List<PersonDTO> addedList = personService.addListPersons(personList);
        // THEN
        assertThat(addedList.size()).isEqualTo(3);
    }

    // GET ("/PersonDTO")>>> READ (Find all persons)
    @Test
    @Tag("TestB-FindAllPersons")
    @DisplayName("2. Given persons in database, when findAll,"
            + " then returns the list of all persons.")
    public void b_givenAllPersonsToFind_whenFindAll_thenReturnListOfAllPersons()
            throws Exception {
        // GIVEN
        given(personRepository.findAll()).willReturn(pEntList);
        given(personMapping.convertToPerson(Mockito.<PersonEntity>anyList()))
                .willReturn(personList);
        // WHEN
        List<PersonDTO> foundList = personService.findAll();
        // THEN
        assertThat(foundList.get(0).getFirstName()).isEqualTo("John");
        assertThat(foundList.get(1).getFirstName()).isEqualTo("Jacob");
        assertThat(foundList.get(2).getFirstName()).isEqualTo("Tenley");
    }

    // GET ("/PersonDTO/{lastName}/{FirstName}") >>> READ (Find a PersonDTO)
    @Test
    @Tag("TestC-FindAPerson")
    @DisplayName("3.Given a person to find, when search person by last name & firstname,"
            + " then returns the person.")
    public void c1_givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws PersonNotFoundException {
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(pEntList.get(0));
        given(personMapping.convertToPerson(any(PersonEntity.class)))
                .willReturn(personList.get(0));

        PersonDTO personDTO = personService.findByLastNameAndFirstName("Boyd",
                "John");
        assertThat(personDTO.getLastName()).isEqualTo("Boyd");
        assertThat(personDTO.getFirstName()).isEqualTo("John");

    }

    // GET ("/PersonDTO/{lastName}/{FirstName}") >>> READ (Try to find an
    // Unknown
    // PersonDTO)
    @Test(expected = PersonNotFoundException.class)
    @Tag("TestC2-FindAPerson")
    @DisplayName("3.Given a stranger to find, when search person by last name & firstname,"
            + " then returns null.")
    public void c2_givenAStrangerToFind_whenFindPersonByLastNameAndFirstName_thenNotFoundException()
            throws PersonNotFoundException {
        // GIVEN
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        PersonDTO personDTO = personService
                .findByLastNameAndFirstName(anyString(), anyString());
        // THEN
        assertThat(personDTO).isNull();
    }

    // POST >>> CREATE (Add a new PersonDTO)
    @Test
    @Tag("TestD-CreateAPerson")
    @DisplayName("4. Given a person to add, when POST the person, "
            + "then a new person is created.")
    public void d1_givenAPersonToAdd_whenPostPerson_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        PersonDTO personToAdd = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(
                personToAdd.getLastName(), personToAdd.getFirstName()))
                        .willReturn(null, pEntList.get(2));
        given(personMapping.convertToPersonEntity(any(PersonDTO.class)))
                .willReturn(pEntList.get(2));
        given(personRepository.save(any(PersonEntity.class)))
                .willReturn(pEntList.get(2));
        given(personMapping.convertToPerson(any(PersonEntity.class)))
                .willReturn(personList.get(2));

        // WHEN
        PersonDTO addedPerson = personService.addPerson(personToAdd);
        // THEN
        assertThat(addedPerson.getLastName()).isEqualTo("Boyd");
        assertThat(addedPerson.getFirstName()).isEqualTo("Tenley");
    }

    // POST >>> CREATE (Add a new PersonDTO)
    @Test
    @Tag("TestD2-CreateAPerson")
    @DisplayName("4. Given a person to add, when POST the person, "
            + "then a new person is created.")
    public void d2_givenAnExistingPersonToAdd_whenPostPerson_thenReturnNull()
            throws Exception {
        // GIVEN
        PersonDTO personToAdd = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(
                personToAdd.getLastName(), personToAdd.getFirstName()))
                        .willReturn(pEntList.get(2));
        given(personMapping.convertToPersonEntity(any(PersonDTO.class)))
                .willReturn(pEntList.get(2));
        given(personRepository.save(any(PersonEntity.class)))
                .willReturn(pEntList.get(2));
        // WHEN
        personToAdd = personService.addPerson(personToAdd);
        // THEN
        verify(personRepository, never()).save(any(PersonEntity.class));
    }

    @Test // PUT >>> UPDATE (
    @Tag("TestE-UpdateAPerson")
    @DisplayName("5. Given a person to update, when save the person,"
            + " then this person is updated.")
    public void e1_givenAPersonToUpdate_whenUpdatePerson_thenReturnUpdatedPerson()
            throws Throwable {
        // GIVEN
        PersonDTO personToUpdate = personList.get(2);
        personToUpdate.setEmail("Tenley.Boyd@OpenClassrooms.com");
        personToUpdate.setPhone("0123456789");
        PersonDTO updatedPerson = personToUpdate;
        PersonEntity personInDB = pEntList.get(2);
        PersonEntity updatedPersonInDB = pEntList.get(2);
        updatedPersonInDB.setEmail("Tenley.Boyd@OpenClassrooms.com");
        updatedPersonInDB.setPhone("0123456789");
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(pEntList.get(2));
        given(personMapping.convertToPersonEntity(any(PersonDTO.class)))
                .willReturn(personInDB);
        given(personRepository.save(any(PersonEntity.class)))
                .willReturn(updatedPersonInDB);
        given(personMapping.convertToPerson(any(PersonEntity.class)))
                .willReturn(updatedPerson);
        // WHEN
        updatedPerson = personService.updatePerson("boyd", "tenley",
                personToUpdate);
        // THEN
        assertThat(updatedPerson.getFirstName())
                .isEqualTo(personToUpdate.getFirstName());
        assertThat(updatedPerson.getEmail())
                .isEqualTo(personToUpdate.getEmail());
        assertThat(updatedPerson.getPhone())
                .isEqualTo(personToUpdate.getPhone());
    }

    // PUT >>> UPDATE
    @Test(expected = PersonNotFoundException.class)  
    @Tag("TestE-UpdateAPerson")
    @DisplayName("5. Given a unknown person to update, when save the person,"
            + " then returns null.")
    public void e2_givenAnUnknownPersonToUpdate_whenUpdatePerson_thenReturnNull()
            throws Throwable {
        // GIVEN
        PersonDTO personToUpdate = personList.get(2);
        personToUpdate.setEmail("Tenley.Boyd@OpenClassrooms.com");
        personToUpdate.setPhone("0123456789");
        PersonDTO updatedPerson = personToUpdate;
        PersonEntity updatedPersonInDB = pEntList.get(2);
        updatedPersonInDB.setEmail("Tenley.Boyd@OpenClassrooms.com");
        updatedPersonInDB.setPhone("0123456789");
        given(personRepository.findByLastNameAndFirstName(
                updatedPerson.getLastName(), updatedPerson.getFirstName()))
                        .willReturn(null);

        // WHEN
        updatedPerson = personService.updatePerson("boyd", "tenley",
                personToUpdate);
        // THEN
        verify(personRepository, never()).save(any(PersonEntity.class));
    }

    @Test // DELETE
    @Tag("TestF-DeleteAPerson")
    @DisplayName("6. Given a person to delete, when delete the person,"
            + " then person is deleted.")
    public void f1_givenAPersonToDelete_whenDeletePerson_thenReturnPersonIsDeleted()
            throws PersonNotFoundException {
        // GIVEN
        PersonDTO personToDelete = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(pEntList.get(2));
        given(personMapping.convertToPerson(any(PersonEntity.class))).willReturn(personToDelete);
        // WHEN
        personService.deleteAPerson(personToDelete.getLastName(),
                personToDelete.getFirstName());
        // THEN
        verify(personRepository).findByLastNameAndFirstName(
                personToDelete.getLastName(), personToDelete.getFirstName());
        verify(personRepository).deleteById(any(Long.class));
    }

    // DELETE
    @Test(expected = PersonNotFoundException.class) // DELETE
    @Tag("TestF-DeleteAPerson")
    @DisplayName("6. Given a unknown person to delete, when delete the person,"
            + " then find this person returns null.")
    public void f2_givenAUnknownPersonToDelete_whenDeletePerson_thenReturnNull()
            throws PersonNotFoundException {
        // GIVEN
        PersonDTO personToDelete = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        personService.deleteAPerson(personToDelete.getLastName(),
                personToDelete.getFirstName());
        // THEN
        verify(personRepository).findByLastNameAndFirstName(
                personToDelete.getLastName(), personToDelete.getFirstName());
        verify(personRepository, never()).deleteById(any(Long.class));

    }

}
