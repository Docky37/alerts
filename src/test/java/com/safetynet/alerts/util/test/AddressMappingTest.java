package com.safetynet.alerts.util.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.utils.AddressMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressMapping.class)
public class AddressMappingTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private AddressMapping addressMapping;

    public static List<AddressDTO> addressDTOList = new ArrayList<>();
    static {
        addressDTOList
                .add(new AddressDTO("1509 Culver St", "1"));
        addressDTOList.add(new AddressDTO("29_15th_St", "1"));
        addressDTOList.add(new AddressDTO("834 Binoc Ave", "3"));
    }

    public static List<AddressEntity> addressEntityList = new ArrayList<>();
    static {
        addressEntityList
                .add(new AddressEntity(1L, "1509 Culver St", "1"));
        addressEntityList.add(new AddressEntity(2L, "29_15th_St", "1"));
        addressEntityList.add(new AddressEntity(3L, "834 Binoc Ave", "3"));
    }

    // AddressDTO list map to AddressEntity list Test
    @Test
    public void givenAListOfAddressDTO_whenMapToAddressEntity_thenReturnAddressEntityList()
            throws Exception {
        LOGGER.info("Start test: AddressDTO list mapping to AddressEntity list.");
        // GIVEN
        // WHEN
        List<AddressEntity> resultList = addressMapping.convertToAddressEntity(addressDTOList);
        // THEN
        assertThat(resultList.get(0).getAddress()).isEqualTo(addressEntityList.get(0).getAddress());
        assertThat(resultList.get(1).getAddress()).isEqualTo(addressEntityList.get(1).getAddress());
        assertThat(resultList.get(1).getStation()).isEqualTo(addressEntityList.get(1).getStation());
        assertThat(resultList.get(1).getStation()).isEqualTo(addressEntityList.get(1).getStation());
    }

    // AddressEntity list map to AddressDTO list Test
    @Test
    public void givenAListOfAddressEntity_whenMapToAddressDTO_thenReturnAddressDTO()
            throws Exception {
        LOGGER.info("Start test: AddressEntity list mapping to AddressDTO list.");
        // GIVEN
        // WHEN
        List<AddressDTO> resultList = addressMapping.convertToAddressDTO(addressEntityList);
        // THEN
        assertThat(resultList.get(0).getAddress()).isEqualTo(addressDTOList.get(0).getAddress());
        assertThat(resultList.get(1).getAddress()).isEqualTo(addressDTOList.get(1).getAddress());
        assertThat(resultList.get(1).getStation()).isEqualTo(addressDTOList.get(1).getStation());
        assertThat(resultList.get(1).getStation()).isEqualTo(addressDTOList.get(1).getStation());
    }

}
