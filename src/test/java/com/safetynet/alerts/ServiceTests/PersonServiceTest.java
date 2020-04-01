package com.safetynet.alerts.ServiceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.Repositery.PersonRepositery;
import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonService.class)
public class PersonServiceTest {

    @MockBean
    private PersonRepositery personRepositery;

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

    @Before
    public void SetUp() {
        personService = new PersonService(personRepositery);
    }

    // GET ("/Person/{lastName}/{FirstName}") >>> READ (Find a Person)
    @Test
    public void givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        given(personRepositery.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(personList.get(0));

        Person person = personService.findByLastNameAndFirstName("Boyd",
                "John");
        assertThat(person.getLastName()).isEqualTo("Boyd");
        assertThat(person.getFirstName()).isEqualTo("John");

    }

    // GET ("/Person/{lastName}/{FirstName}") >>> READ (Try to find an Unknown
    // Person)
    @Test(expected = PersonNotFoundException.class)
    public void givenAStrangerToFind_whenFindPersonByLastNameAndFirstName_thenNotFoundException()
            throws Exception {
        // GIVEN
        given(personRepositery.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        Person person = personService.findByLastNameAndFirstName(anyString(),
                anyString());
        // THEN
        assertThat(person).isNull();
    }

    // GET ("/Person") >>> READ (Find All)
    @Test
    public void givenAllPersonsToFind_whenFindAll_thenReturnListOfAllPersons()
            throws Exception {
        // GIVEN
        given(personRepositery.findAll()).willReturn(personList);
        // WHEN
        List<Person> foundList = personService.findAll();
        // THEN
        assertThat(foundList.get(0).getFirstName()).isEqualTo("John");
        assertThat(foundList.get(1).getFirstName()).isEqualTo("Jacob");
        assertThat(foundList.get(2).getFirstName()).isEqualTo("Tenley");
    }

    // POST >>> CREATE (Add a new Person)
    @Test
    public void givenAPersonToAdd_whenPostPerson_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        Person personToAdd = new Person(4, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");

        given(personRepositery.addPerson(any(Person.class)))
                .willReturn(personToAdd);
        // WHEN
        Person addedPerson = personService.addPerson(personToAdd);
        // THEN
        assertThat(addedPerson.getId()).isEqualTo(4);
        assertThat(addedPerson.getLastName()).isEqualTo("Boyd");
        assertThat(addedPerson.getFirstName()).isEqualTo("Roger");

    }

    @Test // PUT >>> UPDATE (Upd
    public void givenAPersonToUpdate_whenUpdatePerson_thenReturnUpdatedPerson()
            throws Exception {
        // GIVEN
        Person personToUpdate = personList.get(2);
        personToUpdate.setEmail("updated@email.com");
        personToUpdate.setPhone("0123456789");
        given(personRepositery.updatePerson(any(Person.class)))
                .willReturn(personToUpdate);
        // WHEN
        Person updatedPerson = personService.updatePerson(personToUpdate);
        // THEN
        assertThat(updatedPerson.getFirstName())
                .isEqualTo(personToUpdate.getFirstName());
        assertThat(updatedPerson.getEmail())
                .isEqualTo(personToUpdate.getEmail());
        assertThat(updatedPerson.getPhone())
                .isEqualTo(personToUpdate.getPhone());
    }

    @Test // DELETE
    public void givenAPersonToDelete_whenDeletePerson_thenReturnIsDeleted()
            throws Exception {
        // GIVEN
        Person personToDelete = personList.get(2);
        given(personRepositery.deletePerson(any(Person.class)))
                .willReturn(true);
        // WHEN
        Boolean isPersonDeleted = personService.deletePerson(personToDelete);
        // THEN
        assertThat(isPersonDeleted).isEqualTo(true);
    }

}
