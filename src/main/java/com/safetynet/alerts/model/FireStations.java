package com.safetynet.alerts.model;

public class FireStations {

	private int id;
	private String adress;
	private String station;

	public int getId() {
		return id;
	}

	public void setId(int pId) {
		id = pId;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String pAdress) {
		adress = pAdress;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String pStation) {
		station = pStation;
	}

	@Override
	public String toString() {
		return "FireStations [id=" + id + ", adress=" + adress + ", station=" + station + "]";
	}

}
