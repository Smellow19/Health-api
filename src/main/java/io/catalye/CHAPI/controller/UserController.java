package io.catalye.CHAPI.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		logger.warn(email);
		logger.warn(userRepo.findByEmail(email).getPassword());
		logger.warn(password);
		if (userRepo.findByEmail(email) != null) {
			User user = userRepo.findByEmail(email);
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









}
	



//@RequestMapping(value = "/AllUsers", method = RequestMethod.GET)
//public ResponseEntity<List<User>> getUsers() {
//	users = userRepo.findAll();
//	return new ResponseEntity<>(user, HttpStatus.OK);
//	
//}
