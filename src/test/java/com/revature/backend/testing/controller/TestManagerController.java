package com.revature.backend.testing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.backend.controller.ManagerController;
import com.revature.backend.model.Manager;
import com.revature.backend.service.ManagerService;
import com.revature.backend.util.ClientMessage;

//@WebMvcTest(ManagerController.class)
@SpringBootTest
public class TestManagerController {
	
	ObjectMapper objectMapper = new ObjectMapper();

	@Mock
	ManagerService service;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private ManagerController manageController;
	
	List<Manager> managers = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		Manager manager = new Manager(1, "mock@gmail.com", "mock", "manager");
		mockMvc = MockMvcBuilders.standaloneSetup(manageController).build();
		managers.add(manager);
		
		when(service.getAllManagers()).thenReturn(managers);
	}
	
	/*
	 * Sanity Check - if this fails, application context is not loaded and all other
	 * tests should fail
	 */
	@Test
	public void contextLoads() throws Exception {
		assertThat(manageController).isNotNull();
	}
	
	@Test
	public void verifyListening() throws Exception {
		this.mockMvc.perform(post("/getmanager").contentType("application/json")
			.content(objectMapper.writeValueAsString(new ClientMessage("mock@gmail.com"))))
			.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void verifyResult() throws Exception {		
		ClientMessage expectedResponse = new ClientMessage("1");
		
		MvcResult mvcResult = this.mockMvc.perform(post("/getmanager").contentType("application/json")
							  .content(objectMapper.writeValueAsString(new ClientMessage("mock@gmail.com"))))
							  .andReturn();
		
		String actualResponse = mvcResult.getResponse().getContentAsString();
		
		assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
	}
	
	@Test
	public void verifyFailure() throws Exception {
//		Manager manager2 = new Manager(2, "mock@gmail.com", "mock", "manager");
//		managers.add(manager2);
//		
//		when(service.getAllManagers()).thenReturn(managers);
		
		ClientMessage expectedResponse = new ClientMessage("2");
		
		MvcResult mvcResult = this.mockMvc.perform(post("/getmanager").contentType("application/json")
							  .content(objectMapper.writeValueAsString(new ClientMessage("mock@gmail.com"))))
							  .andReturn();
		
		String actualResponse = mvcResult.getResponse().getContentAsString();
		
		assertThat(actualResponse).isNotEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
	}
	
}
