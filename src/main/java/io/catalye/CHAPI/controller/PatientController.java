package io.catalye.CHAPI.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.catalye.CHAPI.domain.Patient;
import io.catalye.CHAPI.domain.User;
import io.catalye.CHAPI.repositories.PatientRepo;

@RestController
@RequestMapping("/patients")
public class PatientController {
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










}
