package com.safetynet.alerts.DTO;

import java.util.List;

import com.safetynet.alerts.model.Household;

public class FloodDTO {

    private String station;

    private List<Household> householdList;

    /**
     * Getter of station
     * 
     * @return the station
     */
    public String getStation() {
        return station;
    }

    /**
     * Setter of station
     * 
     * @param pStation the station to set
     */
    public void setStation(final String pStation) {
        station = pStation;
    }

    /**
     * Empty class constructor
     */
    public FloodDTO() {
    }

    /**
     * Class constructor
     *
     * @param pStationString
     * @param pPersonsAtAddressList
     */
    public FloodDTO(final String pStationString, final List<Household> pHouseholdList) {
        station = pStationString;
        householdList = pHouseholdList;
    }

    /**
     * Getter of householdList.
     *
     * @return the householdList
     */
    public List<Household> getHouseholdList() {
        return householdList;
    }

    /**
     * Setter of householdList.
     *
     * @param pHouseholdList the householdList to set
     */
    public void setHouseholdList(final List<Household> pHouseholdList) {
        householdList = pHouseholdList;
    }

}
