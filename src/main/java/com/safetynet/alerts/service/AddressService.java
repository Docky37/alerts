package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.repositery.AddressRepository;
import com.safetynet.alerts.utils.AddressMapping;

import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.controller.AddressNotFoundException;

/**
 * AddressService is the class in charge of the business work about adress -
 * fire station association.
 *
 * @author Thierry Schreiner
 */
@Service
public class AddressService implements IAddressService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AddressService.class);

    /**
     * The balanceSheet is a report of the batch import of address list.
     */
    private String balanceSheet;

    /**
     * Getter of balanceSheet.
     *
     * @return the balanceSheet
     */
    @Override
    public String getBalanceSheet() {
        return balanceSheet;
    }

    /**
     * Used to add line break in String.
     */
    private String newLine = System.getProperty("line.separator");

    /**
     * PersonRepository is an Interface that extends CrudRepository.
     */
    private AddressRepository repository;
    /**
     * AddressMapping is an utility that provides bidirectional conversion
     * between AddressDTO and AddressEntity.
     */
    @Autowired
    private AddressMapping addressMapping;

    /**
     * Class constructor - Set addressFireStRepository (IoC).
     *
     * @param pAddressFireStRepositery
     * @param pAddressMapping
     */
    public AddressService(final AddressRepository pAddressFireStRepositery,
            final AddressMapping pAddressMapping) {
        repository = pAddressFireStRepositery;
        addressMapping = pAddressMapping;
    }

    /**
     * The find all method is used to get a list of all addresses (with each
     * corresponding covering station) saved in the DataBase. The method is
     * called by a GET request on /firestation.
     *
     * @return a List<AddressEntity> instance
     */
    @Override
    public List<AddressDTO> findAll() {
        LOGGER.debug(" | AddressService 'Find all addresses' start -->");
        List<AddressEntity> addressEntityList = (List<AddressEntity>) repository
                .findAll();
        List<AddressDTO> addressDTOList = addressMapping
                .convertToAddressDTO(addressEntityList);
        if (addressDTOList.size() > 0) {
            for (AddressDTO addressDTO : addressDTOList) {
                LOGGER.debug(" |  {}", addressDTO.toString());
            }
        } else {
            LOGGER.error(" | !   NO ADDRESS IN DATABASE!");
        }
        LOGGER.debug(" | End of AddressService 'Find all addresses.' ---");
        return addressDTOList;
    }

    /**
     * The addListFireStations method is used to add a list of addresses (with
     * each corresponding covering station) in the DataBase. The method is
     * called by the POST request on /firestations.
     *
     * @param pListAddress - a List<AddressDTO> instance
     * @return a List<AddressEntity> instance
     */
    @Override
    public List<AddressDTO> addListAddress(
            final List<AddressDTO> pListAddress) {
        LOGGER.debug(" | AddressService 'Add a list of addresses' start -->");
        balanceSheet = "";
        List<AddressDTO> createdList = new ArrayList<AddressDTO>();
        AddressDTO createAddressDTO;
        int countOfCreatedAddress = 0;
        int countOfRejectedAddress = 0;
        for (AddressDTO addressDTO : pListAddress) {
            createAddressDTO = addAddress(addressDTO);
            if (createAddressDTO == null) { // MedicalRecord not created.
                countOfRejectedAddress++;
                if (balanceSheet.isEmpty()) {
                    balanceSheet = "These registred addresses have not been"
                            + " created again, to avoid duplicates: " + newLine;
                }
                balanceSheet = balanceSheet
                        .concat("   -  " + addressDTO.toString()) + newLine;
            } else {
                createdList.add(createAddressDTO);
                countOfCreatedAddress++;
            }
        }
        balanceSheet = balanceSheet.concat(newLine + "Balance sheet: "
                + countOfCreatedAddress + " addresses have been created and "
                + countOfRejectedAddress + " rejected.");
        LOGGER.debug(" | Balance sheet:  {} addresses created and {} rejected.",
                countOfCreatedAddress, countOfRejectedAddress);
        LOGGER.debug(" | End of AddressService 'Add a list of addresses.' ---");

        if (createdList.size() > 0) {
            return createdList;
        }
        return new ArrayList<AddressDTO>();
    }

    /**
     * The addAddressFireStation method is used to add one new address with its
     * corresponding covering station in database. The method is called by the
     * POST request on /firestation.
     *
     * @param pAddressDTO an AddressEntity instance
     * @return an AddressEntity instance
     */
    @Override
    public AddressDTO addAddress(final AddressDTO pAddressDTO) {
        LOGGER.debug(" | AddressService 'Add an address' start -->");
        AddressEntity foundAddress = repository
                .findByAddress(pAddressDTO.getAddress());
        if (foundAddress == null) {
            AddressEntity addedAddress = addressMapping
                    .convertToAddressEntity(pAddressDTO);
            addedAddress = repository.save(addedAddress);
            LOGGER.debug(
                    " |   + Address '{}' covered by station {}"
                            + " has been successfully created.",
                    pAddressDTO.getAddress(), pAddressDTO.getStation());
            LOGGER.debug(" | End of AddressService 'Add an address'. ---");
            return addressMapping.convertToAddressDTO(addedAddress);
        } else {
            LOGGER.error(
                    " | !   Cannot create this Address '{}' "
                            + "because it already exists!",
                    pAddressDTO.getAddress());
            LOGGER.debug(" | End of AddressService 'Add an address'. ---");
            return null;
        }
    }

    /**
     * The findByAddress method is used to find an address with its
     * corresponding covering station in database. The method is called by the
     * GET request on /firestation/{address}.
     *
     * @param pAddress a String
     * @return an AddressEntity instance
     * @throws AddressFireStationNotFoundException
     */
    @Override
    public AddressDTO findByAddress(final String pAddress)
            throws AddressNotFoundException {
        LOGGER.debug(" | AddressService 'Find the address {}' start -->",
                pAddress);
        AddressEntity addressEntity = repository.findByAddress(pAddress);
        if (addressEntity == null) {
            LOGGER.error(" |  Address not found!");
            LOGGER.debug(" | End of AddressService 'Find an address.' ---");

            throw new AddressNotFoundException();
        }
        AddressDTO resultAddressDTO = addressMapping
                .convertToAddressDTO(addressEntity);
        LOGGER.debug(" |  {}", resultAddressDTO.toString());
        LOGGER.debug(" | End of AddressService 'Find an address.' ---");
        return resultAddressDTO;
    }

    /**
     * The updateAddress method is used to update an address or its
     * corresponding covering station in database. The method is called by the
     * PUT request on /firestation.
     *
     * @param pAddressDTO an AddressDTO instance
     * @return an AddressEntity instance
     * @throws AddressNotFoundException
     */
    @Override
    public AddressDTO updateAddress(final String pAdressToUpdate,
            final AddressDTO pAddressDTO) throws AddressNotFoundException {
        LOGGER.debug(" | AddressService 'Update the address {}' start -->",
                pAddressDTO.getAddress());
        AddressEntity foundAddressEntity = repository
                .findByAddress(pAdressToUpdate);
        if (foundAddressEntity == null) {
            LOGGER.error(" | !   Cannot update this unregistered address '{}'!",
                    pAdressToUpdate);
            LOGGER.debug(" | End of AddressService 'Update an address'. ---");

            throw new AddressNotFoundException();
        } else if (foundAddressEntity.getAddress()
                .contentEquals(pAddressDTO.getAddress())) {
            AddressEntity updateAddressEntity = foundAddressEntity;
            updateAddressEntity.setStation(pAddressDTO.getStation());
            updateAddressEntity = repository.save(updateAddressEntity);
            LOGGER.debug(" |   OK now Address '{}' is covered by station {}.'",
                    updateAddressEntity.getAddress(),
                    updateAddressEntity.getStation());

            LOGGER.debug(" | End of AddressService 'Add an address'. ---");
            return addressMapping.convertToAddressDTO(updateAddressEntity);
        }
        LOGGER.error(" | !  It is not allowed to rename '{} as {}.'",
                pAdressToUpdate, pAddressDTO.getAddress());
        LOGGER.debug(" | End of AddressService 'Update an address'. ---");
        return null;
    }

    /**
     * The deleteAnAddress method is used to remove an address (and its
     * corresponding covering station of database. The method is called by the
     * DELETE request on /firestation/{address}.
     *
     * @param pAddressToDelete a String
     * @return an AddressEntity instance
     */
    @Override
    public AddressDTO deleteAnAddress(final String pAddressToDelete) {
        LOGGER.debug(" | AddressService 'Delete the address {}' start -->",
                pAddressToDelete);
        AddressEntity foundAddress = repository.findByAddress(pAddressToDelete);
        if (foundAddress != null) {
            repository.deleteById(foundAddress.getId());
            AddressDTO deletedAddressDTO = addressMapping
                    .convertToAddressDTO(foundAddress);
            LOGGER.debug(" |   OK now Address '{}' is deleted.'",
                    deletedAddressDTO);
            LOGGER.debug(" | End of AddressService 'Delete an address.' ---");
            return deletedAddressDTO;
        }
        LOGGER.error(" |  ADDRESS NOT FOUND!");
        LOGGER.debug(" | End of AddressService 'Delete an address.' ---");
        return null;
    }

}
