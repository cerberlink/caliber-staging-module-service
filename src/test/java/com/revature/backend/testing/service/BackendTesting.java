package com.revature.backend.testing.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.backend.model.Associate;
import com.revature.backend.model.AssociateStatus;
import com.revature.backend.model.Batch;
import com.revature.backend.model.Manager;
import com.revature.backend.model.api.ApiBatchTemplate;
import com.revature.backend.repository.BackendRepo;
import com.revature.backend.service.BackendServiceImpl;
import com.revature.backend.util.GetBatchById;

@SpringBootTest()
class BackendTesting {

	@Mock
	BackendRepo repo;

	@Mock
	GetBatchById getbatch;

	@Autowired
	@InjectMocks
	private BackendServiceImpl backend;

	List<Associate> expected;

	@Test
	void contextLoads() {
	}
	Batch batch = new Batch(1, "salesID", "name", "skill", "location");
	Batch batch1 = new Batch(2, "salesID", "name", "skill", "location");
	Manager manager = new Manager(1, "manager@manager.com", "Demo", "Manager");
	List<Associate> associates;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		associates = new ArrayList<>();

		when(repo.findAssociatesByManagerId(1)).thenReturn((associates));
		// create a mock batch ending within the last 7 days
		ApiBatchTemplate newBatch = new ApiBatchTemplate();
		newBatch.setId(1);
		newBatch.setEndDate(LocalDate.now().toString());

		// create a mock batch ending beyond the last 7 days
		ApiBatchTemplate oldBatch = new ApiBatchTemplate();
		oldBatch.setId(2);
		oldBatch.setEndDate("2020-10-10");

		when(getbatch.getBatch(1)).thenReturn(newBatch);
		when(getbatch.getBatch(2)).thenReturn(oldBatch);

		expected = backend.findNewAssociatesByManagerId(1);
	}

	/**
	 * Tests logic in the BackendServiceImpl.findAssociatesByManagerId()
	 * 
	 * Ensures that only associates assigned to the manager's id are returned.
	 * 
	 * The test assumes that a manager will always have at least one associate
	 * assigned to him or her.
	 * 
	 * The test uses assertFalse to test if the list is not empty.
	 * 
	 * @author Mareo Yapp
	 */
	@Test
	public void PASSfindAssociatesByManagerId() {
		associates.add(new Associate(1, "salesID", "email@email.com", "John", "Doe", manager, batch, AssociateStatus.STAGING));
		associates.add(new Associate(2, "salesID", "email@email.com", "John", "Doe", manager, batch1,AssociateStatus.STAGING));
		List<Associate> a = backend.findAssociatesByManagerId(1);
		assertFalse(a.isEmpty());
	}

	/**
	 * Tests logic in the BackendServiceImpl.findAssociatesByManagerId()
	 * 
	 * Ensures that if the manager id does not exists on the database then a empty
	 * list is return
	 * 
	 * The test uses assertTrue to test if the list is empty.
	 * 
	 * @author Mareo Yapp
	 */
	@Test
	public void FAILfindAssociatesByManagerId() {
		associates.add(	new Associate(1, "salesID", "email@email.com", "John", "Doe", manager, batch, AssociateStatus.STAGING));
		associates.add(new Associate(2, "salesID", "email@email.com", "John", "Doe", manager, batch1,AssociateStatus.STAGING));
		List<Associate> a = backend.findAssociatesByManagerId(0);
		assertTrue(a.isEmpty());
	}

	/*
	 * Tests logic in BackendServiceImpl.findNewAssociatesByManagerId() Mocking
	 * logic of GetBatchById logic. Ensures that only associates whose batch ended
	 * within the last 7 days gets returned. Creates a mock batch to be returned
	 * that has an end date of LocalDateTime.now()
	 * 
	 */
	@Test
	public void findNewAssociates() {

		associates.add(new Associate(1, "salesID", "email@email.com", "John", "Doe", manager, batch, AssociateStatus.STAGING));
		associates.add(new Associate(2, "salesID", "email@email.com", "John", "Doe", manager, batch1,AssociateStatus.STAGING));
		expected = backend.findNewAssociatesByManagerId(1);
		// assert that only the 1 associate is returned
		assertEquals(1, expected.size());

	}

	/*
	 * Tests that multiple associates will be returned if from the same batch that
	 * ended today
	 * 
	 */

	@Test
	public void findNewAssociatesMultiple() {
		associates.add(new Associate(1, "salesID", "email@email.com", "John", "Doe", manager, batch, AssociateStatus.STAGING));
		associates.add(new Associate(2, "salesID", "email@email.com", "John", "Doe", manager, batch, AssociateStatus.STAGING));

		expected = backend.findNewAssociatesByManagerId(1);

		// assert that both associates are returned
		assertEquals(2, expected.size());

	}

	/*
	 * Tests that the list returned of associates will be empty if they are not
	 * apart of batches that ended within the last 7 days.
	 */

	@Test
	public void findNewAssociatesFail() {

		associates.add(new Associate(1, "salesID", "email@email.com", "John", "Doe", manager, batch1,AssociateStatus.STAGING));
		associates.add(new Associate(2, "salesID", "email@email.com", "John", "Doe", manager, batch1,AssociateStatus.STAGING));

		ApiBatchTemplate oldBatch = new ApiBatchTemplate();
		oldBatch.setId(2);
		oldBatch.setEndDate("2020-10-10");
		
		List<Associate> expected = backend.findNewAssociatesByManagerId(1);

		// assert that no associates are returned
		assertEquals(0, expected.size());

	}

}