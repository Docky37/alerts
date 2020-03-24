package com.safetynet.alerts.model;

/**
 * FireStation class.
 *
 * @author Thierry SCHREINER
 */
public class FireStations {

	/**
	 * The id of the fire station.
	 */
	private int id;
	/**
	 * The adress  of the fire station.
	 */
	private String address;
	/**
	 * Te identification number of the fire station.
	 */
	private String station;

	/**
	 * Class constructor.
	 * 
	 * @param id
	 * @param adress
	 * @param station
	 */
	public FireStations(int id, String adress, String station) {
		super();
		this.id = id;
		this.address = adress;
		this.station = station;
	}

	/**
	 * Getter of id.
	 * 
	 * @return an int - the id of the fire station
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of id.
	 * 
	 * @param pId - the id of the fire station
	 */
	public void setId(int pId) {
		id = pId;
	}

	/**
	 * Getter of address.
	 *
	 * @return a String - the station address
	 */
	public String getAdress() {
		return address;
	}

	/**
	 * Setter of address.
	 *
	 * @param pAdress - the station address
	 */
	public void setAdress(String pAdress) {
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
	public void setStation(String pStation) {
		station = pStation;
	}

	/**
	 * Serialization toString method.
	 */
	@Override
	public String toString() {
		return "FireStations [id=" + id + ", adress=" + address + ", station=" + station + "]";
	}

}
