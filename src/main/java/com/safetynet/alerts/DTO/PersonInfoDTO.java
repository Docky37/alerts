package com.safetynet.alerts.DTO;

/**
 * The PersonInfoDTO class .
 *
 * @author Thierry Schreiner
 */
public class PersonInfoDTO {

    /**
     * The first name of the person.
     */
    public String firstName;
    /**
     * The last name of the person.
     */
    public String lastName;
    /**
     * The age name of the person.
     */
    public String age;

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
     * Class constructor
     *
     */
    public PersonInfoDTO() {
        super();
    }

    /**
     * Class constructor
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
        medications = pMedications;
        allergies = pAllergies;
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
        return medications;
    }

    /**
     * Setter of the medications array.
     *
     * @param pMedications
     */
    public void setMedications(final String[] pMedications) {
        medications = pMedications;
    }

    /**
     * Getter of the allergies array.
     *
     * @return a String
     */
    public String[] getAllergies() {
        return allergies;
    }

    /**
     * Setter of the allergies array.
     *
     * @param pAllergies
     */
    public void setAllergies(final String[] pAllergies) {
        allergies = pAllergies;
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

}
