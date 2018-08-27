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

@RestController
@RequestMapping("/encounter")
public class EncounterController {

		Validation validation = new Validation();

		private static final Logger logger = LoggerFactory.getLogger(EncounterController.class);

		@Autowired
		EncounterRepo encounterRepo;
		
		@RequestMapping(value = "/find_encounter", method = RequestMethod.GET)
		public ResponseEntity<List<Encounter>> getPatientEncounters(@RequestParam String patientid) {
			logger.warn(patientid);
			logger.warn("Repo is " + encounterRepo.findAll().size() + " Patients Long");

			List<Encounter> encounters = encounterRepo.findAll();
			ArrayList<Encounter> patientEncounters = new ArrayList<Encounter>();
			for(int i = 0; i < encounters.size(); i++) {
				if(encounters.get(i).getPatientid().equalsIgnoreCase(patientid)) {
					patientEncounters.add(encounters.get(i));
				}
				
			}
			logger.warn("Patient has " + patientEncounters.size() + " visit records");
			return new ResponseEntity<List<Encounter>>(patientEncounters, HttpStatus.OK);
			
		}
		
		@RequestMapping(value = "/all_encounters", method = RequestMethod.GET)
		public ResponseEntity<List<Encounter>> getEncounters() {
			List<Encounter> encounters = encounterRepo.findAll();
			if (encounters != null) {
				return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);

			}
			return null;
		}
		
		@RequestMapping(value = "/create_encounter", method = RequestMethod.POST)
		public ResponseEntity<Encounter> createEncounter(@RequestBody Encounter encounter) {
				if (encounterRepo.findBy_Id(encounter.get_Id()) != null) {
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				} else {
					encounterRepo.insert(encounter);
					return new ResponseEntity<Encounter>(encounter, HttpStatus.ACCEPTED);
				}
			} 

		@RequestMapping(value = "/update_encounter", method = RequestMethod.PUT)
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

		@RequestMapping(value = "/delete_encounter", method = RequestMethod.DELETE)
		public ResponseEntity<Patient> deletePatient(@RequestParam String id) {
			Encounter encounter = encounterRepo.findBy_Id(id);
				if (encounter != null) {
					logger.warn(encounter+ " deleted");
					encounterRepo.delete(encounter);
					return new ResponseEntity<>(HttpStatus.ACCEPTED);
				} else {
					logger.warn("Encounter Not found");
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}


	
}
