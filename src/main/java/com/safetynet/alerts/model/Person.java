package com.safetynet.alerts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;;

/**
 * Person class, used to contain inhabitant data.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "persons")
public class Person {

    /**
     * The id of the person.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    /**
     * The first name of the person.
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * The last name of the person.
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * The address of the person.
     */
    @Column(name = "address")
    private String address;
    /**
     * The city where lives the person.
     */
    @Column(name = "city")
    private String city;
    /**
     * Zip code of the city where lives the person.
     */
    @Column(name = "zip")
    private String zip;
    /**
     * The phone number of the person.
     */
    @Column(name = "phone")
    private String phone;
    /**
     *
     */
    @Column(name = "email")
    private String email;

    /**
     * Class constructor.
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
    public Person(final Long pId, final String pFirstName,
            final String pLastName, final String pAddress, final String pCity,
            final String pZip, final String pPhone, final String pEmail) {
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
     * Empty constructor.
     */
    public Person() {
    }

    /**
     * Getter of the person id.
     *
     * @return an int - the person id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter of the person id.
     *
     * @param pId - the person id.
     */
    public void setId(final Long pId) {
        id = pId;
    }

    /**
     * Getter of the person first name.
     *
     * @return a String - the person first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of the person first name.
     *
     * @param pFirstName - the person first name
     */
    public void setFirstName(final String pFirstName) {
        firstName = pFirstName;
    }

    /**
     * Getter of the person last name.
     *
     * @return a String - the person last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of the person last name.
     *
     * @param pLastName - the person last name
     */
    public void setLastName(final String pLastName) {
        lastName = pLastName;
    }

    /**
     * Getter of the person address.
     *
     * @return a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of the person address.
     *
     * @param pAddress
     */
    public void setAddress(final String pAddress) {
        address = pAddress;
    }

    /**
     * Getter of the person city.
     *
     * @return a String
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter of the person city.
     *
     * @param pCity
     */
    public void setCity(final String pCity) {
        city = pCity;
    }

    /**
     * Getter of the city zip code.
     *
     * @return a String
     */
    public String getZip() {
        return zip;
    }

    /**
     * Setter of the city zip code.
     *
     * @param pZip
     */
    public void setZip(final String pZip) {
        zip = pZip;
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
     * Getter of the person e-mail.
     *
     * @return a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of the person e-mail.
     *
     * @param pEmail
     */
    public void setEmail(final String pEmail) {
        email = pEmail;
    }

    /**
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "Person [id= " + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", address=" + address + ", city=" + city
                + ", zip=" + zip + ", phone=" + phone + ", email=" + email
                + "]";
    }

}
