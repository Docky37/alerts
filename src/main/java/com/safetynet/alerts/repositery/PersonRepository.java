package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByLastNameAndFirstName(final String pLastName,
            final String pFirstName);
}
