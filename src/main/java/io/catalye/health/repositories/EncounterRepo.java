package io.catalye.health.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.health.domain.Encounter;

public interface EncounterRepo extends MongoRepository<Encounter, String> {
	Encounter findBy_Id(String id);
	List<Encounter> findBypatientid(ObjectId objectId);

}

