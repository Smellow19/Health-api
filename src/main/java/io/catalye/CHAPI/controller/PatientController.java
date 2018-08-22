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
import io.catalye.CHAPI.repositories.PatientRepo;
import io.catalye.CHAPI.validation.Validation;

@RestController
@RequestMapping("/patients")
public class PatientController {

	Validation validation = new Validation();

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientRepo patientRepo;

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

	@RequestMapping(value = "/create_patient", method = RequestMethod.POST)
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		boolean validPatient = validation.validateNotNullElements(patient);
		if (validPatient) {
			if (patientRepo.findByssn(patient.getSsn()) != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				patientRepo.save(patient);
				return new ResponseEntity<Patient>(patient, HttpStatus.ACCEPTED);
			}
		} else {
			logger.warn("User: not Valid ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
//	@RequestMapping(value = "/update_patient", method = RequestMethod.PUT)
//	public ResponseEntity<Patient> createPatient(@RequestParam  String id) {
//		
//	}
	
//	@RequestMapping(value = "/delete_patient", method = RequestMethod.DELETE)
//	public ResponseEntity<Patient> createPatient(@RequestParam  String id) {
//		
//	}

}
