package com.safetynet.alerts.Repositery;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public class PersonRepositery {

    public Person findByLastNameAndFirstName(final String pLastName,
            final String pFirstName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Person> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    public Person addPerson(Person pPerson) {
        // TODO Auto-generated method stub
        return null;
    }

    public Person updatePerson(Person pPerson) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean deletePerson(Person pPerson) {
        // TODO Auto-generated method stub
        return true;
    }

}
