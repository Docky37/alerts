package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;

@Service
public class PersonService {

    public static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person(1, "John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(2, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(3, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public List<Person> findAll() {
        return personList;
    }

    public Person findByLastName(final String pName) {
        for (Person person : personList) {
            System.out.println();
            if (person.getLastName() == pName) {
                return person;
            }
        }
        return null;
    }
}
