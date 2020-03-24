package com.safetynet.alerts.model;

import java.time.LocalDate;
import java.util.Arrays;

public class MedicalRecords {

	private int id;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String[] medications;
	private String[] allergies;

	public int getId() {
		return id;
	}

	public void setId(int pId) {
		id = pId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String pFirstName) {
		firstName = pFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String pLastName) {
		lastName = pLastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate pBirthDate) {
		birthDate = pBirthDate;
	}

	public String[] getMedications() {
		return medications;
	}

	public void setMedications(String[] pMedications) {
		this.medications = pMedications;
	}

	public String[] getAllergies() {
		return allergies;
	}

	public void setAllergies(String[] pAllergies) {
		this.allergies = pAllergies;
	}

	@Override
	public String toString() {
		return "MedicalRecords [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
				+ ", medications=" + Arrays.toString(medications) + ", allergies=" + Arrays.toString(allergies) + "]";
	}

}
