package io.catalye.CHAPI.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.CHAPI.domain.User;

public interface UserRepo extends MongoRepository<User, String> {
	User findByEmail(String email);

	Optional<User> findById(Long id);

}
