package com.safetynet.alerts.DTO;

import java.util.List;

import com.safetynet.alerts.model.AddressEntity;

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
     * The AddressEntity that corresponds to the given address.
     */
    private AddressEntity addressEntity;

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
    public HouseholdDTO(final AddressEntity pAddressFireStation,
            final List<PersonInfoDTO> pPersonList) {
        addressEntity = pAddressFireStation;
        personList = pPersonList;
    }

    /**
     * Getter of addressEntity.
     *
     * @return the addressEntity
     */
    public AddressEntity getAddressFireStation() {
        return addressEntity;
    }

    /**
     * Setter of addressEntity.
     *
     * @param pAddressFireStation the addressEntity to set
     */
    public void setAddressFireStation(
            final AddressEntity pAddressFireStation) {
        addressEntity = pAddressFireStation;
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
        return "HouseholdDTO [addressEntity="
                + addressEntity.toString() + ", personList="
                + personList.toString() + "]";
    }

}
