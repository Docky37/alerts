package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

/**
 * PersonsController class.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class PersonController {

    public PersonService personService;

    public PersonController(PersonService pPersonService) {
        personService = pPersonService;
    }

    // Person/{lastName},{firstName}
    @GetMapping(value = "Person/{lastName}/{firstName}")
    public Person findPersonByName(@PathVariable final String lastName,
            @PathVariable final String firstName) {
        return personService.findByLastnameAndFirstname(lastName, firstName);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void PersonNotFoundHandler(PersonNotFoundException e) {

    }

    // Person/
    @GetMapping(value = "Person")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping(value = "Person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Person pPerson) {

        Person personAdded = personService.save(pPerson);

        if (personAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("Person/{lastName}/{firstName}")
                .buildAndExpand(personAdded.getLastName(),
                        personAdded.getFirstName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
