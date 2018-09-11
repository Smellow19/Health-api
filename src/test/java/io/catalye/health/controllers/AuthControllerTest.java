package io.catalye.health.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import io.catalye.health.controller.PatientController;
import io.catalye.health.domain.Address;
import io.catalye.health.domain.Patient;
import io.catalye.health.payload.JwtAuthenticationResponse;
import io.catalye.health.payload.LoginRequest;
import io.catalye.health.repositories.PatientRepo;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthControllerTest {

    private LoginRequest user;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientRepo patientRepo;

    @Autowired
    private WebApplicationContext wac;

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

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
        MockitoAnnotations.initMocks(this);
        user = new LoginRequest();

        user.setEmail("werickson@superhealth.com");
        user.setPassword("cGFzc3dvcmQ=");

        patient = new Patient();
        address = new Address();
        patient.setId("5900eb2d4a0d410d4724db68");
        patient.setFirstname("Tyler");
        patient.setLastname("Bridges");
        patient.setSsn("534-66-3434");
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
    public void testA1loginSuccess() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testA2LoginFail() throws Exception {
        user.setEmail("Balga@catalyte.io");
        user.setPassword("cGFzcw==");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void anona3AccessTest() throws Exception {
        mockMvc.perform(post("/auth/user"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/auth/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void usera4AccessTest() throws Exception {

        user.setEmail("vburns@superhealth.com");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        String patientJson = gson.toJson(patient);
        String token = gson.fromJson(mockMvc
                .perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(), JwtAuthenticationResponse.class)
                .getAccessToken();

        mockMvc.perform(get("/patients/all_patients").header("Authorization",
                "Bearer " + token)).andExpect(status().isOk());

        mockMvc.perform(post("/patients/create_patient")
                .contentType(MediaType.APPLICATION_JSON).content(patientJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void Admina5AccessTestGet() throws Exception {

        user.setEmail("werickson@superhealth.com");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        String patientJson = gson.toJson(patient);
        String token = gson.fromJson(mockMvc
                .perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(), JwtAuthenticationResponse.class)
                .getAccessToken();

        mockMvc.perform(get("/patients/all_patients").header("Authorization",
                "Bearer " + token)).andExpect(status().isOk());
       
    }
    
    @Test
    public void Admina6AccessTestPost() throws Exception {

        user.setEmail("werickson@superhealth.com");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        String patientJson = gson.toJson(patient);
        String token = gson.fromJson(mockMvc
                .perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(), JwtAuthenticationResponse.class)
                .getAccessToken();
        
        mockMvc.perform(post("/patients/create_patient")
                .contentType(MediaType.APPLICATION_JSON).content(patientJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void Adminc8AccessTestDelete() throws Exception {

        user.setEmail("werickson@superhealth.com");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        String patientJson = gson.toJson(patient);
        String token = gson.fromJson(mockMvc
                .perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(), JwtAuthenticationResponse.class)
                .getAccessToken();
        
        mockMvc.perform(delete("/patients/delete_patient/?ssn=534-66-3434&encounters=0")
                .contentType(MediaType.APPLICATION_JSON).content(patientJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

}
