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
import com.safetynet.alerts.model.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecordEntity;
import com.safetynet.alerts.utils.MedicalRecordMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordMapping.class)
public class MedicaleRecordMappingTest {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    @Autowired
    private MedicalRecordMapping medicalRecordMapping;

    public static List<MedicalRecordDTO> medRecDTOList = new ArrayList<>();
    static {
        medRecDTOList.add(new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                new String[] { "nillacilan" }));
        medRecDTOList.add(new MedicalRecordDTO(
                "Jacob", "Boyd", "03/06/1989", new String[] {
                        "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" },
                new String[] {}));
        medRecDTOList.add(new MedicalRecordDTO("Tenley", "Boyd", "03/06/1989",
                new String[] {}, new String[] { "peanut" }));
    }

    public static List<MedicalRecordEntity> medRecEntityList = new ArrayList<>();
    static {
        medRecEntityList
                .add(new MedicalRecordEntity(1L, "John", "Boyd", "03/06/1984",
                        new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                        new String[] { "nillacilan" }));
        medRecEntityList.add(new MedicalRecordEntity(
                2L, "Jacob", "Boyd", "03/06/1989", new String[] {
                        "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" },
                new String[] {}));
        medRecEntityList.add(new MedicalRecordEntity(3L, "Tenley", "Boyd",
                "03/06/1989", new String[] {}, new String[] { "peanut" }));
    }

    // MedicalRecordDTO list map to MedicalRecordEntity list Test
    @Test
    public void givenAListOfMedicalRecordDTO_whenMapToMedicalRecordEntity_thenReturnMedicalRecordEntityList()
            throws Exception {
        LOGGER.info(
                "Start test: MedicalRecordDTO list mapping to MedicalRecordEntity list.");
        // GIVEN
        // WHEN
        List<MedicalRecordEntity> resultList = medicalRecordMapping
                .convertToMedicalRecordEntity(medRecDTOList);
        // THEN
        assertThat(resultList.get(0).getFirstName())
                .isEqualTo(medRecDTOList.get(0).getFirstName());
        assertThat(resultList.get(0).getBirthdate())
                .isEqualTo(medRecDTOList.get(0).getBirthdate());
        assertThat(resultList.get(2).getFirstName())
                .isEqualTo(medRecDTOList.get(2).getFirstName());
        assertThat(resultList.get(2).getBirthdate())
                .isEqualTo(medRecDTOList.get(2).getBirthdate());
    }

    // AddressEntity list map to MedicalRecordDTO list Test
    @Test
    public void givenAListOfAddressEntity_whenMapToMedicalRecordDTO_thenReturnMedicalRecordDTO()
            throws Exception {
        LOGGER.info(
                "Start test: AddressEntity list mapping to MedicalRecordDTO list.");
        // GIVEN
        // WHEN
        List<MedicalRecordDTO> resultList = medicalRecordMapping
                .convertToMedicalRecordDTO(medRecEntityList);
        // THEN
        assertThat(resultList.get(0).getFirstName())
                .isEqualTo(medRecEntityList.get(0).getFirstName());
        assertThat(resultList.get(0).getBirthdate())
                .isEqualTo(medRecEntityList.get(0).getBirthdate());
        assertThat(resultList.get(2).getFirstName())
                .isEqualTo(medRecEntityList.get(2).getFirstName());
        assertThat(resultList.get(2).getBirthdate())
                .isEqualTo(medRecEntityList.get(2).getBirthdate());
    }

}
