package com.safetynet.alerts.model;

import java.util.Arrays;

/**
 * MedicalRecordEntity class, is used to contain the medical data of persons, in
 * relation with the database.
 *
 * @author Thierry SCHREINER
 */
public class MedicalRecordDTO {

    /**
     * Empty class constructor.
     */
    public MedicalRecordDTO() {
    }

    /**
     * The first name of the medical record owner.
     */
    private String firstName;
    /**
     * The last name of the medical record owner.
     */
    private String lastName;
    /**
     * The birthday of the medical record owner.
     */
    private String birthdate;
    /**
     * List of medications taken by the medical record owner.
     */
    private String[] medications;
    /**
     * List of allergies taken by the medical record owner.
     */
    private String[] allergies;

    /**
     * Class constructor.
     *
     * @param pFirstName
     * @param pLastName
     * @param pBirthdate
     * @param pMedications
     * @param pAllergies
     */
    public MedicalRecordDTO(final String pFirstName,
            final String pLastName, final String pBirthdate,
            final String[] pMedications, final String[] pAllergies) {

        firstName = pFirstName;
        lastName = pLastName;
        birthdate = pBirthdate;
        medications = pMedications.clone();
        allergies = pAllergies.clone();
    }

    /**
     * Getter of medical record owner first name.
     *
     * @return a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of medical record owner first name.
     *
     * @param pFirstName
     */
    public void setFirstName(final String pFirstName) {
        firstName = pFirstName;
    }

    /**
     * Getter of medical record owner last name.
     *
     * @return a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of medical record owner last name.
     *
     * @param pLastName
     */
    public void setLastName(final String pLastName) {
        lastName = pLastName;
    }

    /**
     * Getter of medical record owner birthday.
     *
     * @return a String
     */
    public String getBirthdate() {
        return birthdate;
    }
    /**
     * Setter of medical record owner birthday.
     *
     * @param pBirthDate
     */
    public void setBirthdate(final String pBirthDate) {
        birthdate = pBirthDate;
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
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "MedicalRecordEntity [firstName=" + firstName
                + ", lastName=" + lastName + ", birthdate="
                + birthdate + ", medications="
                + Arrays.toString(medications) + ", allergies="
                + Arrays.toString(allergies) + "]";
    }

}
