package io.catalye.CHAPI.controller;

import java.util.ArrayList;
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

import io.catalye.CHAPI.domain.Encounter;
import io.catalye.CHAPI.domain.Patient;
import io.catalye.CHAPI.domain.Encounter;
import io.catalye.CHAPI.repositories.EncounterRepo;
import io.catalye.CHAPI.validation.Validation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is the controller for the Encounters class.
 * This class handles CRUD functionality for the Encounter domain as well as 
 * the Encounters Repoistory
 * @author tBridges
 *
 */
@RestController
@RequestMapping("/encounter")
public class EncounterController {

	Validation validation = new Validation();

	private static final Logger logger = LoggerFactory.getLogger(EncounterController.class);

	@Autowired
	EncounterRepo encounterRepo;
	
	/**
	 * This function passes in a patient Id from the url and finds all encounters matching that patient
	 * Id
	 * @param patientid
	 * @return
	 */
	@RequestMapping(value = "/find_encounter", method = RequestMethod.GET)
	@ApiOperation("Finds an encounter based off of the patientId.")
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Patient has Encounters")})
	public ResponseEntity<List<Encounter>> getPatientEncounters(@RequestParam String patientid) {
		logger.warn(patientid);
		logger.warn("Repo is " + encounterRepo.findAll().size() + " Patients Long");

		List<Encounter> encounters = encounterRepo.findAll();
		ArrayList<Encounter> patientEncounters = new ArrayList<Encounter>();
		for (int i = 0; i < encounters.size(); i++) {
			if (encounters.get(i).getPatientid().equalsIgnoreCase(patientid)) {
				patientEncounters.add(encounters.get(i));
			}

		}
		logger.warn("Patient has " + patientEncounters.size() + " visit records");
		return new ResponseEntity<List<Encounter>>(patientEncounters, HttpStatus.OK);

	}


	/**
	 * This will find all Encounters inside of the Encounter Repository
	 * @return
	 */
	@RequestMapping(value = "/all_encounters", method = RequestMethod.GET)
	@ApiOperation("Finds all encounters in the database")
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "Encounters Found"),
					@ApiResponse(code = 404, message = "Encounters not found")
	})
	public ResponseEntity<List<Encounter>> getEncounters() {
		List<Encounter> encounters = encounterRepo.findAll();
		if (encounters != null) {
			return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);

		}
		else {
			logger.debug("Repo empty");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This will take an Encounter object, and check to see if there is already an encounter 
	 * object with  the same ID. If not it will create a new Encounter object inside of the Repository.
	 * if so then it will return a http conflict code preventing a new object from being created
	 * @param encounter
	 * @return
	 */
	@RequestMapping(value = "/create_encounter", method = RequestMethod.POST)
	@ApiOperation("Creates a new encounter in the database.")
	@ApiResponses(
			value = {
					@ApiResponse(code = 201, message = "Encounter Created"),
					@ApiResponse(code = 409, message = "Encounter with this ID already exists")		
	})
	public ResponseEntity<Encounter> createEncounter(@RequestBody Encounter encounter) {
		if (encounterRepo.findBy_Id(encounter.get_Id()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			encounterRepo.insert(encounter);
			return new ResponseEntity<Encounter>(encounter, HttpStatus.CREATED);
		}
	}

	/**This function handles the updating of encounter information
	 * 
	 * @param id
	 * @param encounter
	 * @return
	 * This function takes in an Id as well as an encounter object. It will check the database by the ID to 
	 * find the encounter specified and update if the encounter is found.
	 * if the encounter is not found then a 404 error is returned.
	 */
	@RequestMapping(value = "/update_encounter", method = RequestMethod.PUT)
	@ApiOperation("updates an encounter in the database.")
	@ApiResponses(
			value = {
					@ApiResponse(code = 202, message = "Encounter Updated"),
					@ApiResponse(code = 404, message = "Encounter not found")
	})
	public ResponseEntity<Encounter> updateEncounter(@RequestParam String id, @RequestBody Encounter encounter) {
		logger.warn(id);
		logger.warn(encounter.toString());
		if (encounterRepo.findBy_Id(encounter.get_Id()) != null) {
			encounterRepo.save(encounter);
			return new ResponseEntity<Encounter>(encounter, HttpStatus.ACCEPTED);
		} else {
			logger.warn("Encounter Not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**This handles the deleting of an encounter from the Encounter Repository
	 * @param id
	 * @return
	 * A 202 if the encounter is found and deleted.
	 * a 404 if the encounter is not found.
	 */
	@RequestMapping(value = "/delete_encounter", method = RequestMethod.DELETE)
	@ApiOperation("updates an encounter in the database.")
	@ApiResponses(
			value = {
					@ApiResponse(code = 202, message = "Encounter deleted"),
					@ApiResponse(code = 404, message = "Encounter not found")
	})
	public ResponseEntity<Patient> deletePatient(@RequestParam String id) {
		Encounter encounter = encounterRepo.findBy_Id(id);
		if (encounter != null) {
			logger.warn(encounter + " deleted");
			encounterRepo.delete(encounter);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} else {
			logger.warn("Encounter Not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
