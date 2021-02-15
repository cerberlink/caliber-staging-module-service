package com.revature.backend.testing.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.backend.model.Associate;
import com.revature.backend.model.AssociateStatus;
import com.revature.backend.model.Batch;
import com.revature.backend.model.Manager;
import com.revature.backend.repository.AssociateRepository;
import com.revature.backend.repository.BatchRepository;
import com.revature.backend.service.AssociateServiceImpl;

@SpringBootTest
public class AssociateServiceTesting {

	/**
	 * The @Mock annotation will be placed above the dependencies that you need to mock in order to test your code. In this case,
	 * I need to mock the Repository layer in order for the service I am testing to work
	 */
	@Mock
	private AssociateRepository associateRepo;
	
	@Mock
	private BatchRepository batchRepo;
	/**
	 * The @InjectMocks will mark which field the injection should be performed on. In this specific case, 
	 * this will make sure that the mocked repositories above will be injected into the AssociateServiceImpl. 
	 */
	@InjectMocks
	private AssociateServiceImpl associateServ = mock(AssociateServiceImpl.class);
	
	
	Associate associate;
	Associate associate1;
	Manager manager;
	Batch batch1;
	Batch batch2;
	/**
	 *  The Setup method will setup all necessary pieces for the tests. The @BeforeEach will make the setup method run before each test.
	 * @throws Exception
	 */
	
	@BeforeEach 
	public void Setup() throws Exception {
		batch1 = new Batch(1,"Reston","Sophia","1","Java Full Stack");
		manager = new Manager(2,"test@revature.com","REAL_TEST_EMAIL","usery");
		associate = new Associate(1, "testemail@email.com", "test", "associate","1A", manager, batch1, AssociateStatus.STAGING);
		doNothing().when(associateServ).updateAssociate(associate);
	}
	/**
	 *  This test method will test to see if the associate gets updated properly. The @Test annotation will be placed above each and every test
	 */
	@Test
	public void TestUpdateSuccess() {
		associateServ.updateAssociate(associate);
		verify(associateServ, times(1)).updateAssociate(associate);
	}
}
