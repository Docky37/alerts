package com.safetynet.alerts.model;



/**
 * AddressEntity class, use to map an address and its covering fire
 * station.
 *
 * @author Thierry SCHREINER
 */
public class AddressDTO {

    /**
     * The id of the mapping.
     */
    private long id;
    /**
     * The address of the house.
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
     * @param pId
     * @param pAddress
     * @param pStation
     */
    public AddressDTO(final long pId, final String pAddress,
            final String pStation) {
        super();
        id = pId;
        address = pAddress;
        station = pStation;
    }

    /**
     * Getter of id.
     *
     * @return an long - the id of the mapping address
     */
    public long getId() {
        return id;
    }

    /**
     * Setter of id.
     *
     * @param pId - the id of the fire station
     */
    public void setId(final long pId) {
        id = pId;
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
        return "FireStations [id=" + id + ", address=" + address + ", city="
                + city + ", zip code=" + zip + ", station=" + station + "]";
    }

}
