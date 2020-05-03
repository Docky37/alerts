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
import com.safetynet.alerts.DTO.PersonDTO;
import com.safetynet.alerts.service.IPersonService;

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
     * The Status code 501 Not implemented.
     */
    static final int CODE_501 = 501;

    /**
     * The service class used to manage person administrative CRUD operations.
     */
    private IPersonService personService;

    /**
     * Class constructor - Set personService (IoC).
     *
     * @param pPersonService
     */
    public PersonController(final IPersonService pPersonService) {
        personService = pPersonService;
    }

    /**
     * POST request to create persons in DataBase from a list.
     *
     * @param pListPerson
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "persons")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final List<PersonDTO> pListPerson) {

        List<PersonDTO> listPersonAdded = personService
                .addListPersons(pListPerson);

        if (listPersonAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("persons/")
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
    @GetMapping(value = "person")
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }

    /**
     * GET request to find one PersonDTO by lastName and firstName.
     *
     * @param lastName
     * @param firstName
     * @return a PersonDTO
     * @throws PersonNotFoundException
     */
    @GetMapping(value = "person/{lastName}/{firstName}")
    public PersonDTO findPersonByName(@PathVariable final String lastName,
            @PathVariable final String firstName)
            throws PersonNotFoundException {
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
    @PostMapping(value = "person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody final PersonDTO pPerson) {

        PersonDTO personAdded = personService.addPerson(pPerson);

        if (personAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("person/{lastName}/{firstName}")
                .buildAndExpand(personAdded.getLastName(),
                        personAdded.getFirstName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * PUT request to update a person.
     *
     * @param lastName
     * @param firstName
     * @param pPerson
     * @return ResponseEntity<Void>
     * @throws PersonNotFoundException
     */
    @PutMapping(value = "person/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> update(@PathVariable final String lastName,
            @PathVariable final String firstName,
            @RequestBody final PersonDTO pPerson)
            throws PersonNotFoundException {

        PersonDTO personUpdated = personService.updatePerson(lastName,
                firstName, pPerson);
        if (personUpdated == null) {
            return ResponseEntity.status(CODE_501).build();
        }
        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE request to remove a person record from DB.
     *
     * @param lastName
     * @param firstName
     * @return ResponseEntity<Void>
     */
    @DeleteMapping(value = "person/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePerson(
            @PathVariable final String lastName,
            @PathVariable final String firstName) {
        PersonDTO personToDelete = null;
        personToDelete = personService.deleteAPerson(lastName, firstName);
        if (personToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
