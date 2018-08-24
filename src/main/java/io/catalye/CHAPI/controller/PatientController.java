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
import io.catalye.CHAPI.repositories.EncounterRepo;
import io.catalye.CHAPI.repositories.PatientRepo;
import io.catalye.CHAPI.validation.Validation;

@RestController
@RequestMapping("/patients")
public class PatientController {

	Validation validation = new Validation();

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientRepo patientRepo;

	@Autowired
	EncounterRepo encounterRepo;

	@RequestMapping(value = "/all_patients", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> getPatients() {
		logger.warn("Repo is " + patientRepo.findAll().size() + " Patients Long");
		List<Patient> patients = patientRepo.findAll();
		if (patients != null) {
			return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);

		} else {
			logger.debug("User: not found ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/find_patient", method = RequestMethod.GET)
	public ResponseEntity<Patient> getPatient(@RequestParam String ssn) {
		Patient patient = patientRepo.findByssn(ssn);
		if (patient != null) {
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);

		} else {
			logger.debug("User: not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/create_patient", method = RequestMethod.POST)
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		boolean validPatient = validation.validateNotNullElements(patient);
		if (validPatient) {
			if (patientRepo.findByssn(patient.getSsn()) != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				patientRepo.insert(patient);
				return new ResponseEntity<Patient>(patient, HttpStatus.ACCEPTED);
			}
		} else {
			logger.warn("User: not Valid ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update_patient", method = RequestMethod.PUT)
	public ResponseEntity<Patient> createPatient(@RequestParam String ssn, @RequestBody Patient patient) {
		boolean validPatient = validation.validateNotNullElements(patient);
		if (validPatient) {
			if (patientRepo.findByssn(patient.getSsn()) == null) {
				logger.warn("User: not Valid ");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				patientRepo.save(patient);
				return new ResponseEntity<Patient>(patient, HttpStatus.ACCEPTED);
			}
		} else {
			logger.warn("User Not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}

	@RequestMapping(value = "/delete_patient", method = RequestMethod.DELETE)
	public ResponseEntity<Patient> deletePatient(@RequestParam String ssn, @RequestParam String encounters) {
		Patient patient = patientRepo.findByssn(ssn);
		if (Integer.parseInt(encounters) <= 0) {
			if (patient != null) {
				logger.warn(patientRepo.findByssn(ssn) + " deleted");
				patientRepo.delete(patient);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} else {
				logger.warn("User Not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}else {
			logger.warn("Can not delete user with encounters");
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}
	}

}
