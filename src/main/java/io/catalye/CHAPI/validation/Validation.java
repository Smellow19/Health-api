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
	
	public boolean validateZipCode(String zipcode) {
		if (zipcode.matches("^[0-9]{5}$")) {
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
	
	public boolean validateIsString(String string) {
		if (string.matches("^[1-9]\\d*$")) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public boolean validateIsNotGender(String string) {
		if (string.equals("Male") || string.equals("Female")) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public boolean IsNumber(String num) {
		if (num.matches("^[1-9]\\d*{3}$")) {
			return true;
		} else {
			logger.warn("Is Number Failed");
			return false;
		}
	}
	
	public boolean validateSSN(String SSN) {
		if (SSN.matches("^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean validateNotNullElements(Patient patient) {
		  if( (patient.getFirstname().equals("") || patient.getFirstname() == null )) {
			  logger.warn("Firstname invalid");
			  return false;
		  }
		  else if( (patient.getLastname().equals("") || patient.getLastname() == null)){
				  logger.warn("lastname invalid");
				  return false;
		   } 
		  else if((patient.getSsn().equals("") || patient.getSsn() == null) || validateSSN(patient.getSsn()) != true) {
				  logger.warn("SSN invalid");
				  return false;
		   }
		  
		  else if(patient.getStringAge().equals("") || patient.getStringAge() == null || IsNumber(patient.getStringAge())  != true ) {
			  logger.warn("Age Invalid");
			  return false;
	   } 
		  
		  else if( (patient.getGender().equals("") || patient.getGender() == null || (validateIsNotGender(patient.getGender())) )) {
			  logger.warn("Gender invalid");
			  return false;
		  }
		  
		  else if(patient.getStringHeight().equals("") || patient.getLastname() == null || IsNumber(patient.getStringHeight())  != true  ) {
			  logger.warn("Height Invalid");
			  return false;
		  }
		  
		  else if(patient.getStringWeight().equals("") || patient.getStringWeight() == null || IsNumber(patient.getStringWeight()) != true ) {
			  logger.warn("Weight invalid");
			  return false;
		  }



		  else if( (patient.getInsurance().equals("") || patient.getInsurance() == null)) {
			  logger.warn("Insurance invalid");
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
		  else if((patient.getAddress().getCity().equals("") || patient.getAddress().getCity() == null) ) {
				  logger.warn("City invalid");
				  return false;
		   }
		  else if(patient.getAddress().getState().equals("") || patient.getAddress().getState() == null || validateState(patient.getAddress().getState()) != true) {
				  logger.warn("State invalid");
				  return false;
		   }
		  else if((patient.getAddress().getPostal().equals("") || patient.getAddress().getPostal() == null) || validateZipCode(patient.getAddress().getPostal()) != true){
				  logger.warn("Zip invalid");
				  return false;
		   } else {
			   return true;
		   }
}

}
