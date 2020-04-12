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

import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.PersonRepository;
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

    private PersonService personService;

    public static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person(0L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(0L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(0L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public static List<AddressFireStation> addressFireStList = new ArrayList<>();
    static {
        addressFireStList
                .add(new AddressFireStation(1L, "1509 Culver St", "3"));
        addressFireStList.add(new AddressFireStation(2L, "29_15th_St", "2"));
        addressFireStList.add(new AddressFireStation(3L, "834 Binoc Ave", "3"));
    }

    public static List<PersonEntity> pEntList = new ArrayList<>();

    static {
        pEntList.add(new PersonEntity(1L, "John", "Boyd",
                addressFireStList.get(0), "841-874-6512", "jaboyd@email.com"));
        pEntList.add(new PersonEntity(2L, "Jacob", "Boyd",
                addressFireStList.get(0), "841-874-6513", "drk@email.com"));
        pEntList.add(new PersonEntity(3L, "Tenley", "Boyd",
                addressFireStList.get(0), "841-874-6512", "tenz@email.com"));
    }

    @Before
    public void SetUp() {
        personService = new PersonService(personRepository, personMapping);
    }

    // POST >>> CREATE (Add a list of Person)
    @Test
    @Tag("TestA-CreateAListOfPersons")
    @DisplayName("A. Given a list of persons to Add, when POST list,"
            + " then persons are created.")
    public void a_givenAListOfPersonToAdd_whenPostList_thenPersonsAreCreated()
            throws Exception {
        // GIVEN
        given(personMapping.convertToPersonEntity(Mockito.<Person>anyList()))
                .willReturn(pEntList);
        given(personRepository.saveAll(Mockito.<PersonEntity>anyList()))
                .willReturn(pEntList);
        given(personMapping.convertToPerson(Mockito.<PersonEntity>anyList()))
                .willReturn(personList);
        // WHEN
        List<Person> addedList = personService.addListPersons(personList);
        // THEN
        assertThat(addedList.size()).isEqualTo(3);

    }

    // GET ("/Person")>>> READ (Find all persons)
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
        List<Person> foundList = personService.findAll();
        // THEN
        assertThat(foundList.get(0).getFirstName()).isEqualTo("John");
        assertThat(foundList.get(1).getFirstName()).isEqualTo("Jacob");
        assertThat(foundList.get(2).getFirstName()).isEqualTo("Tenley");
    }

    // GET ("/Person/{lastName}/{FirstName}") >>> READ (Find a Person)
    @Test
    @Tag("TestC-FindAPerson")
    @DisplayName("3.Given a person to find, when search person by last name & firstname,"
            + " then returns the person.")
    public void c1_givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(pEntList.get(0));
        given(personMapping.convertToPerson(any(PersonEntity.class)))
                .willReturn(personList.get(0));

        Person person = personService.findByLastNameAndFirstName("Boyd",
                "John");
        assertThat(person.getLastName()).isEqualTo("Boyd");
        assertThat(person.getFirstName()).isEqualTo("John");

    }

    // GET ("/Person/{lastName}/{FirstName}") >>> READ (Try to find an Unknown
    // Person)
    @Test(expected = PersonNotFoundException.class)
    @Tag("TestC2-FindAPerson")
    @DisplayName("3.Given a stranger to find, when search person by last name & firstname,"
            + " then returns null.")
    public void c2_givenAStrangerToFind_whenFindPersonByLastNameAndFirstName_thenNotFoundException()
            throws Exception {
        // GIVEN
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        Person person = personService.findByLastNameAndFirstName(anyString(),
                anyString());
        // THEN
        assertThat(person).isNull();
    }

    // POST >>> CREATE (Add a new Person)
    @Test
    @Tag("TestD-CreateAPerson")
    @DisplayName("4. Given a person to add, when POST the person, "
            + "then a new person is created.")
    public void d_givenAPersonToAdd_whenPostPerson_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        Person personToAdd = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(
                personToAdd.getLastName(), personToAdd.getFirstName()))
                        .willReturn(null, pEntList.get(2));
        given(personMapping.convertToPersonEntity(any(Person.class)))
                .willReturn(pEntList.get(2));
        given(personRepository.save(any(PersonEntity.class)))
                .willReturn(pEntList.get(2));
        given(personMapping.convertToPerson(any(PersonEntity.class)))
                .willReturn(personList.get(2));

        // WHEN
        Person addedPerson = personService.addPerson(personToAdd);
        // THEN
        assertThat(addedPerson.getLastName()).isEqualTo("Boyd");
        assertThat(addedPerson.getFirstName()).isEqualTo("Tenley");

    }

    @Test // PUT >>> UPDATE (
    @Tag("TestE-UpdateAPerson")
    @DisplayName("5. Given a person to update, when save the person,"
            + " then this person is updated.")
    public void e_givenAPersonToUpdate_whenUpdatePerson_thenReturnUpdatedPerson()
            throws Exception {
        // GIVEN
        Person personToUpdate = personList.get(2);
        personToUpdate.setEmail("Tenley.Boyd@OpenClassrooms.com");
        personToUpdate.setPhone("0123456789");
        Person updatedPerson = personToUpdate;
        PersonEntity personInDB = pEntList.get(2);
        PersonEntity updatedPersonInDB = pEntList.get(2);
        updatedPersonInDB.setEmail("Tenley.Boyd@OpenClassrooms.com");
        updatedPersonInDB.setPhone("0123456789");
        given(personRepository.findByLastNameAndFirstName(
                updatedPerson.getLastName(), updatedPerson.getFirstName()))
                        .willReturn(pEntList.get(2));
        given(personMapping.convertToPersonEntity(any(Person.class))).willReturn(personInDB);
        given(personRepository.save(any(PersonEntity.class)))
                .willReturn(updatedPersonInDB);
        given(personMapping.convertToPerson(any(PersonEntity.class))).willReturn(updatedPerson);     

        // WHEN
        updatedPerson = personService.updatePerson(personToUpdate);
        // THEN
        assertThat(updatedPerson.getFirstName())
                .isEqualTo(personToUpdate.getFirstName());
        assertThat(updatedPerson.getEmail())
                .isEqualTo(personToUpdate.getEmail());
        assertThat(updatedPerson.getPhone())
                .isEqualTo(personToUpdate.getPhone());
    }

    @Test // DELETE
    @Tag("TestF-DeleteAPerson")
    @DisplayName("6. Given a person to delete, when delete the person,"
            + " then find this person returns null.")
    public void f_givenAPersonToDelete_whenDeletePerson_thenReturnPersonDoesNotExist()
            throws Exception {
        // GIVEN
        Person personToDelete = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(pEntList.get(2));
        // WHEN
        personService.deleteAPerson(personToDelete.getLastName(),
                personToDelete.getFirstName());
        // THEN
        verify(personRepository).findByLastNameAndFirstName(
                personToDelete.getLastName(), personToDelete.getFirstName());
        verify(personRepository).deleteById(any(Long.class));
    }

}
