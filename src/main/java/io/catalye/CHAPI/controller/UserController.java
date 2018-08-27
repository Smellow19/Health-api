package io.catalye.CHAPI.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.catalye.CHAPI.domain.Patient;
import io.catalye.CHAPI.domain.User;
import io.catalye.CHAPI.exceptions.FailedLogin;
import io.catalye.CHAPI.repositories.UserRepo;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepo userRepo;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@RequestParam String email, @RequestParam String password) {
		logger.warn("User email :" + email);
		if (userRepo.findByEmail(email) != null) {
			User user = userRepo.findByEmail(email);
			logger.warn("user " + user.toString());
			if (user.getPassword().equals(password)) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				throw new FailedLogin();
			}
		} else {
			logger.debug("User: not found ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/all_users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userRepo.findAll();
		if (users != null) {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);

		} else {
			logger.debug("User: not found ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
			if (userRepo.findByEmail(user.getEmail()) != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				userRepo.insert(user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			}
		} 

	@RequestMapping(value = "/update_user", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestParam String email, @RequestBody User user) {

		
			if (userRepo.findByEmail(user.getEmail()) != null) {
				logger.warn(userRepo.findByEmail(user.getEmail()).toString());
				logger.warn(user.toString());
				userRepo.save(user);
				return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
		} else {
			logger.warn("User Not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete_user", method = RequestMethod.DELETE)
	public ResponseEntity<Patient> deletePatient(@RequestParam String email) {
		User user = userRepo.findByEmail(email);
			if (user != null) {
				logger.warn(user+ " deleted");
				userRepo.delete(user);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} else {
				logger.warn("User Not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
