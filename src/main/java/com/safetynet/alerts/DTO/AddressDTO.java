package com.safetynet.alerts.DTO;



/**
 * AddressEntity class, use to map an address and its covering fire
 * station.
 *
 * @author Thierry SCHREINER
 */
public class AddressDTO {

    /**
     * The address of the household.
     */
    private String address;
    /**
     * The city of the address.
     */
    private String city = "Culver";
    /**
     * The zip code of the city.
     */
    private String zip = "97451";
    /**
     * The identification number of the covering fire station.
     */
    private String station;

    /**
     * Empty constructor.
     */
    public AddressDTO() {

    }

    /**
     * Class constructor.
     *
     * @param pAddress
     * @param pStation
     */
    public AddressDTO(final String pAddress,
            final String pStation) {
        super();
        address = pAddress;
        station = pStation;
    }

    /**
     * Getter of address.
     *
     * @return a String - the station address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of address.
     *
     * @param pAdress - the station address
     */
    public void setAddress(final String pAdress) {
        address = pAdress;
    }

    /**
     * Getter of the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter of the city.
     *
     * @param pCity the city to set
     */
    public void setCity(final String pCity) {
        this.city = pCity;
    }

    /**
     * Getter of the zip code.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Setter of the zip code.
     *
     * @param pZip the zip to set
     */
    public void setZip(final String pZip) {
        this.zip = pZip;
    }

    /**
     * Getter of station (identification number).
     *
     * @return a String - the station identification number
     */
    public String getStation() {
        return station;
    }

    /**
     * Setter of station (identification number).
     *
     * @param pStation - the station identification number
     */
    public void setStation(final String pStation) {
        station = pStation;
    }

    /**
     * Serialization toString method.
     */
    @Override
    public String toString() {
        return "AddressDTO [address=" + address + ", city="
                + city + ", zip code=" + zip + ", station=" + station + "]";
    }

}
