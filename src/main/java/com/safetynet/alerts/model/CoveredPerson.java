package com.safetynet.alerts.model;

/**
 * CoveredPerson class, used for OPS1 - List of persons covered by a given
 * station.
 *
 * @author Thierry SCHREINER
 */
public class CoveredPerson {

    /**
     * The id of the person.
     */
    private long id;
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
     * The phone number of the person.
     */
    private String phone;

    /**
     * Class constructor.
     *
     * @param pId
     * @param pFirstName
     * @param pLastName
     * @param pAddress
     * @param pPhone
     */
    public CoveredPerson(final Long pId, final String pFirstName,
            final String pLastName, final String pAddress,
            final String pPhone) {
        super();
        id = pId;
        firstName = pFirstName;
        lastName = pLastName;
        address = pAddress;
        phone = pPhone;
    }

    /**
     * Empty constructor.
     */
    public CoveredPerson() {
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
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "Person [id= " + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", address=" + address + ", phone=" + phone + "]";
    }

}
