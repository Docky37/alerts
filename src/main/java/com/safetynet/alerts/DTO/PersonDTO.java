package com.safetynet.alerts.DTO;

/**
 * PersonDTO class, used to contain inhabitant data.
 *
 * @author Thierry SCHREINER
 */
public class PersonDTO {

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
     * The e-mail of the person.
     */
    private String email;

    /**
     * Class constructor.
     * @param pFirstName
     * @param pLastName
     * @param pAddress
     * @param pCity
     * @param pZip
     * @param pPhone
     * @param pEmail
     */
    public PersonDTO(final String pFirstName,
            final String pLastName, final String pAddress, final String pCity,
            final String pZip, final String pPhone, final String pEmail) {
        super();
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
    public PersonDTO() {
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

}
