package io.catalye.CHAPI.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.CHAPI.domain.Patient;
import io.catalye.CHAPI.domain.User;


public interface PatientRepo extends MongoRepository<Patient, String> {
	

}

