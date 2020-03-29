package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    // Person/{lastName}
    @GetMapping(value = "Person/{lastName}")
    public Person findPersonByName(@PathVariable final String lastName) {
        return personService.findByLastName(lastName);
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

}
