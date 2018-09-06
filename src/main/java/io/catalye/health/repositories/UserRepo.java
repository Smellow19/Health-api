package io.catalye.health.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.health.domain.User;

public interface UserRepo extends MongoRepository<User, String> {
	User findByEmail(String email);

}
