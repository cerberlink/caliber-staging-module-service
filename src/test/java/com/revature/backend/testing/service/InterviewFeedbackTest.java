package com.revature.backend.testing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.revature.backend.model.Associate;
import com.revature.backend.model.AssociateStatus;
import com.revature.backend.model.Batch;
import com.revature.backend.model.InterviewFeedback;
import com.revature.backend.model.Manager;
import com.revature.backend.repository.AssociateRepository;
import com.revature.backend.repository.BatchRepository;
import com.revature.backend.repository.InterviewFeedbackRepository;
import com.revature.backend.service.InterviewFeedbackService;

public class InterviewFeedbackTest {

	/**
	 * The @Mock annotation will be placed above the dependencies that you need to
	 * mock in order to test your code. In this case, I need to mock the Repository
	 * layer in order for the service I am testing to work
	 */
	@Mock
	private InterviewFeedbackRepository interviewFeedRepo;

	@Mock
	private AssociateRepository associateRepo;

	@Mock
	private BatchRepository batchRepo;
	/**
	 * The @InjectMocks will mark which field the injection should be performed on.
	 * In this specific case, this will make sure that the mocked repositories above
	 * will be injected into the AssociateServiceImpl.
	 */
	@InjectMocks
	private InterviewFeedbackService interFeedService = mock(InterviewFeedbackService.class);
	Associate associate;
	Associate associate2;
	Manager manager;
	Manager manager2;
	Batch batch;
	InterviewFeedback interFeedback;
	InterviewFeedback interFeedback2;
	InterviewFeedback interFeedback3;
	InterviewFeedback interFeedback4;

	List<InterviewFeedback> interFeedManager = new ArrayList<>();
	List<InterviewFeedback> interFeedAll = new ArrayList<>();
	List<InterviewFeedback> interFeedAssociate = new ArrayList<>();

	Timestamp t;

	/**
	 * The Setup method will setup all necessary pieces for the tests.
	 * The @BeforeEach will make the setup method run before each test.
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		batch = new Batch(1, "Reston", "Sophia", "1", "Java Full Stack");
		manager = new Manager(1, "Testemail@email.email", "test", "user");
		manager2 = new Manager(2, "test@revature.com", "REAL_TEST_EMAIL", "usery");
		associate = new Associate(1, "testemail@email.com", "test", "associate", "1A", manager, batch,
				AssociateStatus.STAGING);
		associate2 = new Associate(2, "testemail@email.com", "testB", "associate", "1B", manager, batch,
				AssociateStatus.STAGING);
		t = new Timestamp(System.currentTimeMillis());
		interFeedback = new InterviewFeedback(1, associate, manager, "Great speaker", t);
		interFeedback2 = new InterviewFeedback(2, associate2, manager, "Great programmer", t);
		interFeedback3 = new InterviewFeedback(3, associate, manager2, "Great everything", t);
		interFeedback4 = new InterviewFeedback(4, associate2, manager2, "Who hired this guy?", t);

		// setup for retrieveall testing
		interFeedAll.add(interFeedback);
		interFeedAll.add(interFeedback2);
		interFeedAll.add(interFeedback3);
		interFeedAll.add(interFeedback4);
		// end of setup for retriveall testing
		// set up for retrievebymanager testing
		interFeedManager.add(interFeedback);
		interFeedManager.add(interFeedback2);
		// end of setup for retrievebymanager testing
		// setup for retrievebyassociatetesting
		interFeedAssociate.add(interFeedback);
		interFeedAssociate.add(interFeedback3);
		// end of setup for retrievebyassociatetesting
		// Verify Testing
		doNothing().when(interFeedService).insertFeedback(interFeedback);
		doNothing().when(interFeedService).updateFeedback(interFeedback);
		doNothing().when(interFeedService).deleteFeedback(interFeedback);
		// end of verify testing
		// positive testing
		when(interFeedService.retrieveFeedbackById(1)).thenReturn(interFeedback);
		when(interFeedService.retrieveFeedbackByManager(manager)).thenReturn(interFeedManager);
		when(interFeedService.retrieveAllFeedback()).thenReturn(interFeedAll);
		when(interFeedService.retrieveFeedbackByAssociate(associate)).thenReturn(interFeedAssociate);
		// end of positive testing
		// negative testing
		when(interFeedService.retrieveFeedbackByManager(null)).thenReturn(null);
		when(interFeedService.retrieveFeedbackById(100)).thenReturn(null);
		when(interFeedService.retrieveFeedbackByAssociate(null)).thenReturn(null);
		// end of negative testing

	}

	@Test
	public void getAllTest() {
		assertEquals(interFeedService.retrieveAllFeedback(), interFeedAll);
	}

	@Test
	public void getByIdSuccess() {
		assertEquals(interFeedService.retrieveFeedbackById(1), interFeedback);
	}

	@Test
	public void getByIdFailure() {
		assertEquals(interFeedService.retrieveFeedbackById(100), null);
	}

	@Test
	public void getByManagerSuccess() {
		assertEquals(interFeedService.retrieveFeedbackByManager(manager), interFeedManager);
	}

	@Test
	public void getByManagerFailure() {
		assertEquals(interFeedService.retrieveFeedbackByManager(null), null);

	}

	@Test
	public void getByAssociateSuccess() {
		assertEquals(interFeedService.retrieveFeedbackByAssociate(associate), interFeedAssociate);

	}

	@Test
	public void getByAssociateFailure() {
		assertEquals(interFeedService.retrieveFeedbackByAssociate(null), null);
	}

	@Test
	public void insertSuccess() {
		interFeedService.insertFeedback(interFeedback);
		verify(interFeedService, times(1)).insertFeedback(interFeedback);
	}

	@Test
	public void updateSuccess() {
		interFeedService.updateFeedback(interFeedback);
		verify(interFeedService, times(1)).updateFeedback(interFeedback);
	}

	@Test
	public void deleteSuccess() {
		interFeedService.deleteFeedback(interFeedback);
		verify(interFeedService, times(1)).deleteFeedback(interFeedback);
	}
}
