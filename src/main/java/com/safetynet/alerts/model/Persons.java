package com.safetynet.alerts.model;

public class Persons {

	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int pId) {
		id = pId;
	}

	public String getFirstname() {
		return firstName;
	}

	public void setFirstname(String pFirstName) {
		firstName = pFirstName;
	}

	public String getLastname() {
		return lastName;
	}

	public void setLastname(String pLastName) {
		lastName = pLastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String pAddress) {
		address = pAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String pCity) {
		city = pCity;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String pZip) {
		zip = pZip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String pPhone) {
		phone = pPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String pEmail) {
		email = pEmail;
	}

	@Override
	public String toString() {
		return "Persons [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", city=" + city + ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
	}

}
