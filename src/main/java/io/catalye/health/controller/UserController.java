package io.catalye.health.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.catalye.health.domain.Patient;
import io.catalye.health.domain.User;
import io.catalye.health.repositories.UserRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is the User controller class that handles all of the CRUD functionality
 * for the User Repository as well as Domain.
 * 
 * @author tBridges
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepo userRepo;

    /**
     * Finds all users in the database
     * 
     * @return a 200 and list of users if the database isn't empty, or a 404 if
     *         there is no content.
     */
    @RequestMapping(value = "/all_users", method = RequestMethod.GET)
    @ApiOperation("Finds all users in the database.")
    
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Users Found"),
            @ApiResponse(code = 404, message = "Users not found") })
    
    public ResponseEntity<List<User>> getUsers() {
        
        List<User> users = userRepo.findAll();
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * This creates a user inside of the database
     * 
     * @param user the information the user to be created
     * @return a 201 as well as the user param or a 409 if a user already exists
     *         with the same email.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/create_user", method = RequestMethod.POST)
    @ApiOperation("creates an user in the database.")
    
    @ApiResponses(value = { @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 409, message = "User already exists") })
    
    public ResponseEntity<User> createUser(@RequestBody User user) {
        
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);

            
        }
        
        else {
            userRepo.insert(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
           
        }
    }

    /**
     * This updates a user by seaching the database via passed in email
     * 
     * @param email searches database for user
     * @param user  json object that holds user information for updating
     * @return a 202 if the user is accepted or a 404 if the user is not found.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update_user", method = RequestMethod.PUT)
    @ApiOperation("updates an user in the database.")
    
    @ApiResponses(value = { @ApiResponse(code = 204, message = "User updated"),
            @ApiResponse(code = 404, message = "User not found") })
    
    
    public ResponseEntity<User> updateUser(@RequestParam String email, @RequestBody User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            
            logger.warn(user.toString());
            userRepo.save(user);
            return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
        }
        
        else {
            logger.warn("User Not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This searches the database for a user by email, then deletes them if they
     * exist
     * 
     * @param email
     * @return 202 if the user is found and deleted, a 404 if the user is not found.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete_user", method = RequestMethod.DELETE)
    public ResponseEntity<Patient> deletePatient(@RequestParam String email) {
        boolean truth = userRepo.findByEmail(email).isPresent();
            if (truth) {
                User user = userRepo.findByEmail(email).get();
                logger.warn(user+ " deleted");
                userRepo.delete(user);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                logger.warn("User Not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
