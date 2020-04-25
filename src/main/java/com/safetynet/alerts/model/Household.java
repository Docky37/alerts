package com.safetynet.alerts.model;

import java.util.List;

import com.safetynet.alerts.DTO.PersonInfoDTO;

/**
 * The Household class is used for OPS4 fire endpoint. It provides a list of the
 * person living at a given address. Its a List<PersonInfoDTO> with firstName,
 * lastName, age, medications[], allergies[] completed with the number of the
 * covering station.
 *
 * @author Thierry Schreiner
 */
public class Household {

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
    public Household() {
    }

    /**
     * Class constructor.
     *
     * @param pAddressFireStation
     * @param pPersonList
     */
    public Household(AddressFireStation pAddressFireStation,
            List<PersonInfoDTO> pPersonList) {
        addressFireStation = pAddressFireStation;
        personList = pPersonList;
    }

    /**
     * Getter of addressFireStation
     * 
     * @return the addressFireStation
     */
    public AddressFireStation getAddressFireStation() {
        return addressFireStation;
    }

    /**
     * Setter of addressFireStation
     * 
     * @param addressFireStation the addressFireStation to set
     */
    public void setAddressFireStation(AddressFireStation addressFireStation) {
        this.addressFireStation = addressFireStation;
    }

    /**
     * Getter of personList
     * 
     * @return the personList
     */
    public List<PersonInfoDTO> getPersonList() {
        return personList;
    }
    
    /**
     * Setter of personList
     * 
     * @param personList the personList to set
     */
    public void setPersonList(List<PersonInfoDTO> personList) {
        this.personList = personList;
    }

}
