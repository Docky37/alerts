package com.safetynet.alerts.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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

import com.safetynet.alerts.AlertsApplication;
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
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

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
    @PostMapping(value = "firestation/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> importAddressList(
            @RequestBody final List<AddressDTO> pAdressDTOList) {
        LOGGER.info("NEW HTML ADMINISTRATIVE POST REQUEST"
                + " on http://localhost:8080/firestation/batch");
        LOGGER.info(" AddressController  >>> Import a Address list");
        LOGGER.info(" => List = {}", pAdressDTOList.toString());

        List<AddressDTO> listFireStAdded = addressService
                .addListAddress(pAdressDTOList);

        if (listFireStAdded == null) {
            LOGGER.info("END of HTML administrative POST Request"
                    + "with Status 200 OK");
            // return ResponseEntity.noContent().build();
            return new ResponseEntity<Object>(addressService.getBalanceSheet(),
                    new HttpHeaders(), HttpStatus.CREATED);
        }

        LOGGER.info("END of HTML administrative POST Request"
                + "with Status 201 Created");
        return new ResponseEntity<Object>(addressService.getBalanceSheet(),
                new HttpHeaders(), HttpStatus.CREATED);

    }

    /**
     * GET request to find all address - fire station associations in DataBase.
     *
     * @return a List of all address - fire station associations in DB
     */
    @GetMapping(value = "/firestation")
    public List<AddressDTO> findAll() {
        LOGGER.info("NEW HTML ADMINISTRATIVE GET REQUEST"
                + " on http://localhost:8080/firestation");
        LOGGER.info(" AddressController  >>> Get all addresses.");
        List<AddressDTO> resultList = addressService.findAll();
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 200 OK ---");
        return resultList;
    }

    /**
     * POST request to add a new Address in DB.
     *
     * @param pAdressDTO - The association to add in DB
     * @return ResponseEntity<Void>
     */
    @PostMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(
            @RequestBody final AddressDTO pAdressDTO) {
        LOGGER.info("NEW HTML ADMINISTRATIVE POST REQUEST"
                + " on http://localhost:8080/firestation");
        LOGGER.info(" AddressController  >>> Add the address '{}'", pAdressDTO);

        AddressDTO addressAdded = addressService.addAddress(pAdressDTO);

        if (addressAdded == null) {
            LOGGER.info("END of HTML administrative POST Request"
                    + " with Status 400 Bad Request");
            return new ResponseEntity<Object>(
                    "Cannot create this Address '" + pAdressDTO.getAddress()
                            + "' that is already registred. "
                            + "Consider PUT request if you want to update it.",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{address}").buildAndExpand(addressAdded.getAddress(),
                        addressAdded.getStation())
                .toUri();
        LOGGER.info("END of HTML administrative POST Request"
                + " with Status 201 Created");
        return ResponseEntity.created(location).build();
    }

    /**
     * GET request to find one address.
     *
     * @param address
     * @return AddressDTO
     * @throws AddressFireStationNotFoundException
     */
    @GetMapping(value = "firestation/{address}")
    public AddressDTO findByAddress(@PathVariable final String address)
            throws AddressNotFoundException {
        LOGGER.info("NEW HTML ADMINISTRATIVE GET REQUEST"
                + " on http://localhost:8080/firestation");
        LOGGER.info(" AddressController  >>> Get the address: '{}'", address);
        AddressDTO result = addressService.findByAddress(address);
        LOGGER.info(
                "END of HTML administrative GET Request with Status 200 OK");
        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void addressFireStationNotFoundHandler(
            final AddressNotFoundException e) {
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 404 NOT FOUND");
    }

    /**
     * PUT request to update an address - fire station association from
     * DataBase.
     *
     * @param address
     * @param pAddressDTO
     * @return ResponseEntity<Void>
     * @throws AddressNotFoundException
     */
    @PutMapping(value = "firestation/{address}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> update(@PathVariable final String address,
            @RequestBody final AddressDTO pAddressDTO)
            throws AddressNotFoundException {
        LOGGER.info("NEW HTML ADMINISTRATIVE PUT REQUEST"
                + " on http://localhost:8080/firestation");
        LOGGER.info("AddressController >>> Update the address '{}',", address);
        LOGGER.info(" with content: '{}'.", pAddressDTO.toString());

        AddressDTO addressDTOUpdated = addressService.updateAddress(address,
                pAddressDTO);
        if (addressDTOUpdated == null) {
            LOGGER.info("END of HTML administrative GET Request"
                    + " with Status 501 Not Implemented");
            // return ResponseEntity.status(CODE_501).build();
            return new ResponseEntity<Object>(
                    "It is not allowed to rename '" + address + " as "
                            + pAddressDTO.getAddress() + ".",
                    new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED);
        }
        LOGGER.info("END of HTML administrative GET Request"
                + " with Status 204 No Content");
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
        LOGGER.info("NEW HTML ADMINISTRATIVE DELETE REQUEST"
                + " on http://localhost:8080/firestation");
        LOGGER.info("AddressController >>> Delete the address '{}',", address);
        AddressDTO addressFireStToDelete = null;
        addressFireStToDelete = addressService.deleteAnAddress(address);
        if (addressFireStToDelete == null) {
            LOGGER.info("END of HTML administrative GET Request"
                    + " with Status 404 NOT FOUND");
            return ResponseEntity.notFound().build();
        }
        LOGGER.info(
                "END of HTML administrative GET Request with Status 200 OK");
        return ResponseEntity.ok().build();
    }

}
