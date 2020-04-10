package com.safetynet.alerts.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.dozermapper.core.DozerConverter;
import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.repositery.AddressFireStationRepository; 

public final class DozerMappingUtil
        extends DozerConverter<String, AddressFireStation> {

    @Autowired
    AddressFireStationRepository addressFireStationRepository;
    
    public DozerMappingUtil() {
        super(String.class, AddressFireStation.class);
        // TODO Auto-generated constructor stub
    }

    @Override
    public AddressFireStation convertTo(String source,
            AddressFireStation destination) {
        AddressFireStation convertedAddress = addressFireStationRepository.findByAddress(source);
        // TODO Auto-generated method stub
        return convertedAddress;
    }

    @Override
    public String convertFrom(AddressFireStation source, String destination) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * public DozerMappingUtil(Class<Person> pPerson, Class<PersonEntity>
     * pPersonEntity) { super(pPerson, pPersonEntity); // TODO Auto-generated
     * constructor stub } private AddressFireStationRepository
     * addressFireStationRepository;
     * 
     * @Override public PersonEntity convertTo(Person pSource, PersonEntity
     * pDestination) { pDestination.setFirstName(pSource.getFirstName());
     * pDestination.setLastName(pSource.getLastName());
     * pDestination.setAddressFireSt(addressFireStationRepository.findByAddress(
     * pSource.getAddress())); pDestination.setPhone(pSource.getPhone());
     * pDestination.setEmail(pSource.getEmail()); return pDestination; }
     * 
     * @Override public Person convertFrom(PersonEntity source, Person
     * destination) { // TODO Auto-generated method stub return null; }
     */
}
