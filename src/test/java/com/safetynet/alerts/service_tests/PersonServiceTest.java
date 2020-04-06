package com.safetynet.alerts.service_tests;

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
import static org.mockito.Mockito.verify;

import com.safetynet.alerts.controller.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.PersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonService.class)
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    private PersonService personService;

    public static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person(1L, "John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(2L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(3L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    @Before
    public void SetUp() {
        personService = new PersonService(personRepository);
    }

    // GET ("/Person/{lastName}/{FirstName}") >>> READ (Find a Person)
    @Test
    public void givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        given(personRepository.findByLastNameAndFirstName(anyString(),
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
        given(personRepository.findByLastNameAndFirstName(anyString(),
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
        given(personRepository.findAll()).willReturn(personList);
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
        Person personToAdd = new Person(4L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");

        given(personRepository.save(any(Person.class)))
                .willReturn(personToAdd);
        // WHEN
        Person addedPerson = personService.addPerson(personToAdd);
        // THEN
        assertThat(addedPerson.getId()).isEqualTo(4);
        assertThat(addedPerson.getLastName()).isEqualTo("Boyd");
        assertThat(addedPerson.getFirstName()).isEqualTo("Roger");

    }

    @Test // PUT >>> UPDATE (
    public void givenAPersonToUpdate_whenUpdatePerson_thenReturnUpdatedPerson()
            throws Exception {
        // GIVEN
        Person personToUpdate = personList.get(2);
        Person updatedPerson = personList.get(2);
        updatedPerson.setEmail("updated@email.com");
        updatedPerson.setPhone("0123456789");
        given(personRepository.findByLastNameAndFirstName(personToUpdate.getLastName(),personToUpdate.getFirstName()))
                .willReturn(personToUpdate);
        given(personRepository.save(any(Person.class)))
                .willReturn(updatedPerson);
        
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
    public void givenAPersonToDelete_whenDeletePerson_thenReturnPersonDoesNotExist()
            throws Exception {
        // GIVEN
        Person personToDelete = personList.get(2);
        given(personRepository.findByLastNameAndFirstName(personToDelete.getLastName(),personToDelete.getFirstName()))
        .willReturn(personToDelete);
        // WHEN
        personService.deleteAPerson(personToDelete);
        // THEN
        verify(personRepository).findByLastNameAndFirstName(personToDelete.getLastName(),personToDelete.getFirstName());
        verify(personRepository).deleteById(any(Long.class));
     }

}
