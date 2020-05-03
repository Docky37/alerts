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

import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.service.IAddressService;

/**
 * AddressController class, used to create administrative endpoints for CRUD
 * operations on address-fire station mapping.
 *
 * @author Thierry Schreiner
 */
@RestController
public class AddressController {
    /**
     * The service class used to manage address - fire station association CRUD
     * operations.
     */
    private IAddressService addressService;

    /**
     * The Status code 501 Not implemented.
     */
    static final int CODE_501 = 501;

    /**
     * Class constructor - Set addressService (IoC).
     *
     * @param pFireStationService
     */
    public AddressController(final IAddressService pFireStationService) {
        addressService = pFireStationService;
    }

    /**
     * POST request to create address-fireStation mapping in DataBase from a
     * list of mapping.
     *
     * @param pAdressDTOList
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "firestations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final List<AddressDTO> pAdressDTOList) {

        List<AddressDTO> listFireStAdded = addressService
                .addListAddress(pAdressDTOList);

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
    public List<AddressDTO> findAll() {
        return addressService.findAll();
    }

    /**
     * POST request to add a new Address-FireStation in DB.
     *
     * @param pAdressDTO - The association to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(
            @RequestBody final AddressDTO pAdressDTO) {

        AddressDTO addressFireStAdded = addressService.addAddress(pAdressDTO);

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
     * @return AddressDTO
     * @throws AddressFireStationNotFoundException
     */
    @GetMapping(value = "firestation/{address}")
    public AddressDTO findByAddress(@PathVariable final String address)
            throws AddressNotFoundException {
        return addressService.findByAddress(address);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void addressFireStationNotFoundHandler(
            final AddressNotFoundException e) {

    }

    /**
     * PUT request to update an address - fire station association from
     * DataBase.
     *
     * @param address
     * @param pAdressDTO
     * @return ResponseEntity<Void>
     * @throws AddressNotFoundException
     */
    @PutMapping(value = "firestation/{address}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(@PathVariable final String address,
            @RequestBody final AddressDTO pAdressDTO)
            throws AddressNotFoundException {

        AddressDTO addressDTOUpdated = addressService.updateAddress(address,
                pAdressDTO);
        if (addressDTOUpdated == null) {
            return ResponseEntity.status(CODE_501).build();
        }
        return ResponseEntity.noContent().build();
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
        AddressDTO addressFireStToDelete = null;
        addressFireStToDelete = addressService.deleteAnAddress(address);
        if (addressFireStToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
