package com.safetynet.alerts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return personService.getPersonDetails(lastName);
    }

    // Person/
    @GetMapping(value = "Person")
    public Person findAll() {
        return personService.findAll();
    }

}
