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
 * MedicalRecord class, is used to contain the medical data of persons, in
 * relation with the database.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    static DateTimeFormatter Formatter = DateTimeFormatter
            .ofPattern("MM/dd/yyyy");
    static DateFormat Format = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Empty class constructor.
     */
    public MedicalRecord() {
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
    @Column(name = "birth_date")
    private Date birthDate;
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
     * @param id
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param medications
     * @param allergies
     */
    public MedicalRecord(final long pId, final String pFirstName,
            final String pLastName, final String pBirthDate,
            final String[] pMedications, final String[] pAllergies) {

        super();
        id = pId;
        firstName = pFirstName;
        lastName = pLastName;
        birthDate = Date.valueOf(LocalDate.parse(pBirthDate, Formatter));
        medications = pMedications;
        allergies = pAllergies;
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
    public String getBirthDate() {
        return Format.format(birthDate);
    }

    /**
     * Setter of medical record owner birthday.
     *
     * @param pBirthDate
     */
    public void setBirthDate(final String pBirthDate) {
        birthDate = Date.valueOf(LocalDate.parse(pBirthDate, Formatter));
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
        return "MedicalRecord [id= " + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", birthDate="
                + Format.format(birthDate) + ", medications="
                + Arrays.toString(medications) + ", allergies="
                + Arrays.toString(allergies) + "]";
    }

}
