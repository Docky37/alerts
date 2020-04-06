package com.safetynet.alerts.repositery_tests;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
import com.safetynet.alerts.repositery.PersonRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    @Tag("TestA-CreateAListOfPersons")
    @DisplayName("1. Given a list of persons to Add, when saveAll,"
            + " then records are created in DataBase.")
    @Rollback(false)
    public void a_givenAListOfPersonsToAdd_whenSaveAll_thenListOfPersonsIsCreated()
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
        List<Person> savedList = (List<Person>) personRepository
                .saveAll(personList);
        // THEN
        Assertions.assertThat(savedList.size()).isEqualTo(3);
        Assertions.assertThat(savedList.get(2).getFirstName())
                .isEqualTo("Tenley");
    }

    @Test
    @Tag("TestB-FindAllPersons")
    @DisplayName("2. Given persons table is not empty, when findAll,"
            + " then returns the list of persons.")
    @Rollback(false)
    public void b_givenAPersonsTableNotEmpty_whenFindAll_thenReturnsTheListAllPersons()
            throws Exception {
        // GIVEN
        // If TestA-CreateAListOfPersons is green, then persons table contains 3
        // persons.
        // WHEN
        List<Person> foundPersonsList = (List<Person>) personRepository
                .findAll();
        // THEN
        Assertions.assertThat(foundPersonsList.size()).isEqualTo(3);
        Assertions.assertThat(foundPersonsList.get(0).getFirstName())
                .isEqualTo("John");
        Assertions.assertThat(foundPersonsList.get(1).getFirstName())
                .isEqualTo("Jacob");
        Assertions.assertThat(foundPersonsList.get(2).getFirstName())
                .isEqualTo("Tenley");
    }

    @Test
    @Tag("TestC-FindAPerson")
    @DisplayName("Given a person to find, when search person by last name & firstname,"
            + " then DataBase returns the person.")
    public void c_givenAPersonToFind_whenFindByLastNameAndFirstName_thenReturnThePerson()
            throws Exception {
        // GIVEN
        Person personToFind = new Person(0L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");
        // WHEN
        Person foundPerson = personRepository.findByLastNameAndFirstName(
                personToFind.getLastName(), personToFind.getFirstName());
        // THEN
        Assertions.assertThat(foundPerson.getId()).isEqualTo(3);
    }

    @Test
    @Tag("TestD-CreateAPerson")
    @DisplayName("Given a person to add, when save the person, "
            + "then a new record is created in DataBase.")
    @Rollback(false)
    public void d_givenAPersonToAdd_whenSave_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        Person personToSave = new Person(0L, "Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com");
        // WHEN
        Person savedPerson = personRepository.save(personToSave);
        savedPerson = personRepository.findByLastNameAndFirstName(
                personToSave.getLastName(), personToSave.getFirstName());
        // THEN
        Assertions.assertThat(savedPerson.getFirstName())
                .isEqualTo(personToSave.getFirstName());
        Assertions.assertThat(savedPerson.getLastName())
                .isEqualTo(personToSave.getLastName());
    }

    @Test
    @Tag("TestE-DeleteAPerson")
    @DisplayName("Given a person to delete, when delete the person,"
            + " then find this person return null.")
    @Rollback(false)
    public void e_givenAPersonToDelete_whenDelete_thenFindThePersonReturnsNull()
            throws Exception {
        // GIVEN
        Person personToDelete = new Person(3L, "Tenley", "Boyd",
                "1509 Culver St", "Culver", "97451", "841-874-6512",
                "tenz@email.com");
        // WHEN
        personRepository.delete(personToDelete);
        // THEN
        Person personToFind = personRepository.findByLastNameAndFirstName(
                personToDelete.getLastName(), personToDelete.getFirstName());
        Assertions.assertThatObject(personToFind).isNull();
    }

}
