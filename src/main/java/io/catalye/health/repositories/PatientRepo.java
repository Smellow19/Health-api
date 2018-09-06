package io.catalye.health.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.health.domain.Patient;
import io.catalye.health.domain.User;

public interface PatientRepo extends MongoRepository<Patient, String> {
	Patient findByssn(String ssn);

}
