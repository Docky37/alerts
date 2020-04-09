package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

/**
 * PersonsController class, used to create administrative endpoints for CRUD
 * operations on persons data.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class PersonController {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * The service class used to manage person administrative CRUD operations.
     */
    private PersonService personService;

    /**
     * Class constructor - Set personService (IoC).
     *
     * @param pPersonService
     */
    public PersonController(final PersonService pPersonService) {
        personService = pPersonService;
    }

    /**
     * POST request to create persons in DataBase from a list.
     *
     * @param pListPerson
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "Persons")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final List<Person> pListPerson) {

        List<Person> listPersonAdded = personService
                .addListPersons(pListPerson);

        if (listPersonAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("Persons/")
                .buildAndExpand(listPersonAdded.get(1).getLastName(),
                        listPersonAdded.get(1).getFirstName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * GET request to find all persons in DataBase.
     *
     * @return a List of all persons in DB
     */
    @GetMapping(value = "Person")
    public List<Person> findAll() {
        return personService.findAll();
    }

    /**
     * GET request to find one Person by lastName and firstName.
     *
     * @param lastName
     * @param firstName
     * @return a Person
     */
    @GetMapping(value = "Person/{lastName}/{firstName}")
    public Person findPersonByName(@PathVariable final String lastName,
            @PathVariable final String firstName) {
        return personService.findByLastNameAndFirstName(lastName, firstName);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void personNotFoundHandler(final PersonNotFoundException e) {

    }

    /**
     * POST request to add a new person in DB.
     *
     * @param pPerson - The person to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "Person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody final Person pPerson) {

        Person personAdded = personService.addPerson(pPerson);

        if (personAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("Person/{lastName}/{firstName}")
                .buildAndExpand(personAdded.getLastName(),
                        personAdded.getFirstName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * PUT request to update a person.
     *
     * @param pPerson
     * @return ResponseEntity<Void>
     */
    @PutMapping(value = "Person/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> update(@RequestBody final Person pPerson) {

        Person personUpdated = personService.updatePerson(pPerson);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("Person/{lastName}/{firstName}")
                .buildAndExpand(personUpdated.getLastName(),
                        personUpdated.getFirstName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * DELETE request to remove a person record from DB.
     *
     * @param lastName
     * @param firstName
     * @return ResponseEntity<Void>
     */
    @DeleteMapping(value = "Person/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePerson(
            @PathVariable final String lastName,
            @PathVariable final String firstName) {
        Person personToDelete = null;
        personToDelete = personService.deleteAPerson(lastName, firstName);
        if (personToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
