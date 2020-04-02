package com.safetynet.alerts.repositery;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runners.MethodSorters;

import com.safetynet.alerts.model.Person;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    @Tag("TestCreateAListOfPersons")
    @Rollback(false)
    public void givenAListOfPersonsToAdd_whenPostList_thenListOfPersonsIsCreated()
            throws Exception {
        // GIVEN
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(2L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(3L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
        // WHEN
        List<Person> savedList = personRepository.saveAll(personList);
        // THEN
        Assertions.assertThat(savedList.size()).isEqualTo(3);
        Assertions.assertThat(savedList.get(2).getFirstName())
                .isEqualTo("Tenley");
    }

    @Test
    @Tag("TestCreateAPerson")
    @Rollback(false)
    public void givenAPersonToAdd_whenPostPerson_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        Person personToSave = new Person(4L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        // WHEN
        Person savedPerson = personRepository.savePerson(personToSave);
        // THEN
        Assertions.assertThat(savedPerson.getFirstName())
                .isEqualTo(personToSave.getFirstName());
        Assertions.assertThat(savedPerson.getLastName())
                .isEqualTo(personToSave.getLastName());
    }

    @Test
    @Tag("TestFindAPerson")
    public void givenAPersonToFind_whenGetPersonByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        // GIVEN
        Person personToFind = new Person(3L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");
        // WHEN
        Person foundPerson = personRepository.findByLastNameAndFirstName(
                personToFind.getLastName(), personToFind.getFirstName());
        // THEN
        Assertions.assertThat(foundPerson.getId()).isEqualTo(3);
    }

}
