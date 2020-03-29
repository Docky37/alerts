package com.safetynet.alerts.model;

/**
 * Person class, used to contain inhabitant data.
 *
 * @author Thierry SCHREINER
 */
public class Person {

	/**
	 * The id of the person.
	 */
	private int id;
	/**
	 * The first name of the person.
	 */
	private String firstName;
	/**
	 * The last name of the person.
	 */
	private String lastName;
	/**
	 * The address of the person.
	 */
	private String address;
	/**
	 * The city where lives the person.
	 */
	private String city;
	/**
	 * Zip code of the city where lives the person.
	 */
	private String zip;
	/**
	 * The phone number of the person.
	 */
	private String phone;
	/**
	 * 
	 */
	private String email;

	/**
	 * Class constructor
	 *
	 * @param pId
	 * @param pFirstName
	 * @param pLastName
	 * @param pAddress
	 * @param pCity
	 * @param pZip
	 * @param pPhone
	 * @param pEmail
	 */
	public Person(int pId, String pFirstName, String pLastName, String pAddress, String pCity, String pZip,
			String pPhone, String pEmail) {
		super();
		id = pId;
		firstName = pFirstName;
		lastName = pLastName;
		address = pAddress;
		city = pCity;
		zip = pZip;
		phone = pPhone;
		email = pEmail;
	}

	/**
	 * Getter of the person id.
	 *
	 * @return an int - the person id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of the person id.
	 *
	 * @param pId - the person id.
	 */
	public void setId(int pId) {
		id = pId;
	}

	/**
	 * Getter of the person first name.
	 *
	 * @return a String - the person first name
	 */
	public String getFirstname() {
		return firstName;
	}

	/**
	 * Setter of the person first name.
	 *
	 * @param pFirstName - the person first name
	 */
	public void setFirstname(String pFirstName) {
		firstName = pFirstName;
	}

	/**
	 * Getter of the person last name.
	 *
	 * @return a String - the person last name
	 */
	public String getLastname() {
		return lastName;
	}

	/**
	 * Setter of the person last name.
	 *
	 * @param pLastName - the person last name
	 */
	public void setLastname(String pLastName) {
		lastName = pLastName;
	}

	/**
	 * Getter of the person address.
	 *
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter of the person address.
	 *
	 * @param pAddress
	 */
	public void setAddress(String pAddress) {
		address = pAddress;
	}
	/**
	 * Getter of the person city.
	 *
	 * @return
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Getter of the person city.
	 *
	 * @param pCity
	 */
	public void setCity(String pCity) {
		city = pCity;
	}
	/**
	 * Getter of the city zip code.
	 *
	 * @return
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * Setter of the city zip code.
	 *
	 * @param pZip
	 */
	public void setZip(String pZip) {
		zip = pZip;
	}
	/**
	 * Getter of the phone number.
	 *
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * Setter of the phone number.
	 *
	 * @param pPhone
	 */
	public void setPhone(String pPhone) {
		phone = pPhone;
	}
	/**
	 * Getter of the person e-mail.
	 *
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Setter of the person e-mail.
	 *
	 * @param pEmail
	 */
	public void setEmail(String pEmail) {
		email = pEmail;
	}

	/**
	 * Serialization toString method.
	 */
	@Override
	public String toString() {
		return "Person [id= " + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", city=" + city + ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
	}

}
