package com.safetynet.alerts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AddressFireStation class, use to map an address and its covering fire
 * station.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "address")
public class AddressFireStation {

    /**
     * The id of the mapping.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    /**
     * The address of the house.
     */
    @Column(name = "address")
    private String address;
    /**
     * The identification number of the covering fire station.
     */
    @Column(name = "station")
    private String station;

    /**
     * Empty constructor.
     */
    public AddressFireStation() {

    }

    /**
     * Class constructor.
     *
     * @param pId
     * @param pAddress
     * @param pStation
     */
    public AddressFireStation(final long pId, final String pAddress,
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
        return "FireStations [id=" + id + ", adress=" + address + ", station="
                + station + "]";
    }

}
