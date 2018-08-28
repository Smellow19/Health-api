package io.catalye.CHAPI.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.CHAPI.domain.Encounter;

public interface EncounterRepo extends MongoRepository<Encounter, String> {
	Encounter findBy_Id(String id);

	List<Encounter> findBypatientid(String patientid);

}
