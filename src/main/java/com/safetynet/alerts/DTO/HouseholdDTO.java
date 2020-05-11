package com.safetynet.alerts.DTO;

import java.util.List;

/**
 * The HouseholdDTO class is used for OPS4 fire endpoint. It provides a list of
 * the person living at a given address. Its a List<PersonInfoDTO> with
 * firstName, lastName, age, medications[], allergies[] completed with the
 * number of the covering station.
 *
 * @author Thierry Schreiner
 */
public class HouseholdDTO {

    /**
     * The AddressDTO that corresponds to the given address.
     */
    private AddressDTO address;

    /**
     * The list of person leaving at the given address.
     */
    private List<PersonInfoDTO> personList;

    /**
     * Empty class constructor.
     */
    public HouseholdDTO() {
    }

    /**
     * Class constructor.
     *
     * @param pAddressDTO
     * @param pPersonList
     */
    public HouseholdDTO(final AddressDTO pAddressDTO,
            final List<PersonInfoDTO> pPersonList) {
        address = pAddressDTO;
        personList = pPersonList;
    }

    /**
     * Getter of addressDTO.
     *
     * @return the addressDTO
     */
    public AddressDTO getAddressDTO() {
        return address;
    }

    /**
     * Setter of addressDTO.
     *
     * @param pAddressDTO the addressDTO to set
     */
    public void setAddressDTO(
            final AddressDTO pAddressDTO) {
        address = pAddressDTO;
    }

    /**
     * Getter of personList.
     *
     * @return the personList
     */
    public List<PersonInfoDTO> getPersonList() {
        return personList;
    }

    /**
     * Setter of personList.
     *
     * @param pPersonList the personList to set
     */
    public void setPersonList(final List<PersonInfoDTO> pPersonList) {
        personList = pPersonList;
    }

    /**
     * Serialization method.
     */
    @Override
    public String toString() {
        return "HouseholdDTO [addressDTO="
                + address.toString() + ", personList="
                + personList.toString() + "]";
    }

}
