package com.safetynet.alerts.repositery;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.alerts.model.MedicalRecord;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

    MedicalRecord findByLastNameAndFirstName(final String lastName, final String firstName);

}
