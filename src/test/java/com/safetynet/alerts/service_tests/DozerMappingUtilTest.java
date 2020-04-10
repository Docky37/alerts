package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetynet.alerts.model.AddressFireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.AddressFireStationRepository;
import com.safetynet.alerts.service.DozerMappingUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(DozerMappingUtil.class)
public class DozerMappingUtilTest {

    @MockBean
    private AddressFireStationRepository addressFireStationRepository;

    private DozerMappingUtil dozerMapingUtil;

    public static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person(1L, "John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person(2L, "Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person(3L, "Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }
    public static AddressFireStation addressFireStation = new AddressFireStation(
            1L, "1509 Culver St", "3");

    @Test
    @Tag("TestPersonToPersonEntityConversion")
    @DisplayName("Given a person to convert, when convert, then returns PersonEntity.")
    public void givenAPerson_whenConvertTo_thenReturnsAPersonEntity() {
        // GIVEN
        AddressFireStation addressFS = new AddressFireStation();
        given(addressFireStationRepository.findByAddress(anyString()))
                .willReturn(addressFireStation);
        // WHEN
        AddressFireStation convertedAddress = dozerMapingUtil
                .convertTo(personList.get(0).getAddress(),addressFS);
        // THEN
        assertThat(convertedAddress).isEqualTo(addressFireStation);
    }

}
