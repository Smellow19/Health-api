package io.catalye.CHAPI.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import io.catalye.CHAPI.controller.PatientController;
import io.catalye.CHAPI.domain.Address;
import io.catalye.CHAPI.domain.Patient;
import io.catalye.CHAPI.repositories.PatientRepo;
import io.catalye.CHAPI.validation.Validation;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PatientControllerTest {

	@InjectMocks
	private PatientController patientController;

	@Mock
	private PatientRepo patientRepo;
	
	Validation validation = new Validation();


	private Patient patient;
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

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {


		patient = new Patient();
		address = new Address();
		patient.setId("5900eb2d4a0d410d4724db68");
		patient.setFirstname("Tyler");
		patient.setLastname("Bridges");
		patient.setSsn("534-55-3434");
		patient.setAge(21);
		patient.setGender("Male");
		patient.setHeight(175);
		patient.setWeight(175);
		patient.setInsurance("React Health");
		address.setStreet("4808 Verena Lane");
		address.setCity("Sacramento");
		address.setState("CA");
		address.setPostal("60402");
		patient.setAddress(address);
	}
	
	@Test
	public void a1CreatePatientTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(post("/patients/create_patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isCreated())
	
				.andReturn();
	}
	
	@Test
	public void a2CreatePatientUserExistsTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(post("/patients/create_patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isConflict())
				.andReturn();
	}
	
	@Test
	public void a3getAllPatientsTest() throws Exception {
			this.mockMvc.perform(get("/patients/all_patients"))
			.andExpect(status().isOk());
	}

	@Test
	public void a4getAnPatientsTest() throws Exception {
			this.mockMvc.perform(get("/patients/find_patient?ssn=534-55-3434"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void a5getAnPatientsTest() throws Exception {
			this.mockMvc.perform(get("/patients/find_patient?ssn=111-11-1111"))
			.andExpect(status().isNotFound());
	}
	

	
	@Test
	public void a6updatePatientTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(put("/patients/update_patient?ssn=534-55-3434")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isAccepted())
				.andReturn();
	}
	
	@Test
	public void a7updatePatientNotFoundTest() throws Exception {
		Gson gson = new Gson();
		patient.setSsn("111-11-1111");
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(put("/patients/update_patient?ssn=534-343")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isNotFound())
				.andReturn();
	}
	
	@Test
	public void a8updatePatientNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setFirstname("");
		patient.setGender("GIRL");
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(put("/patients/update_patient?ssn=534-55-3434")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isBadRequest())
				.andReturn();
	}
	
	@Test
	public void b7DeletePatientTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(delete("/patients/delete_patient?ssn=534-55-3434&encounters=0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isAccepted())
				.andReturn();
	}
	
	@Test
	public void a3CreatePatientFirstnameNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setFirstname("");
		boolean patientNotValid = validation.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);		
	}
	
	@Test
	public void a3CreatePatientLastnameNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setLastname("");
		boolean patientNotValid = validation.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);		
	}
	
	@Test
	public void a3CreatePatientSSNNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setSsn("");
		boolean patientNotValid = validation.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);		
	}
	
	@Test
	public void a3CreatePatientAgeNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setAge(0);
		boolean patientNotValid = validation.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);		
	}
	
	@Test
	public void a3CreatePatientGenderNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setGender("Girl");
		boolean patientNotValid = validation.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);		
	}
	
}