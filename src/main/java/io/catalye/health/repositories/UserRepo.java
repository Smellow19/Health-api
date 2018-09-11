package io.catalye.health.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.catalye.health.domain.User;

public interface UserRepo extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

}
