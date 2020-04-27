package com.safetynet.alerts.model;

import java.util.List;

import com.safetynet.alerts.DTO.OpsPersonDTO;

/**
 * The class ChildAlert is used in OPS#2 endpoint to store the list of chidren
 * and a list of adults leaving at a given address.
 *
 * @author Thierry Schreiner
 */
public class ChildAlert {

    /**
     * The given address.
     */
    private String address;
    /**
     * The list of child living at the address.
     */
    private List<OpsPersonDTO> childList;
    /**
     * The list of adults living at the address.
     */
    private List<String> adultList;

    /**
     * Empty class constructor.
     */
    public ChildAlert() {
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param pAddress the address to set
     */
    public void setAddress(final String pAddress) {
        address = pAddress;
    }

    /**
     * Getter of childList.
     *
     * @return the childList
     */
    public List<OpsPersonDTO> getChildList() {
        return childList;
    }

    /**
     * Setter of childList.
     *
     * @param pChildList the childList to set
     */
    public void setChildList(final List<OpsPersonDTO> pChildList) {
        childList = pChildList;
    }

    /**
     * Getter of adultList.
     *
     * @return the adultList
     */
    public List<String> getAdultList() {
        return adultList;
    }

    /**
     * Setter of adultList.
     *
     * @param pAdultList the adultList to set
     */
    public void setAdultList(final List<String> pAdultList) {
        adultList = pAdultList;
    }

    /**
     * Class constructor.
     *
     * @param pAddress
     * @param pChildList
     * @param pAdultList
     */
    public ChildAlert(final String pAddress, final List<OpsPersonDTO> pChildList,
            final List<String> pAdultList) {
        address = pAddress;
        childList = pChildList;
        adultList = pAdultList;
    }

    /**
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "ChildAlert [address=" + address + " childList=" + childList
                + ", adultList=" + adultList + "]";
    }
}
