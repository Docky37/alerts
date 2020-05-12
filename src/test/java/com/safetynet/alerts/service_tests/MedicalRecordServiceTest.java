package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.safetynet.alerts.DTO.MedicalRecordDTO;
import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.AddressEntity;
import com.safetynet.alerts.model.MedicalRecordEntity;
import com.safetynet.alerts.model.PersonEntity;
import com.safetynet.alerts.repositery.MedicalRecordRepository;
import com.safetynet.alerts.repositery.PersonRepository;
import com.safetynet.alerts.service.IMedicalRecordService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.utils.MedicalRecordMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MedicalRecordServiceTest {

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    MedicalRecordMapping medicalRecordMapping;

    @Autowired
    IMedicalRecordService medicalRecordService;

    public static AddressEntity addressEntity = new AddressEntity(1L,
            "1509 Culver St", "3");

    public static PersonEntity personEntity = new PersonEntity(1L, "John",
            "Boyd", addressEntity, "841-874-6512", "jaboyd@email.com", null);

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

    @Before
    public void SetUp() {
        medicalRecordService = new MedicalRecordService(medicalRecordRepository,
                personRepository, medicalRecordMapping);
    }

    // POST >>> CREATE (Add a list of PersonDTO)
    @Test
    @Tag("TestA-CreateAListOfMedicalRecords")
    @DisplayName("A. Given a list of MedicalRecordEntity to Add, when POST list,"
            + " then MedicalRecordEntity are created.")
    public void a_givenAListOfMedicalRecordsToAdd_whenPostList_thenMedicalRecordsAreCreated()
            throws Exception {
        // GIVEN
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(personEntity);
        given(medicalRecordMapping
                .convertToMedicalRecordEntity(any(MedicalRecordDTO.class)))
                        .willReturn(medRecEntityList.get(0),
                                medRecEntityList.get(1),
                                medRecEntityList.get(2));
        given(medicalRecordRepository.save(any(MedicalRecordEntity.class)))
                .willReturn(medRecEntityList.get(0), medRecEntityList.get(1),
                        medRecEntityList.get(2));
        given(medicalRecordMapping
                .convertToMedicalRecordDTO(any(MedicalRecordEntity.class)))
                        .willReturn(medRecDTOList.get(0), medRecDTOList.get(1),
                                medRecDTOList.get(2));
        // WHEN
        List<MedicalRecordDTO> addedList = medicalRecordService
                .addListMedicalRecord(medRecDTOList);
        // THEN
        assertThat(addedList.size()).isEqualTo(3);

    }

    // GET ("/medicalRecord")>>> READ (Find all persons)
    @Test
    @Tag("TestB-FindAllMedicalRecords")
    @DisplayName("2. Given MedicalRecords in database, when findAll,"
            + " then returns the list of all MedicalRecords.")
    public void b_givenAllMedicalRecordsToFind_whenFindAll_thenReturnListOfAllMedicalRecords()
            throws Exception {
        // GIVEN
        given(medicalRecordRepository.findAll()).willReturn(medRecEntityList);
        given(medicalRecordMapping.convertToMedicalRecordDTO(
                Mockito.<MedicalRecordEntity>anyList()))
                        .willReturn(medRecDTOList);
        // WHEN
        List<MedicalRecordDTO> foundList = medicalRecordService.findAll();
        // THEN
        assertThat(foundList.get(0).getFirstName()).isEqualTo("John");
        assertThat(foundList.get(1).getFirstName()).isEqualTo("Jacob");
        assertThat(foundList.get(2).getFirstName()).isEqualTo("Tenley");
    }

    // GET ("/medicalRecord/{lastName}/{FirstName}") >>> READ (Find a PersonDTO)
    @Test
    @Tag("TestC-FindAMedicalRecord")
    @DisplayName("3.Given a MedicalRecordEntity to find, when search MedicalRecordEntity by last name & firstname,"
            + " then returns the MedicalRecordEntity.")
    public void c1_givenAMedicalRecordToFind_whenFindByLastNameAndFirstName_thenReturnMedRecord()
            throws Exception {
        // GIVEN
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(medRecEntityList.get(0));
        given(medicalRecordMapping
                .convertToMedicalRecordDTO(medRecEntityList.get(0)))
                        .willReturn(medRecDTOList.get(0));
        // WHEN
        MedicalRecordDTO medicalRecordDTO = medicalRecordService
                .findByLastNameAndFirstName("Boyd", "John");
        // THEN
        assertThat(medicalRecordDTO.getLastName()).isEqualTo("Boyd");
        assertThat(medicalRecordDTO.getFirstName()).isEqualTo("John");

    }

    // GET ("/medicalRecord/{lastName}/{FirstName}") >>> READ (Try to find an
    // Unknown MedicalRecordEntity)
    @Test(expected = MedicalRecordNotFoundException.class)
    @Tag("TestC2-FindAMedicalReport")
    @DisplayName("3.Given a stranger to find, when search person by last name & firstname,"
            + " then returns null.")
    public void c2_givenAStrangerToFind_whenFindPersonByLastNameAndFirstName_thenNotFoundException()
            throws Exception {
        // GIVEN
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        MedicalRecordDTO medicalRecordDTO = medicalRecordService
                .findByLastNameAndFirstName(anyString(), anyString());
        // THEN
        assertThat(medicalRecordDTO).isNull();
    }

    // POST >>> CREATE (Add a new MedicalRecordEntity)
    @Test
    @Tag("TestD-CreateAMedicalRecord")
    @DisplayName("4. Given a MedicalRecordEntity to add, when POST the person, "
            + "then a new MedicalRecordEntity is created.")
    public void d1_givenAPersonToAdd_whenPostPerson_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(personEntity);
        given(medicalRecordMapping
                .convertToMedicalRecordEntity(any(MedicalRecordDTO.class)))
                        .willReturn(medRecEntityList.get(0));
        given(medicalRecordRepository.save(any(MedicalRecordEntity.class)))
                .willReturn(medRecEntityList.get(0));
        given(medicalRecordMapping
                .convertToMedicalRecordDTO(any(MedicalRecordEntity.class)))
                        .willReturn(medRecDTOList.get(0));
        // WHEN
        MedicalRecordDTO addedMedicalRecord = medicalRecordService
                .addMedicalRecord(medRecDTOList.get(0));
        // THEN
        assertThat(addedMedicalRecord.getLastName()).isEqualTo("Boyd");
        assertThat(addedMedicalRecord.getFirstName()).isEqualTo("John");

    }

    // POST >>> CREATE (Try to add an existing MedicalRecordEntity)
    @Test
    @Tag("TestD-CreateAMedicalRecord")
    @DisplayName("4. Given a MedicalRecordEntity to add that already exist, when POST, "
            + "then returns null.")
    public void d2_givenAMedicalRecordToAddThatExists_whenPost_thenReturnNull()
            throws Exception {
        // GIVEN
        MedicalRecordDTO medicalRecordToAdd = medRecDTOList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(medRecEntityList.get(2));
        // WHEN
        medicalRecordToAdd = medicalRecordService
                .addMedicalRecord(medicalRecordToAdd);
        // THEN
        verify(medicalRecordRepository).findByLastNameAndFirstName(anyString(),
                anyString());
        verify(personRepository, never())
                .findByLastNameAndFirstName(anyString(), anyString());
        verify(medicalRecordRepository, never())
                .save(any(MedicalRecordEntity.class));
    }

    // POST >>> CREATE (Try to add an orphan MedicalRecordEntity)
    @Test
    @Tag("TestD-CreateAnOrphanMedicalRecord")
    @DisplayName("4. Given an Orphan MedicalRecordEntity to add, when POST the person, "
            + "then a new MedicalRecordEntity is created.")
    public void d3_givenAnOrphanMedicalRecord_whenPost_thenReturnNull()
            throws Exception {
        // GIVEN
        MedicalRecordDTO medicalRecordToAdd = medRecDTOList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        medicalRecordToAdd = medicalRecordService
                .addMedicalRecord(medicalRecordToAdd);
        // THEN
        verify(medicalRecordRepository).findByLastNameAndFirstName(anyString(),
                anyString());
        verify(personRepository).findByLastNameAndFirstName(anyString(),
                anyString());
        verify(medicalRecordMapping, never())
                .convertToMedicalRecordEntity(any(MedicalRecordDTO.class));
        verify(medicalRecordRepository, never())
                .save(any(MedicalRecordEntity.class));
    }

    @Test // PUT >>> UPDATE a MedicalRecordEntity
    @Tag("TestE-UpdateAMedicalRecord")
    @DisplayName("5. Given a MedicalRecordEntity to update, when save the MedicalRecordEntity,"
            + " then this MedicalRecordEntity is updated.")
    public void e_givenAMedicalRecordToUpdate_whenUpdate_thenReturnUpdatedMedicalRecord()
            throws Exception {
        // GIVEN
        MedicalRecordDTO medicalRecordToUpdate = medRecDTOList.get(2);
        medicalRecordToUpdate.setBirthdate("06/03/1989");
        MedicalRecordEntity updatedMedicalRecord = medRecEntityList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(medRecEntityList.get(2));
        given(medicalRecordMapping
                .convertToMedicalRecordEntity(any(MedicalRecordDTO.class)))
                        .willReturn(updatedMedicalRecord);
        given(medicalRecordRepository.save(any(MedicalRecordEntity.class)))
                .willReturn(updatedMedicalRecord);
        given(medicalRecordMapping
                .convertToMedicalRecordDTO(any(MedicalRecordEntity.class)))
                        .willReturn(medicalRecordToUpdate);
        // WHEN
        MedicalRecordDTO updatedMedRecDTO = medicalRecordService
                .updateMedicalRecord(anyString(), anyString(),
                        medicalRecordToUpdate);
        // THEN
        assertThat(updatedMedRecDTO.getFirstName())
                .isEqualTo(medicalRecordToUpdate.getFirstName());
        assertThat(updatedMedRecDTO.getBirthdate())
                .isEqualTo(medicalRecordToUpdate.getBirthdate());
    }

    @Test
    @Tag("TestF-DeleteAPerson")
    @DisplayName("6. Given a person to delete, when delete the person,"
            + " then find this person returns null.")
    public void f1_givenAMedicalRecordToDelete_whenDelete_thenMedicalRecordIsDeleted()
            throws Exception {
        // GIVEN
        MedicalRecordDTO medicalRecordToDelete = medRecDTOList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(medRecEntityList.get(2));
        given(personRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(personEntity);
        given(medicalRecordMapping
                .convertToMedicalRecordDTO(any(MedicalRecordEntity.class)))
                        .willReturn(medRecDTOList.get(0));
        // WHEN
        medicalRecordService.deleteAMedicalRecord(
                medicalRecordToDelete.getLastName(),
                medicalRecordToDelete.getFirstName());
        // THEN
        verify(medicalRecordRepository).findByLastNameAndFirstName(anyString(),
                anyString());
        verify(medicalRecordRepository).deleteById(any(Long.class));
    }

    @Test(expected = MedicalRecordNotFoundException.class) // DELETE
    @Tag("TestF-DeleteAMedicalRecord")
    @DisplayName("6. Given a unknown MedicalRecordEntity to delete, when delete,"
            + " returns null.")
    public void f2_givenAnUnknownMedicalRecordToDelete_whenDelete_then404()
            throws Exception {
        // GIVEN
        MedicalRecordDTO medicalRecordToDelete = medRecDTOList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(null);
        // WHEN
        medicalRecordService.deleteAMedicalRecord(
                medicalRecordToDelete.getLastName(),
                medicalRecordToDelete.getFirstName());
        // THEN
        verify(medicalRecordRepository).findByLastNameAndFirstName(
                medicalRecordToDelete.getLastName(),
                medicalRecordToDelete.getFirstName());
        verify(medicalRecordRepository, never()).deleteById(any(Long.class));
    }

}
