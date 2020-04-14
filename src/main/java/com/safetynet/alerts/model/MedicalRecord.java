package com.safetynet.alerts.model;

import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Medical records class, used to contain the medical data of persons.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    /**
     * The id of a medical record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    /**
     * The first name of the medical record owner.
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * The last name of the medical record owner.
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * The birthday of the medical record owner.
     */
    @Column(name = "birthdate")
    private LocalDate birthDate;
    /**
     * List of medications taken by the medical record owner.
     */
    @Column(name = "medications")
    private String[] medications;
    /**
     * List of allergies taken by the medical record owner.
     */
    @Column(name = "allergies")
    private String[] allergies;

    /**
     * Getter of medical record id.
     *
     * @return an int
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of medical record id.
     *
     * @param pId
     */
    public void setId(final int pId) {
        id = pId;
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
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Setter of medical record owner birthday.
     *
     * @param pBirthDate
     */
    public void setBirthDate(final LocalDate pBirthDate) {
        birthDate = pBirthDate;
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
        this.medications = pMedications;
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
        this.allergies = pAllergies;
    }

    /**
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "MedicalRecord [id= " + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", birthDate=" + birthDate + ", medications="
                + Arrays.toString(medications) + ", allergies="
                + Arrays.toString(allergies) + "]";
    }

}
