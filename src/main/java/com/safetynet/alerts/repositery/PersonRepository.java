package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

/**
 * Interface PersonRepository allows us to use CrudRepository methods.
 *
 * @author docky
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    /**
     * A method that allows us to find a person with lastName & firstName as
     * arguments.
     *
     * @param pLastName
     * @param pFirstName
     * @return the Person
     */
    Person findByLastNameAndFirstName(String pLastName, String pFirstName);
}
