package com.safetynet.alerts.DTO;

import java.util.Arrays;

/**
 * The PersonInfoDTO class .
 *
 * @author Thierry Schreiner
 */
public class PersonInfoDTO {

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
     * List of medications taken by the medical record owner.
     */
    private String[] medications;
    /**
     * List of allergies taken by the medical record owner.
     */
    private String[] allergies;

    /**
     * The phone number of the person.
     */
    private String phone;

    /**
     * Class constructor.
     */
    public PersonInfoDTO() {
    }

    /**
     * Class constructor.
     *
     * @param pFirstName
     * @param pLastName
     * @param pAge
     * @param pMedications
     * @param pAllergies
     * @param pPhone
     */
    public PersonInfoDTO(final String pFirstName, final String pLastName,
            final String pAge, final String[] pMedications,
            final String[] pAllergies, final String pPhone) {
        firstName = pFirstName;
        lastName = pLastName;
        age = pAge;
        medications = pMedications.clone();
        allergies = pAllergies.clone();
        phone = pPhone;
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
     * Getter of the medications array.
     *
     * @return a String
     */
    public String[] getMedications() {
        String[] medic = medications;
        return medic;
    }

    /**
     * Setter of the medications array.
     *
     * @param pMedications
     */
    public void setMedications(final String[] pMedications) {
        medications = pMedications.clone();
    }

    /**
     * Getter of the allergies array.
     *
     * @return a String
     */
    public String[] getAllergies() {
        String[] allerg = allergies;
        return allerg;
    }

    /**
     * Setter of the allergies array.
     *
     * @param pAllergies
     */
    public void setAllergies(final String[] pAllergies) {
        allergies = pAllergies.clone();
    }

    /**
     * Getter of the phone number.
     *
     * @return a String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter of the phone number.
     *
     * @param pPhone
     */
    public void setPhone(final String pPhone) {
        phone = pPhone;
    }

    /**
     * Serialization method.
     */
    @Override
    public String toString() {
        return "PersonInfoDTO [firstName=" + firstName + ", lastName="
                + lastName + ", age=" + age + ", medications="
                + Arrays.toString(medications) + ", allergies="
                + Arrays.toString(allergies) + ", phone=" + phone + "]";
    }
}
