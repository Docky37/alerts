package com.safetynet.alerts.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MedicalRecordEntity class, is used to contain the medical data of persons, in
 * relation with the database.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "medical_records")
public class MedicalRecordEntity {

    /**
     * Date format used to convert String parameter to LocalDate.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("MM/dd/yyyy");

    /**
     * Date format used to convert Date to String.
     */
    private static DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Empty class constructor.
     */
    public MedicalRecordEntity() {
    }

    /**
     * The id of a medical record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
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
    private Date birthdate;
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
     * Class constructor.
     *
     * @param pId
     * @param pFirstName
     * @param pLastName
     * @param pBirthdate
     * @param pMedications
     * @param pAllergies
     */
    public MedicalRecordEntity(final long pId, final String pFirstName,
            final String pLastName, final String pBirthdate,
            final String[] pMedications, final String[] pAllergies) {

        id = pId;
        firstName = pFirstName;
        lastName = pLastName;
        birthdate = Date.valueOf(LocalDate.parse(pBirthdate, formatter));
        medications = pMedications.clone();
        allergies = pAllergies.clone();
    }

    /**
     * Getter of medical record id.
     *
     * @return an int
     */
    public long getId() {
        return id;
    }

    /**
     * Setter of medical record id.
     *
     * @param pId
     */
    public void setId(final long pId) {
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
    public String getBirthdate() {
        return formatter2.format(birthdate);
    }
    /**
     * Setter of medical record owner birthday.
     *
     * @param pBirthDate
     */
    public void setBirthdate(final String pBirthDate) {
        birthdate = Date.valueOf(LocalDate.parse(pBirthDate, formatter));
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
        return "MedicalRecordEntity [id= " + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", birthdate="
                + formatter2.format(birthdate) + ", medications="
                + Arrays.toString(medications) + ", allergies="
                + Arrays.toString(allergies) + "]";
    }

}
