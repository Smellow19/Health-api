package io.catalye.health.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import io.catalye.health.controller.UserController;
import io.catalye.health.domain.User;
import io.catalye.health.repositories.UserRepo;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserRepo userRepo;

	private User user;
	private User payload;

	String id;
	String name;
	String title;
	ArrayList<String> roles;
	String email;
	String password;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
	    
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

		user = new User();
		user.setId("5900eb2d4a0d410adawdasdadw2c");
		user.setName("Peter Williams");
		user.setTitle("Biller");
		user.setEmail("tbridges@superhealth.com");
		user.setPassword("password");
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a1CreateUserTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(user);
		MvcResult result = mockMvc
				.perform(post("/user/create_user").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())

				.andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a2CreateUserAlreadyExisitsTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(user);
		MvcResult result = mockMvc
				.perform(post("/user/create_user").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isConflict())

				.andReturn();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void a2getAllUsersTest() throws Exception {
		this.mockMvc.perform(get("/user/all_users")).andExpect(status().isOk());
	}



	@Test
	@WithMockUser(roles = "ADMIN")
	public void a4updateUserTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(user);
		MvcResult result = mockMvc
				.perform(put("/user/update_user?email=pwilliams@superhealth.com")
						.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent())

				.andReturn();
	}
	
	   @Test
	    @WithMockUser(roles = "ADMIN")
	    public void a4updateUserTestNotFound() throws Exception {
	        Gson gson = new Gson();
	        user.setEmail("fsdfesfsefsdfes");
	        String json = gson.toJson(user);
	        
	        MvcResult result = mockMvc
	                .perform(put("/user/update_user?email=pwilli@superhealth.com")
	                        .contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isNotFound())

	                .andReturn();
	    }

	@Test 
	@WithMockUser(roles = "ADMIN")
	public void b1DeleteUserTest() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(user);
		MvcResult result = mockMvc
				.perform(delete("/user/delete_user?email=tbridges@superhealth.com")
						.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isAccepted()).andReturn();
	}
	
	   @Test 
	    @WithMockUser(roles = "ADMIN")
	    public void b1DeleteUserTestNotFound() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(user);
	        MvcResult result = mockMvc
	                .perform(delete("/user/delete_user?email=tbries@superhealth.com")
	                        .contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isNotFound()).andReturn();
	    }
	
	
	
	//User Tests
	
	
	
	   @Test
	    @WithMockUser(roles = "USER")
	    public void a2CreateUserAlreadyExisitsTestUser() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(user);
	        try {
	            mockMvc
	                .perform(post("/user/create_user").contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden())

	                .andReturn();
	        } catch (Exception e) {
                Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void a2getAllUsersTestUser() throws Exception {
	        this.mockMvc.perform(get("/user/all_users")).andExpect(status().isOk());
	    }

	    @Test
	    @WithMockUser(roles = "USER")
	    public void a4updateUserTestUser() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(user);
	        try{
	            mockMvc
	        
	                .perform(put("/user/update_user?email=pwilliams@superhealth.com")
	                        .contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden())

	                .andReturn();
	        } catch (Exception e) {
                Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
	    }

	    @Test 
	    @WithMockUser(roles = "USER")
	    public void b1DeleteUserTestUser() throws Exception {
	        Gson gson = new Gson();
	        String json = gson.toJson(user);
	        try{ 
	            mockMvc
	        
	                .perform(delete("/user/delete_user?email=tbridges@superhealth.com")
	                        .contentType(MediaType.APPLICATION_JSON).content(json))
	                .andExpect(status().isForbidden()).andReturn();
	        } catch (Exception e) {
                Assert.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
	    }

}