package io.catalye.health.controller;

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

import io.catalye.health.domain.Patient;
import io.catalye.health.repositories.EncounterRepo;
import io.catalye.health.repositories.PatientRepo;
import io.catalye.health.validation.Validation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is the patient controller class that handles all of the CRUD
 * functionality for the Patients Repository as well as Domain.
 * 
 * @author tBridges
 *
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

	Validation validation = new Validation();

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientRepo patientRepo;

	@Autowired
	EncounterRepo encounterRepo;

	/**
	 * This gets all patients
	 * 
	 * @return 200 if the patients are found 404 if the patients are not found
	 */
	@RequestMapping(value = "/all_patients", method = RequestMethod.GET)
	@ApiOperation("Finds all patients in the database.")
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Patients Found"),
			@ApiResponse(code = 404, message = "Patients not found") })
	
	public ResponseEntity<List<Patient>> getPatients() {
	    
		logger.warn("Repo is " + patientRepo.findAll().size() + " Patients Long");
		List<Patient> patients = patientRepo.findAll();
		
		if (patients != null) {
			return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
		}
		
		else {
			logger.debug("Repo empty");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This gets a patient by their social security number
	 * 
	 * @param ssn
	 * @return 200 if the patient is found 404 if the patient isnt found
	 */
	@RequestMapping(value = "/find_patient", method = RequestMethod.GET)
	@ApiOperation("Finds a single patient in the database.")
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Patient Found"),
			@ApiResponse(code = 404, message = "Patient not found") })
	
	public ResponseEntity<Patient> getPatient(@RequestParam String ssn) {
	    
		Patient patient = patientRepo.findByssn(ssn);
		
		if (patient != null) {
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);

		} 
		
		else {
			logger.debug("User: not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This handles creating a patient
	 * 
	 * @param patient is the json object being sent in
	 * @return a 201 if the patient is created, a 409 if the patient already exists,
	 *         and a 404 if the patient is not found.
	 */
	@RequestMapping(value = "/create_patient", method = RequestMethod.POST)
	@ApiOperation("Creates a new patient in the database.")
	
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Patient created"),
			@ApiResponse(code = 409, message = "Patient already exists"),
			@ApiResponse(code = 404, message = "Patient not found") })
	
	
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
	    
		boolean validPatient = patient.validateNotNullElements(patient);
		
		if (validPatient) {
		    
			if (patientRepo.findByssn(patient.getSsn()) != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			
			else {
				patientRepo.insert(patient);
				return new ResponseEntity<Patient>(patient, HttpStatus.CREATED);
			}
			
		}
		
		else {
			logger.warn("User: not Valid ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This handles updating a patient as well as validating all the incoming data
	 * against the validation file.
	 * 
	 * @param ssn     Takes in a ssn to search the repo for the matching record
	 * @param patient the json object that contains the information for updating
	 * @return a 202 if the patient is updated, a 400 if the patient is not found,
	 *         and a 400 if the patient fails validation.
	 */
	@RequestMapping(value = "/update_patient", method = RequestMethod.PUT)
	@ApiOperation("Updates a patient in the database.")
	
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Patient updated"),
			@ApiResponse(code = 400, message = "Patient failed validation"),
			@ApiResponse(code = 404, message = "Patient not found") })
	
	public ResponseEntity<Patient> createPatient(@RequestParam String ssn, @RequestBody Patient patient) {
	    
		boolean validPatient = patient.validateNotNullElements(patient);
		
		if (validPatient) {
		    
			if (patientRepo.findByssn(patient.getSsn()) == null) {
			    
				logger.warn("User: not found ");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			else {
				patientRepo.save(patient);
				return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
			}
			
		} 
		
		else {
			logger.warn("User not Valid");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * This deletes a patient if they have no encounters
	 * 
	 * @param ssn        This searches the Repository for the record based on the
	 *                   SSN
	 * @param encounters This is a check to see if the patient has encounters
	 * @return a 202 if the patient has no encounters and is deleted, a 409 if the
	 *         patient has encounters and can not be deleted, and a 404 if the
	 *         patient is not found.
	 */
	@RequestMapping(value = "/delete_patient", method = RequestMethod.DELETE)
	@ApiOperation("deletes a patient in the database.")
	
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Patient Deleted"),
			@ApiResponse(code = 409, message = "Can not delete patient with encounters"),
			@ApiResponse(code = 404, message = "Patient not found") })
	
	public ResponseEntity<Patient> deletePatient(@RequestParam String ssn, @RequestParam String encounters) {
	    
		Patient patient = patientRepo.findByssn(ssn);
		
		if (Integer.parseInt(encounters) <= 0) {
		    
			if (patient != null) {
				logger.warn(patientRepo.findByssn(ssn) + " deleted");
				patientRepo.delete(patient);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
			
			else {
				logger.warn("User Not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}
		
		else {
			logger.warn("Can not delete user with encounters");
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}
	}

}

