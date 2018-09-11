package io.catalye.health.controllers;

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

import io.catalye.health.controller.EncounterController;
import io.catalye.health.domain.Encounter;
import io.catalye.health.repositories.EncounterRepo;

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

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

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
    @WithMockUser(roles = "ADMIN")
    public void a1getAllEncountersTestAdmin() throws Exception {
        this.mockMvc.perform(get("/encounter/all_encounters"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void a2getAnEncountersTestAdmin() throws Exception {
        this.mockMvc.perform(get(
                "/encounter/find_encounter?patientid=5900eb2d4a0d410d4724db37"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void a3CreateEncounterTestAdmin() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        MvcResult result = mockMvc
                .perform(post("/encounter/create_encounter")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())

                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void a4CreateEncounterTestAdmin() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        MvcResult result = mockMvc
                .perform(post("/encounter/create_encounter")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict())

                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void a5updateEncounterTestAdmin() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        MvcResult result = mockMvc.perform(
                put("/encounter/update_encounter?id=5900eb2d4a0d410d4724db68")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent())

                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void a6updateEncounterTestAdmin() throws Exception {
        Gson gson = new Gson();
        encounter.set_Id("21312rewfwfewefwe23r23423dff");
        String json = gson.toJson(encounter);
        MvcResult result = mockMvc
                .perform(put("/encounter/update_encounter?id=5gdfg34trgfdg4")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound())

                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void b1DeleteEncounterTestAdmin() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        MvcResult result = mockMvc.perform(delete(
                "/encounter/delete_encounter?id=5900eb2d4a0d410d4724db68")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void b2DeleteEncounterTestAdmin() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        MvcResult result = mockMvc.perform(delete(
                "/encounter/delete_encounter?id=5900eb2d4a0d410d4724db68")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound()).andReturn();
    }

//Users test

    @Test
    @WithMockUser(roles = "USER")
    public void a1getAllEncountersTestUser() throws Exception {
        this.mockMvc.perform(get("/encounter/all_encounters"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void a2getAnEncountersTestUser() throws Exception {
        this.mockMvc.perform(get(
                "/encounter/find_encounter?patientid=5900eb2d4a0d410d4724db37"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void a3CreateEncounterTestUser() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        try {
            mockMvc.perform(post("/encounter/create_encounter")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isUnauthorized())

                    .andReturn();
        } catch (Exception e) {
            Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
        }

    }

    @Test
    @WithMockUser(roles = "USER")
    public void a4CreateEncounterTestUser() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        try {
        mockMvc
        .perform(post("/encounter/create_encounter")
                .contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isUnauthorized()).andReturn();
    } catch (Exception e) {
        Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
    }
    }


    @Test
    @WithMockUser(roles = "USER")
    public void a5updateEncounterTestUser() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        try {
            mockMvc.perform(
                    put("/encounter/update_encounter?id=5900eb2d4a0d410d4724db68")
                            .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isUnauthorized())

                    .andReturn();
    } catch (Exception e) {
        Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
    }

    }

    @Test
    @WithMockUser(roles = "USER")
    public void a6updateEncounterTestUser() throws Exception {
        Gson gson = new Gson();
        encounter.set_Id("21312rewfwfewefwe23r23423dff");
        String json = gson.toJson(encounter);
        try {
            mockMvc
            .perform(put("/encounter/update_encounter?id=5gdfg34trgfdg4")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isUnauthorized())

            .andReturn();
    } catch (Exception e) {
        Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
    }
        
    }

    @Test
    @WithMockUser(roles = "USER")
    public void b1DeleteEncounterTestUser() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        try {
            mockMvc.perform(delete(
                    "/encounter/delete_encounter?id=5900eb2d4a0d410d4724db68")
                            .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isUnauthorized()).andReturn();
            
    } catch (Exception e) {
        Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
    }

    }

    @Test
    @WithMockUser(roles = "USER")
    public void b2DeleteEncounterTestUser() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(encounter);
        
        try {
            mockMvc.perform(delete(
                    "/encounter/delete_encounter?id=5900eb2d4a0d410d4724db68")
                            .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isUnauthorized()).andReturn();
            
    } catch (Exception e) {
        Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
    }
        

    }

}