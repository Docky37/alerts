package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.service.IAddressService;

/**
 * AddressFireStationController class, used to create administrative endpoints
 * for CRUD operations on address-fire station mapping.
 *
 * @author Thierry Schreiner
 */
@RestController
public class AddressFireStationController {
    /**
     * The service class used to manage address - fire station association CRUD
     * operations.
     */
    private IAddressService addressService;

    /**
     * Class constructor - Set addressService (IoC).
     *
     * @param pFireStationService
     */
    public AddressFireStationController(
            final IAddressService pFireStationService) {
        addressService = pFireStationService;
    }

    /**
     * POST request to create address-fireStation mapping in DataBase from a
     * list of mapping.
     *
     * @param pListFireStation
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "firestations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final List<AddressEntity> pListFireStation) {

        List<AddressEntity> listFireStAdded = addressService
                .addListFireStations(pListFireStation);

        if (listFireStAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("firestations/")
                .buildAndExpand(listFireStAdded.get(1).getAddress(),
                        listFireStAdded.get(1).getStation())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    /**
     * GET request to find all address - fire station associations in DataBase.
     *
     * @return a List of all address - fire station associations in DB
     */
    @GetMapping(value = "firestation")
    public List<AddressEntity> findAll() {
        return addressService.findAll();
    }

    /**
     * POST request to add a new Address-FireStation in DB.
     *
     * @param pAddressFireStation - The association to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final AddressEntity pAddressFireStation) {

        AddressEntity addressFireStAdded = addressService
                .addAddressFireStation(pAddressFireStation);

        if (addressFireStAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("firestation/{address}")
                .buildAndExpand(addressFireStAdded.getAddress(),
                        addressFireStAdded.getStation())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * GET request to find one address-fire station association.
     *
     * @param address
     * @return AddressEntity
     * @throws AddressFireStationNotFoundException
     */
    @GetMapping(value = "firestation/{address}")
    public AddressEntity findByAddress(@PathVariable final String address)
            throws AddressFireStationNotFoundException {
        return addressService.findByAddress(address);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void addressFireStationNotFoundHandler(
            final AddressFireStationNotFoundException e) {

    }

    /**
     * PUT request to update an address - fire station association from
     * DataBase.
     *
     * @param pAddressFireStation
     * @return ResponseEntity<Void>
     */
    @PutMapping(value = "firestation/{address}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> update(
            @RequestBody final AddressEntity pAddressFireStation) {

        AddressEntity addressFireStUpdated = addressService
                .updateAddress(pAddressFireStation);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("firestation/{address}")
                .buildAndExpand(addressFireStUpdated.getAddress(),
                        addressFireStUpdated.getStation())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * DELETE request to remove an address - fire station association from
     * DataBase.
     *
     * @param address
     * @return ResponseEntity<Void>
     */
    @DeleteMapping(value = "firestation/{address}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteAddressFireStation(
            @PathVariable final String address) {
        AddressEntity addressFireStToDelete = null;
        addressFireStToDelete = addressService
                .deleteAnAddress(address);
        if (addressFireStToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
