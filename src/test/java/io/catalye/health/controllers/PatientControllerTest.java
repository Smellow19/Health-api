package io.catalye.health.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import io.catalye.health.controller.PatientController;
import io.catalye.health.domain.Address;
import io.catalye.health.domain.Patient;
import io.catalye.health.repositories.PatientRepo;
import io.catalye.health.validation.Validation;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PatientControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(PatientControllerTest.class);

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
	    
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

		patient = new Patient();
		address = new Address();
	      patient.setId("5900eb2d4a0d410d4724db68");
	        patient.setFirstname("Cassidy");
	        patient.setLastname("Bridges");
	        patient.setSsn("544-55-3434");
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
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientTest() throws Exception {
		Gson gson = new Gson();

		String json = gson.toJson(patient);
		MvcResult result = mockMvc
				.perform(post("/patients/create_patient").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a2CreatePatientUserExistsTest() throws Exception {
		Gson gson = new Gson();
		logger.warn(patient.toString());
		String json = gson.toJson(patient);
		MvcResult result = mockMvc
				.perform(post("/patients/create_patient").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isConflict()).andReturn();
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a3getAllPatientsTest() throws Exception {
		this.mockMvc.perform(get("/patients/all_patients")).andExpect(status().isOk());
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a4getAnPatientsTest() throws Exception {
		this.mockMvc.perform(get("/patients/find_patient?ssn=544-55-3434")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a5getAnPatientsTest() throws Exception {
		this.mockMvc.perform(get("/patients/find_patient?ssn=544-5")).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void b1updatePatientTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(
				put("/patients/update_patient?ssn=544-55-3434").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent()).andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void b2updatePatientNotFoundTest() throws Exception {
		Gson gson = new Gson();
		patient.setSsn("544-55-3493");
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(
				put("/patients/update_patient?ssn=534-3493").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void b3updatePatientNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setFirstname("");
		patient.setGender("GIRL");
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(
				put("/patients/update_patient?ssn=534-55-3434").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void c1DeletePatientNotFoundTest() throws Exception {
		Gson gson = new Gson();
		patient.setSsn("123");
		String json = gson.toJson(patient);
		MvcResult result = mockMvc.perform(delete("/patients/delete_patient?ssn=534&encounters=0")
				.contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void c2CantDeletePatientWithEncountersTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc
				.perform(delete("/patients/delete_patient?ssn=534-55-3434&encounters=5")
						.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isConflict()).andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void c3DeletePatientTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(patient);
		MvcResult result = mockMvc
				.perform(delete("/patients/delete_patient?ssn=544-55-3434&encounters=0")
						.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent()).andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientFirstnameNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setFirstname("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientLastnameNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setLastname("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientSSNNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setSsn("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientAgeNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setAge(0);
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientGenderNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setGender("Girl");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientHeightNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setHeight(0);
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientWeightNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setWeight(0);
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientInsuranceNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.setInsurance("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientStreetNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.getAddress().setStreet("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientCityNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.getAddress().setCity("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientStateNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.getAddress().setState("");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreatePatientPostalNotValidTest() throws Exception {
		Gson gson = new Gson();
		patient.getAddress().setPostal("0");
		boolean patientNotValid = patient.validateNotNullElements(patient);
		assertEquals(false, patientNotValid);
	}
	
	
	//User Testing
	
	
	   @Test 
	   @WithMockUser(roles = "USER")
	    public void a2CreatePatientUserExistsTestUser() throws Exception {
	        Gson gson = new Gson();
	        logger.warn(patient.toString());
	        String json = gson.toJson(patient);
	        try {
	        mockMvc
	                .perform(post("/patients/create_patient").contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test   
	    @WithMockUser(roles = "USER")
	    public void a3getAllPatientsTestUser() throws Exception {
	        this.mockMvc.perform(get("/patients/all_patients")).andExpect(status().isOk());
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void a4getAnPatientsTestUser() throws Exception {
	        this.mockMvc.perform(get("/patients/find_patient?ssn=544-55-3434")).andExpect(status().isOk());
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void a5getAnPatientsTestUser() throws Exception {
	        this.mockMvc.perform(get("/patients/find_patient?ssn=111-11-1111")).andExpect(status().isNotFound());
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void a6updatePatientTestUser() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(patient);
	        try {
	        mockMvc.perform(
	                put("/patients/update_patient?ssn=534-55-3434").contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void a7updatePatientNotFoundTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setSsn("111-11-1111");
	        String json = gson.toJson(patient);
	        try {
	        mockMvc.perform(
	                put("/patients/update_patient?ssn=534-343").contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void a8updatePatientNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setFirstname("");
	        patient.setGender("GIRL");
	        String json = gson.toJson(patient);
	        try {
	        mockMvc.perform(
	                put("/patients/update_patient?ssn=534-55-3434").contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	        
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void c4DeletePatientNotFoundTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setSsn("123");
	        String json = gson.toJson(patient);
	        try {
	            mockMvc.perform(delete("/patients/delete_patient?ssn=534&encounters=0")
	                .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound()).andReturn();
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void c5CantDeletePatientWithEncountersTestUser() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(patient);
	        try{ 
	            mockMvc
	                .perform(delete("/patients/delete_patient?ssn=534-55-3434&encounters=5")
	                        .contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	            
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void c6DeletePatientTestUser() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(patient);
	        try { 
	            mockMvc
	                .perform(delete("/patients/delete_patient?ssn=534-55-3434&encounters=0")
	                        .contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	        } catch (Exception e) {
	            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
	        }
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void b1CreatePatientFirstnameNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setFirstname("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void b2CreatePatientLastnameNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setLastname("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void b3CreatePatientSSNNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setSsn("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void b4CreatePatientAgeNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setAge(0);
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void b5CreatePatientGenderNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setGender("Girl");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void b6CreatePatientHeightNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setHeight(0);
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void b7CreatePatientWeightNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setWeight(0);
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void b8CreatePatientInsuranceNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.setInsurance("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void b9CreatePatientStreetNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.getAddress().setStreet("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void b10CreatePatientCityNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.getAddress().setCity("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void c1CreatePatientStateNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.getAddress().setState("");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void c2CreatePatientPostalNotValidTestUser() throws Exception {
	        Gson gson = new Gson();
	        patient.getAddress().setPostal("0");
	        boolean patientNotValid = patient.validateNotNullElements(patient);
	        assertEquals(false, patientNotValid);
	    }

}