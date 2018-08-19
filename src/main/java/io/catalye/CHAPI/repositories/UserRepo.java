package io.catalye.CHAPI.repositories;

import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepo extends MongoRepository<User, String> {
	User findByEmailAddress(String email);

}


