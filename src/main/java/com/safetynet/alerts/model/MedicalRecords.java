package com.safetynet.alerts.model;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Medical records class, used to contain the medical data of inhabitants.
 *
 * @author Thierry SCHREINER
 */
public class MedicalRecords {

	/**
	 * The id of a medical record.
	 */
	private int id;
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
	private LocalDate birthDate;
	/**
	 * List of medications taken by the medical record owner.
	 */
	private String[] medications;
	/**
	 * List of allergies taken by the medical record owner.
	 */
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
	public void setId(int pId) {
		id = pId;
	}

	/**
	 * Getter of medical record owner first name.
	 *
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter of medical record owner first name.
	 *
	 * @param pFirstName
	 */
	public void setFirstName(String pFirstName) {
		firstName = pFirstName;
	}

	/**
	 * Getter of medical record owner last name.
	 *
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter of medical record owner last name.
	 *
	 * @param pLastName
	 */
	public void setLastName(String pLastName) {
		lastName = pLastName;
	}

	/**
	 * Getter of medical record owner birthday.
	 *
	 * @return
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter of medical record owner birthday.
	 *
	 * @param pBirthDate
	 */
	public void setBirthDate(LocalDate pBirthDate) {
		birthDate = pBirthDate;
	}
	/**
	 * Getter of the medications array.
	 *
	 * @return
	 */
	public String[] getMedications() {
		return medications;
	}
	/**
	 * Setter of the medications array.
	 *
	 * @param pMedications
	 */
	public void setMedications(String[] pMedications) {
		this.medications = pMedications;
	}
	/**
	 * Getter of the allergies array.
	 *
	 * @return
	 */
	public String[] getAllergies() {
		return allergies;
	}
	/**
	 * Setter of the allergies array.
	 *
	 * @param pAllergies
	 */
	public void setAllergies(String[] pAllergies) {
		this.allergies = pAllergies;
	}

	/**
	 * Serialization toString method.
	 */
	@Override
	public String toString() {
		return "MedicalRecords [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
				+ ", medications=" + Arrays.toString(medications) + ", allergies=" + Arrays.toString(allergies) + "]";
	}

}
