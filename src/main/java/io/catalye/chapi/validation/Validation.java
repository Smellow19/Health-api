package io.catalye.chapi.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.catalye.chapi.domain.Patient;

public class Validation {

	private static final Logger logger = LoggerFactory.getLogger(Validation.class);

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
	

}
