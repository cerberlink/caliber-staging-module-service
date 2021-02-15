package com.revature.backend.testing.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.backend.controller.InterviewFeedbackController;
import com.revature.backend.model.Associate;
import com.revature.backend.model.AssociateStatus;
import com.revature.backend.model.Batch;
import com.revature.backend.model.InterviewFeedback;
import com.revature.backend.model.Manager;
import com.revature.backend.service.AssociateServiceImpl;
import com.revature.backend.service.InterviewFeedbackService;
import com.revature.backend.service.ManagerServiceImpl;

@SpringBootTest
public class InterviewFeedbackControllerTest {
	
	@Mock
	private InterviewFeedbackService ifServ;
	
	@Mock
	private AssociateServiceImpl aServ;
	
	@Mock
	private ManagerServiceImpl mServ;
	
	@InjectMocks
	private InterviewFeedbackController ifCon;
	
	private MockMvc mockMvc;
	
	/**
	 *  This declaration are used to instantiate global variables 
	 *  that will be used throughout the test 
	 */	
	Manager manager = new Manager(1, "manager@manager.com", "Demo", "Manager");
	Batch batch = new Batch(1, "salesID", "name", "skill", "location");
	Associate associate1 = new  Associate(1, "salesID", "email@email.com", "John", "Doe", manager, batch, AssociateStatus.STAGING);
	Timestamp date = new Timestamp(System.currentTimeMillis());
	
	/**
	 * Setting up for the when statements to reduce redundancy in the program
	 * with this all the program will have all necessary when statement to run
	 * the test
	 */
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(ifCon).build();
		List<InterviewFeedback> feedbacks = new ArrayList<>();
		
		InterviewFeedback feedback1 = new InterviewFeedback(1, associate1, manager, "test content", date);
		InterviewFeedback feedback2 = new InterviewFeedback(2, associate1, manager, "test content", date);
		feedbacks.add(feedback1);
		feedbacks.add(feedback2);
		
		when(ifServ.retrieveAllFeedback()).thenReturn(feedbacks);
		when(ifServ.retrieveFeedbackById(1)).thenReturn(feedback1);
		when(aServ.getAssociateById(1)).thenReturn(associate1);
		when(ifServ.retrieveFeedbackByAssociate(associate1)).thenReturn(feedbacks);
		when(mServ.getManagerById(1)).thenReturn(manager);
		when(ifServ.retrieveFeedbackByManager(manager)).thenReturn(feedbacks);		
	}

	/**
	 * Checking if getAllFeedback method is working my mocking a list of 
	 * InterviewFeedbacks; In this test I am only testing the first element
	 * in the list and if it is true we assume that the rest will specially
	 * that the status code is the right response
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllFeedbackTest() throws Exception {
		InterviewFeedback feedback1 = new InterviewFeedback(1, associate1, manager, "test content", date);
		this.mockMvc.perform(get("/feedback/all")).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].id", is(feedback1.getId())))
		.andExpect(jsonPath("$.[0].content", is(feedback1.getContent())));
	}
	
	/**
	 * This test will be the same as getAllFeedbackTest but for a specific 
	 * feedback_id
	 * 
	 * @throws Exception
	 */
	@Test
	public void getFeedbackByIdTest() throws Exception {
		InterviewFeedback feedback1 = new InterviewFeedback(1, associate1, manager, "test content", date);
		this.mockMvc.perform(get("/feedback/{id}", feedback1.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(feedback1.getId())))
		.andExpect(jsonPath("$.content", is(feedback1.getContent())));
	}
	
	/**
	 * This test will be the same as getAllFeedbackTest but for a specific 
	 * associate_id
	 * 
	 * @throws Exception
	 */
	@Test
	public void getFeedbackByAssocaiteTest() throws Exception {
		InterviewFeedback feedback2 = new InterviewFeedback(2, associate1, manager, "test content", date);
		this.mockMvc.perform(get("/feedback/associate/{id}", associate1.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$.[1].id", is(feedback2.getId())))
		.andExpect(jsonPath("$.[1].content", is(feedback2.getContent())));
	}
	
	/**
	 * This test will be the same as getAllFeedbackTest but for a specific 
	 * manager_id
	 * 
	 * @throws Exception
	 */
	@Test
	public void getFeedbackByManagerTest() throws Exception {
		InterviewFeedback feedback2 = new InterviewFeedback(2, associate1, manager, "test content", date);
		this.mockMvc.perform(get("/feedback/associate/{id}", manager.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$.[1].id", is(feedback2.getId())))
		.andExpect(jsonPath("$.[1].content", is(feedback2.getContent())));
	}
	
	/**
	 * This post test is for checking if the following inputs are valid and will
	 * result to giving a status OK. This test shouldn't affect the database
	 * 
	 * @throws Exception
	 */
	@Test
	public void postFeedbackTest() throws Exception {
		this.mockMvc.perform(post("/feedback").contentType(MediaType.APPLICATION_JSON).content(
			"{ "
			+ " \"associate_id\": 1,"
			+ " \"manager_id\": 1,"
			+ " \"content\": \"Test context\""
			+ " }"
		)).andExpect(status().isOk());
	}
	
	/**
	 * This put test is for checking if the following inputs are valid and will
	 * result to giving a status OK. This test shouldn't affect the database.
	 * 
	 * @throws Exception
	 */
	@Test
	public void putFeedbackTest() throws Exception {
		InterviewFeedback feedback1 = new InterviewFeedback(1, associate1, manager, "test content", date);
		this.mockMvc.perform(put("/feedback/{id}", feedback1.getId()).contentType(MediaType.APPLICATION_JSON).content(
			"{ "
			+ " \"associate_id\": 1,"
			+ " \"manager_id\": 1,"
			+ " \"content\": \"Test next context\""
			+ " }"
		)).andExpect(status().isOk());
	}
	
	/**
	 * This delete test is for checking if the following inputs are valid and will
	 * result to giving a status OK. This test shouldn't affect the database
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteFeedbackTest() throws Exception {
		InterviewFeedback feedback1 = new InterviewFeedback(1, associate1, manager, "test content", date);
		this.mockMvc.perform(delete("/feedback/{id}", feedback1.getId())).andExpect(status().isOk());
	}
}
