package com.safetynet.alerts.service;

import com.github.dozermapper.core.DozerConverter;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;

public final class DozerMappingUtil2 extends DozerConverter<Person, PersonEntity>{
    
    public DozerMappingUtil2(Class<Person> pPerson,
            Class<PersonEntity> pPersonEntity) {
        super(pPerson, pPersonEntity);
        // TODO Auto-generated constructor stub
    }
    private AddressFireStationRepository addressFireStationRepository;

    @Override
    public PersonEntity convertTo(Person pSource, PersonEntity pDestination) {
        pDestination.setFirstName(pSource.getFirstName());
        pDestination.setLastName(pSource.getLastName());
        pDestination.setAddressFireSt(addressFireStationRepository.findByAddress(pSource.getAddress()));
        pDestination.setPhone(pSource.getPhone());
        pDestination.setEmail(pSource.getEmail());
        return pDestination;
    }

    @Override
    public Person convertFrom(PersonEntity source, Person destination) {
        // TODO Auto-generated method stub
        return null;
    }
}
