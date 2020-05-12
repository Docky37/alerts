package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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
            .getLogger(PersonController.class);

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
     * @param pPersonList
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "person/batch")
    public ResponseEntity<Object> create(
            @RequestBody final List<PersonDTO> pPersonList) {
        LOGGER.info("NEW HTML ADMINISTRATIVE POST REQUEST"
                + " on http://localhost:8080/person/batch");
        LOGGER.info(" AddressController  >>> Import a Person list");
        LOGGER.info(" => List = {}", pPersonList.toString());

        List<PersonDTO> listPersonAdded = personService
                .addListPersons(pPersonList);

        if (listPersonAdded.isEmpty()) {
            LOGGER.info("END of HTML administrative POST Request"
                    + " with Status 200 OK");
            return new ResponseEntity<Object>(personService.getBalanceSheet(),
                    new HttpHeaders(), HttpStatus.OK);
        }

        LOGGER.info("END of HTML administrative POST Request"
                + " with Status 201 Created");
        return new ResponseEntity<Object>(personService.getBalanceSheet(),
                new HttpHeaders(), HttpStatus.CREATED);

    }

    /**
     * GET request to find all persons in DataBase.
     *
     * @return a List of all persons in DB
     */
    @GetMapping(value = "person")
    public List<PersonDTO> findAll() {
        LOGGER.info("NEW HTML ADMINISTRATIVE GET REQUEST"
                + " on http://localhost:8080/person");
        LOGGER.info(" PersonController  >>> Get all persons.");
        List<PersonDTO> resultList = personService.findAll();
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 200 OK ---");
        return resultList;
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
        LOGGER.info("NEW HTML ADMINISTRATIVE GET REQUEST"
                + " on http://localhost:8080/person");
        LOGGER.info(" PersonController  >>> Get the person named '{} {}'.",
                firstName, lastName);
        PersonDTO resultPerson = personService
                .findByLastNameAndFirstName(lastName, firstName);
        LOGGER.info(
                "END of HTML administrative GET Request with Status 200 OK");
        return resultPerson;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void personNotFoundHandler(final PersonNotFoundException e) {
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 404 NOT FOUND");
    }

    /**
     * POST request to add a new person in DB.
     *
     * @param pPerson - The person to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody final PersonDTO pPerson) {
        LOGGER.info("NEW HTML ADMINISTRATIVE POST REQUEST"
                + " on http://localhost:8080/person");
        LOGGER.info(" PersonController  >>> Add the person '{}'", pPerson);

        PersonDTO personAdded = personService.addPerson(pPerson);

        if (personAdded == null) {
            LOGGER.info("END of HTML administrative POST Request"
                    + " with Status 400 Bad Request");
            return new ResponseEntity<Object>("Cannot create this person ("
                    + pPerson.getFirstName() + " " + pPerson.getLastName()
                    + ") that is already registred. "
                    + "Consider PUT request if you want to update this person.",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{lastName}/{firstName}")
                .buildAndExpand(personAdded.getLastName(),
                        personAdded.getFirstName())
                .toUri();
        LOGGER.info("END of HTML administrative POST Request"
                + " with Status 201 Created");
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> update(@PathVariable final String lastName,
            @PathVariable final String firstName,
            @RequestBody final PersonDTO pPerson)
            throws PersonNotFoundException {
        LOGGER.info("NEW HTML ADMINISTRATIVE PUT REQUEST"
                + " on http://localhost:8080/person");
        LOGGER.info("PersonController >>> Update the person '{} {}',",
                firstName, lastName);
        LOGGER.info(" with content: '{}'.", pPerson.toString());

        PersonDTO personUpdated = personService.updatePerson(lastName,
                firstName, pPerson);

        if (personUpdated == null) {
            LOGGER.info("END of HTML administrative PUT Request"
                    + " with Status 501 Not Implemented");
            return new ResponseEntity<Object>(
                    "It is not allowed to rename a person!", new HttpHeaders(),
                    HttpStatus.NOT_IMPLEMENTED);
        } else if (personUpdated.getAddress().contains("'?")) {
            LOGGER.info("END of HTML administrative PUT Request"
                    + " with Status 400 Bad Request");
            return new ResponseEntity<Object>(
                    "It is not allowed to update a person"
                            + " with an unknown address ("
                            + pPerson.getAddress() + " " + pPerson.getZip()
                            + " " + pPerson.getCity() + ").",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("END of HTML administrative PUT Request"
                + " with Status 204 No Content");
        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE request to remove a person record from DB.
     *
     * @param lastName
     * @param firstName
     * @return ResponseEntity<Void>
     * @throws PersonNotFoundException
     */
    @DeleteMapping(value = "person/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePerson(
            @PathVariable final String lastName,
            @PathVariable final String firstName)
            throws PersonNotFoundException {
        LOGGER.info("NEW HTML ADMINISTRATIVE DELETE REQUEST"
                + " on http://localhost:8080/person");
        LOGGER.info("PersonController >>> Delete the person named '{} {}',",
                firstName, lastName);
        PersonDTO personToDelete = null;
        personToDelete = personService.deleteAPerson(lastName, firstName);
        if (personToDelete == null) {
            LOGGER.info("END of HTML administrative DELETE Request"
                    + " with Status 404 NOT FOUND");
            return ResponseEntity.notFound().build();
        }
        LOGGER.info(
                "END of HTML administrative DELETE Request with Status 200 OK");
        return ResponseEntity.ok().build();
    }
}
