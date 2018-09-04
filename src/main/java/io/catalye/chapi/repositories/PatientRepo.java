package io.catalye.chapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.chapi.domain.Patient;
import io.catalye.chapi.domain.User;

public interface PatientRepo extends MongoRepository<Patient, String> {
	Patient findByssn(String ssn);

}
