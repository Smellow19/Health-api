package io.catalye.CHAPI.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import io.catalye.CHAPI.controller.EncounterController;
import io.catalye.CHAPI.domain.Encounter;
import io.catalye.CHAPI.repositories.EncounterRepo;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class EncounterControllerTest {

	@InjectMocks
	private EncounterController encounterController;

	@Mock
	private EncounterRepo encounterRepo;

	private Encounter encounter;
	String _Id;
	String patientid;
	String notes;
	String vistcode;
	String provider;
	String billingcode;
	Integer totalcost;
	Integer copay;
	String chiefcomplaint;
	Integer pulse;
	Integer systolic;
	Integer diastolic;
	String date;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {


		encounter = new Encounter();
		encounter.set_Id("5900eb2d4a0d410d4724db68");
		encounter.setPatientid("5900eb2d4a0d410d4724db37");
		encounter.setNotes("test Notes");
		encounter.setVistcode("test visitcode");
		encounter.setProvider("Doctor Dennis");
		encounter.setBillingcode("562.762.298-90");
		encounter.setTotalcost(0);
		encounter.setCopay(0);
		encounter.setChiefcomplaint("test Complaint");
		encounter.setPulse(0);
		encounter.setSystolic(0);
		encounter.setDiastolic(0);
		encounter.setDate("1489398612876");
	}
	
	@Test
	public void a1getAllEncountersTest() throws Exception {
			this.mockMvc.perform(get("/encounter/all_encounters"))
			.andExpect(status().isOk());
	}

	@Test
	public void a2getAnEncountersTest() throws Exception {
			this.mockMvc.perform(get("/encounter/find_encounter?patientid=5900eb2d4a0d410d4724db37"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void a3CreateEncounterTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(encounter);
		MvcResult result = mockMvc.perform(post("/encounter/create_encounter")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isCreated())
	
				.andReturn();
	}
	
	@Test
	public void a4updateEncounterTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(encounter);
		MvcResult result = mockMvc.perform(put("/encounter/update_encounter?id=5900eb2d4a0d410d4724db68")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isAccepted())
	
				.andReturn();
	}
	
	@Test
	public void b1DeleteEncounterTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(encounter);
		MvcResult result = mockMvc.perform(delete("/encounter/delete_encounter?id=5900eb2d4a0d410d4724db68")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status()
				.isAccepted())
				.andReturn();
	}
	
}