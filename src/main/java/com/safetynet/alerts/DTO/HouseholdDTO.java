package com.safetynet.alerts.DTO;

import java.util.List;

import com.safetynet.alerts.model.AddressFireStation;

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
     * The AddressFireStation that corresponds to the given address.
     */
    private AddressFireStation addressFireStation;

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
     * @param pAddressFireStation
     * @param pPersonList
     */
    public HouseholdDTO(final AddressFireStation pAddressFireStation,
            final List<PersonInfoDTO> pPersonList) {
        addressFireStation = pAddressFireStation;
        personList = pPersonList;
    }

    /**
     * Getter of addressFireStation.
     *
     * @return the addressFireStation
     */
    public AddressFireStation getAddressFireStation() {
        return addressFireStation;
    }

    /**
     * Setter of addressFireStation.
     *
     * @param pAddressFireStation the addressFireStation to set
     */
    public void setAddressFireStation(
            final AddressFireStation pAddressFireStation) {
        addressFireStation = pAddressFireStation;
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
        return "HouseholdDTO [addressFireStation="
                + addressFireStation.toString() + ", personList="
                + personList.toString() + "]";
    }

}
