package io.catalye.chapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.chapi.domain.User;

public interface UserRepo extends MongoRepository<User, String> {
	User findByEmail(String email);

}
