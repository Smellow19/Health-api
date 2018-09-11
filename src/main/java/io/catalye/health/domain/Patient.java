package io.catalye.health.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.catalye.health.validation.Validation;

@Document(collection = "patients")
public class Patient {
	@Id
	String id;
	String firstname;
	String lastname;
	String ssn;
	Integer age;
	String gender;
	Integer height;
	Integer weight;
	String insurance;
	Address address;
	
	private static final Logger logger = LoggerFactory.getLogger(Patient.class);
	
	Validation validation = new Validation();


	public String getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getSsn() {
		return ssn;
	}

	public Integer getAge() {
		return age;
	}

	public String getStringAge() {
		return Integer.toBinaryString(age);
	}

	public String getGender() {
		return gender;
	}

	public Integer getHeight() {
		return height;
	}

	public String getStringHeight() {
		return Integer.toBinaryString(height);
	}

	public Integer getWeight() {
		return weight;
	}

	public String getStringWeight() {
		return Integer.toBinaryString(weight);
	}

	public String getInsurance() {
		return insurance;
	}

	public Address getAddress() {
		return address;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", ssn=" + ssn + ", age="
				+ age + ", gender=" + gender + ", height=" + height + ", weight=" + weight + ", insurance=" + insurance
				+ ", address=" + address + "]";
	}
	
	public boolean validateNotNullElements(Patient patient) {
		if ((patient.getFirstname().equals("") || patient.getFirstname() == null)) {
			logger.warn("Firstname invalid");
			return false;
		} else if ((patient.getLastname().equals("") || patient.getLastname() == null)) {
			logger.warn("lastname invalid");
			return false;
		} else if ((patient.getSsn().equals("") || patient.getSsn() == null) || validation.validateSSN(patient.getSsn()) != true) {
			logger.warn("SSN invalid");
			return false;
		}

		else if (patient.getStringAge().equals("") || patient.getStringAge() == null
				|| validation.IsNumber(patient.getStringAge()) != true) {
			logger.warn("Age Invalid");
			return false;
		}

		else if ((patient.getGender().equals("") || patient.getGender() == null)) {
			logger.warn("Gender invalid");
			return false;
		}

		else if (patient.getStringHeight().equals("") || patient.getLastname() == null
				|| validation.IsNumber(patient.getStringHeight()) != true) {
			logger.warn("Height Invalid");
			return false;
		}

		else if (patient.getStringWeight().equals("") || patient.getStringWeight() == null
				|| validation.IsNumber(patient.getStringWeight()) != true) {
			logger.warn("Weight invalid");
			return false;
		}

		else if ((patient.getInsurance().equals("") || patient.getInsurance() == null)) {
			logger.warn("Insurance invalid");
			return false;
		}

		else if ((patient.getAddress() == null)) {
			logger.warn("Address invalid");
			return false;
		} else if ((patient.getAddress().getStreet().equals("") || patient.getAddress().getStreet() == null)) {
			logger.warn("Street invalid");
			return false;
		} else if ((patient.getAddress().getCity().equals("") || patient.getAddress().getCity() == null)) {
			logger.warn("City invalid");
			return false;
		} else if (patient.getAddress().getState().equals("") || patient.getAddress().getState() == null
				|| validation.validateState(patient.getAddress().getState()) != true) {
			logger.warn("State invalid");
			return false;
		} else if ((patient.getAddress().getPostal().equals("") || patient.getAddress().getPostal() == null)
				|| validation.validateZipCode(patient.getAddress().getPostal()) != true) {
			logger.warn("Zip invalid");
			return false;
		} else {
			return true;
		}
	}


}

