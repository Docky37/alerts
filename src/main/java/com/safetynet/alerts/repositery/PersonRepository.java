package com.safetynet.alerts.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByLastNameAndFirstName(final String pLastName,
            final String pFirstName);

    List<Person> findAll();

    Person savePerson(Person pPerson);

    Person updateByLastNameAndFirstName(final String pLastName,
            final String pFirstName) ;

    void deleteByLastNameAndFirstName(final String pLastName,
            final String pFirstName);

    
}
