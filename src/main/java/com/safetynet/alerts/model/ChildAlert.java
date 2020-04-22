package com.safetynet.alerts.model;

import java.util.List;

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
    private List<Child> childList;
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
    public List<Child> getChildList() {
        return childList;
    }

    /**
     * Setter of childList.
     *
     * @param pChildList the childList to set
     */
    public void setChildList(final List<Child> pChildList) {
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
    public ChildAlert(final String pAddress, final List<Child> pChildList,
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
