package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.alerts.model.AddressFireStation;

public interface AddressFireStationRepository
        extends CrudRepository<AddressFireStation, Long> {

    public AddressFireStation findByAddress(final String pAddressFireStation);

}
