package com.safetynet.alerts.model;

/**
 * The class PersonFLA is used to store first name, last name and age of each
 * child in the List<PersonFLA> field of the class ChildAlert (for OPS#2
 * endpoint).
 *
 * @author Thierry Schreiner
 */
public class PersonFLA {

    /**
     * The first name of the person.
     */
    private String firstName;
    /**
     * The last name of the person.
     */
    private String lastName;
    /**
     * The age name of the person.
     */
    private String age;

    /**
     * Empty class constructor.
     */
    public PersonFLA() {
    }

    /**
     * @param pFirstName
     * @param pLastName
     * @param pAge
     */
    public PersonFLA(final String pFirstName, final String pLastName,
            final String pAge) {
        firstName = pFirstName;
        lastName = pLastName;
        age = pAge;
    }

    /**
     * Getter of firstName.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter of firstName.
     *
     * @param pFirstName the firstName to set
     */
    public void setFirstName(final String pFirstName) {
        firstName = pFirstName;
    }

    /**
     * Getter of lastName.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of lastName.
     *
     * @param pLastName the lastName to set
     */
    public void setLastName(final String pLastName) {
        lastName = pLastName;
    }

    /**
     * getter of age.
     *
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter of age.
     *
     * @param pAge the age to set
     */
    public void setAge(final String pAge) {
        age = pAge;
    }

    /**
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "PersonFLA [firstName=" + firstName + ", lastName=" + lastName
                + ", age=" + age + "]";
    }
}
