package io.catalye.chapi.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.chapi.domain.Encounter;

public interface EncounterRepo extends MongoRepository<Encounter, String> {
	Encounter findBy_Id(String id);

	List<Encounter> findBypatientid(ObjectId objectId);

}
