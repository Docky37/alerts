package com.safetynet.alerts.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import static org.mockito.Mockito.verify;

import com.safetynet.alerts.controller.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repositery.MedicalRecordRepository;
import com.safetynet.alerts.service.MedicalRecordService;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MedicalRecordServiceTest {

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    MedicalRecordService medicalRecordService;

    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    static {
        medicalRecordList
                .add(new MedicalRecord(1L, "John", "Boyd", "03/06/1984",
                        new String[] { "aznol:350mg", "hydrapermazol:100mg" },
                        new String[] { "nillacilan" }));
        medicalRecordList.add(new MedicalRecord(
                2L, "Jacob", "Boyd", "03/06/1989", new String[] {
                        "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" },
                new String[] {}));
        medicalRecordList.add(new MedicalRecord(3L, "Tenley", "Boyd",
                "03/06/1989", new String[] {}, new String[] { "peanut" }));

    }

    @Before
    public void SetUp() {
        medicalRecordService = new MedicalRecordService(
                medicalRecordRepository);
    }

    // POST >>> CREATE (Add a list of Person)
    @Test
    @Tag("TestA-CreateAListOfMedicalRecords")
    @DisplayName("A. Given a list of MedicalRecord to Add, when POST list,"
            + " then MedicalRecord are created.")
    public void a_givenAListOfMedicalRecordsToAdd_whenPostList_thenMedicalRecordsAreCreated()
            throws Exception {
        DateTimeFormatter Formatter=DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
;
        // GIVEN
LocalDate birthDate = LocalDate.parse("25/12/2010", Formatter);
System.out.println(Date.valueOf(birthDate));
        given(medicalRecordRepository.saveAll(Mockito.<MedicalRecord>anyList()))
                .willReturn(medicalRecordList);
        // WHEN
        List<MedicalRecord> addedList = medicalRecordService
                .addListMedicalRecord(medicalRecordList);
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
        given(medicalRecordRepository.findAll()).willReturn(medicalRecordList);
        // WHEN
        List<MedicalRecord> foundList = medicalRecordService.findAll();
        // THEN
        assertThat(foundList.get(0).getFirstName()).isEqualTo("John");
        assertThat(foundList.get(1).getFirstName()).isEqualTo("Jacob");
        assertThat(foundList.get(2).getFirstName()).isEqualTo("Tenley");
    }

    // GET ("/medicalRecord/{lastName}/{FirstName}") >>> READ (Find a Person)
    @Test
    @Tag("TestC-FindAMedicalRecord")
    @DisplayName("3.Given a MedicalRecord to find, when search MedicalRecord by last name & firstname,"
            + " then returns the MedicalRecord.")
    public void c1_givenAMedicalRecordToFind_whenFindByLastNameAndFirstName_thenReturnMedRecord()
            throws Exception {
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(medicalRecordList.get(0));

        MedicalRecord medicalRecord = medicalRecordService
                .findByLastNameAndFirstName("Boyd", "John");
        assertThat(medicalRecord.getLastName()).isEqualTo("Boyd");
        assertThat(medicalRecord.getFirstName()).isEqualTo("John");

    }

    // GET ("/medicalRecord/{lastName}/{FirstName}") >>> READ (Try to find an
    // Unknown MedicalRecord)
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
        MedicalRecord medicalRecord = medicalRecordService
                .findByLastNameAndFirstName(anyString(), anyString());
        // THEN
        assertThat(medicalRecord).isNull();
    }

    // POST >>> CREATE (Add a new Person)
    @Test
    @Tag("TestD-CreateAMedicalRecord")
    @DisplayName("4. Given a MedicalRecord to add, when POST the person, "
            + "then a new MedicalRecord is created.")
    public void d_givenAPersonToAdd_whenPostPerson_thenPersonIsCreated()
            throws Exception {
        // GIVEN
        MedicalRecord medicalRecordToAdd = medicalRecordList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(
                medicalRecordToAdd.getLastName(),
                medicalRecordToAdd.getFirstName())).willReturn(null,
                        medicalRecordList.get(2));
        given(medicalRecordRepository.save(any(MedicalRecord.class)))
                .willReturn(medicalRecordList.get(2));

        // WHEN
        MedicalRecord addedMedicalRecord = medicalRecordService
                .addMedicalRecord(medicalRecordToAdd);
        // THEN
        assertThat(addedMedicalRecord.getLastName()).isEqualTo("Boyd");
        assertThat(addedMedicalRecord.getFirstName()).isEqualTo("Tenley");

    }

    @Test // PUT >>> UPDATE (
    @Tag("TestE-UpdateAMedicalRecord")
    @DisplayName("5. Given a MedicalRecord to update, when save the MedicalRecord,"
            + " then this MedicalRecord is updated.")
    public void e_givenAMedicalRecordToUpdate_whenUpdate_thenReturnUpdatedMedicalRecord()
            throws Exception {
        // GIVEN
        MedicalRecord medicalRecordToUpdate = medicalRecordList.get(2);
        medicalRecordToUpdate.setBirthDate("06/03/1989");
        MedicalRecord updatedMedicalRecord = medicalRecordToUpdate;
        given(medicalRecordRepository.findByLastNameAndFirstName(
                updatedMedicalRecord.getLastName(),
                updatedMedicalRecord.getFirstName()))
                        .willReturn(medicalRecordList.get(2));
        given(medicalRecordRepository.save(any(MedicalRecord.class)))
                .willReturn(updatedMedicalRecord);

        // WHEN
        updatedMedicalRecord = medicalRecordService
                .updateMedicalRecord(medicalRecordToUpdate);
        // THEN
        assertThat(updatedMedicalRecord.getFirstName())
                .isEqualTo(medicalRecordToUpdate.getFirstName());
        assertThat(updatedMedicalRecord.getBirthDate())
                .isEqualTo(medicalRecordToUpdate.getBirthDate());
    }

    @Test // DELETE
    @Tag("TestF-DeleteAPerson")
    @DisplayName("6. Given a person to delete, when delete the person,"
            + " then find this person returns null.")
    public void f_givenAMedicalRecordToDelete_whenDelete_thenMedicalRecordIsDeleted()
            throws Exception {
        // GIVEN
        MedicalRecord medicalRecordToDelete = medicalRecordList.get(2);
        given(medicalRecordRepository.findByLastNameAndFirstName(anyString(),
                anyString())).willReturn(medicalRecordList.get(2));
        // WHEN
        medicalRecordService.deleteAMedicalRecord(
                medicalRecordToDelete.getLastName(),
                medicalRecordToDelete.getFirstName());
        // THEN
        verify(medicalRecordRepository).findByLastNameAndFirstName(
                medicalRecordToDelete.getLastName(),
                medicalRecordToDelete.getFirstName());
        verify(medicalRecordRepository).deleteById(any(Long.class));
    }

}