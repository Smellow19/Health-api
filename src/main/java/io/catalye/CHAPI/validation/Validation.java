package io.catalye.CHAPI.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.catalye.CHAPI.domain.Patient;

public class Validation {

	
	private static final Logger logger = LoggerFactory.getLogger(Validation.class);

	public boolean validateEmail(String validEmail) {
		if (validEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			return true;
		}

		else {
			return false;
		}
	}
	
	public boolean validateZipCode(int zipcode) {
		if (zipcode.matches("^[0-9]{5}")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validateState(String state) {
		if (state.matches("^[A-Za-z]{2}")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validateSSN(String SSN) {
		if (SSN.matches("^(?!000|666)[0-8][0-9]{3}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean validateNotNullElements(Patient patient) {
		  if( (patient.getFirstname().equals("") || patient.getFirstname() == null) ) {
			  logger.warn("Firstname invalid");
			  return false;
		  }
		  else if( (patient.getLastname().equals("") || patient.getLastname() == null) ) {
				  logger.warn("lastname invalid");
				  return false;
		   } 
		  else if((patient.getSsn().equals("") || patient.getSsn() == null) || validateSSN( patient.getSsn())) {
				  logger.warn("SSN invalid");
				  return false;
		   }
		  else if((patient.getAddress() == null)) {
				  logger.warn("Address invalid");
				  return false;
		   }
		  else if((patient.getAddress().getStreet().equals("") || patient.getAddress().getStreet() == null)) {
				  logger.warn("Street invalid");
				  return false;
		   }
		  else if((patient.getAddress().getCity().equals("") || patient.getAddress().getCity() == null)) {
				  logger.warn("City invalid");
				  return false;
		   }
		  else if(patient.getAddress().getState().equals("") || patient.getAddress().getState() == null) {
				  logger.warn("State invalid");
				  return false;
		   }
		  else if((patient.getAddress().getPostal().equals("") || patient.getAddress().getPostal() == null) || validateZipCode(patient.getAddress().getPostal())){
				  logger.warn("Zip invalid");
				  return false;
		   } else {
			   return true;
		   }
}

}
