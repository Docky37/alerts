package com.safetynet.alerts.DTO;

/**
 * The class OpsPersonDTO is used to store first name, last name and age of each
 * child in the List<OpsPersonDTO> field of the class ChildDTO (for OPS#2
 * endpoint).
 *
 * @author Thierry Schreiner
 */
public class OpsPersonDTO {

    /**
     * The first name of the person.
     */
    private String firstName;
    /**
     * The last name of the person.
     */
    private String lastName;
    /**
     * The age name of the person.
     */
    private String age;

    /**
     * The address of the person.
     */
    private String address;

    /**
     * The phone number of the person.
     */
    private String phone;

    /**
     * Empty class constructor.
     */
    public OpsPersonDTO() {
    }

    /**
     * @param pFirstName
     * @param pLastName
     * @param pAge
     * @param pAddress
     * @param pPhone
     */
    public OpsPersonDTO(final String pFirstName, final String pLastName,
            final String pAge, final String pAddress, final String pPhone) {
        firstName = pFirstName;
        lastName = pLastName;
        age = pAge;
        phone = pPhone;
        address = pAddress;
    }

    /**
     * Getter of firstName.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter of firstName.
     *
     * @param pFirstName the firstName to set
     */
    public void setFirstName(final String pFirstName) {
        firstName = pFirstName;
    }

    /**
     * Getter of lastName.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of lastName.
     *
     * @param pLastName the lastName to set
     */
    public void setLastName(final String pLastName) {
        lastName = pLastName;
    }

    /**
     * getter of age.
     *
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter of age.
     *
     * @param pAge the age to set
     */
    public void setAge(final String pAge) {
        age = pAge;
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
        return "OpsPersonDTO [firstName=" + firstName + ", lastName=" + lastName
                + ", age=" + age + ", address=" + address + ", phone=" + phone
                + "]";
    }
}
