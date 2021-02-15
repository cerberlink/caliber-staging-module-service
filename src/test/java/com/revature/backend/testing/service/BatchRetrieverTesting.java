package com.revature.backend.testing.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.backend.model.api.ApiAssociateAssignment;
import com.revature.backend.model.api.ApiAssociateTemplate;
import com.revature.backend.model.api.ApiBatchTemplate;
import com.revature.backend.util.BatchRetriever;
import com.revature.backend.util.BatchRetrieverImpl;
import com.revature.backend.util.StagingListener;

@SpringBootTest(classes={BatchRetriever.class, BatchRetrieverImpl.class})
public class BatchRetrieverTesting {

	@MockBean
	StagingListener mockStagingListener;

    @Autowired
	BatchRetrieverImpl batchRetriever;

	@Test
	void testRetrieveNewlyStagingAssociates() {
		testRetrieveNewlyStagingAssociatesHelp(new int[]{});
		testRetrieveNewlyStagingAssociatesHelp(new int[]{0});
		testRetrieveNewlyStagingAssociatesHelp(new int[]{4});
		testRetrieveNewlyStagingAssociatesHelp(new int[]{4, 5});
		testRetrieveNewlyStagingAssociatesHelp(new int[]{2, 7, 15});
		testRetrieveNewlyStagingAssociatesHelp(new int[]{25, 17, 15, 30, 45, 11, 0});
	}

	void testRetrieveNewlyStagingAssociatesHelp(int[] batchSizes) {
		int numBatches = batchSizes.length;
		int numAssociates = 0;
		for (int size : batchSizes) numAssociates += size;

		// this list will be returned by the staging listener - the batchRetriever should
		// properly unpack the ApiAssociateTemplates from it
		List<ApiBatchTemplate> mockBatchList = generateBatchTemplates(batchSizes);
		
		when(mockStagingListener.getLatestBatches()).thenReturn(mockBatchList);

		List<ApiAssociateTemplate> associateTemplateList
			= batchRetriever.retrieveNewlyStagingAssociates();

		// make sure all of the associates are included
		assertEquals(numAssociates, associateTemplateList.size());
		// now check to make sure these are the correct associates
		// takes advantage of naming format in test generation method
		boolean[][] foundTracker = new boolean[numBatches][];
		for (int i = 0; i < batchSizes.length; i++){
			boolean[] foundBatch = new boolean[batchSizes[i]];
			foundTracker[i] = foundBatch;
		}

		for (ApiAssociateTemplate assoc : associateTemplateList){
			int batchIndex = Integer.parseInt(assoc.getLastName());
			int assocIndex = Integer.parseInt(assoc.getFirstName());

			assertTrue(batchIndex >= 0 && batchIndex < numBatches); // valid batch index?
			// valid assoc index?
			assertTrue(assocIndex >= 0 && assocIndex < batchSizes[batchIndex]);
			assertFalse(foundTracker[batchIndex][assocIndex]); // should be no duplicates
			foundTracker[batchIndex][assocIndex] = true; // mark that we found it
		}
		// now make sure that all expect associates were found
		for (boolean[] batch : foundTracker){
			for (boolean assoc : batch) assertTrue(assoc);
		}
	}

	@Test
	void testRetrieveNewlyStagingBatches() {
		testRetrieveNewlyStagingBatchesHelp(new int[]{});
		testRetrieveNewlyStagingBatchesHelp(new int[]{4});
		testRetrieveNewlyStagingBatchesHelp(new int[]{40});
		testRetrieveNewlyStagingBatchesHelp(new int[]{14, 8, 19});
		testRetrieveNewlyStagingBatchesHelp(new int[]{17, 20, 35, 4, 0, 12});
	}

	void testRetrieveNewlyStagingBatchesHelp(int[] batchSizes){
		// technically only need the batches, not their contents
		List<ApiBatchTemplate> mockBatchList = generateBatchTemplates(batchSizes);

		when(mockStagingListener.getLatestBatches()).thenReturn(mockBatchList);
		List<ApiBatchTemplate> b = batchRetriever.retrieveNewlyStagingBatches();

		// NOTE: could fail if batchRetriever returns a valid list that has been sorted
		// or re-arranged in some way
		assertEquals(mockBatchList, b);
	}

	// ----------
	// SHARED HELPER METHODS
	// ----------

	/**
	 * Generates a list of ApiBatchTemplates, where each batch is the size of the
	 * corresponding int in batchSizes.
	 * 
	 * Currently these templates will not be filled out; they will have 
	 * ApiAssociateAssignments, which will have ApiAssociateTemplates.
	 * Batch templates have names equal to their index in the list.
	 * Associates have a first name equal to their index inside their batch, and a last
	 * name equal to their batch's name.
	 * 
	 * @param batchSizes
	 * @return
	 */
	private List<ApiBatchTemplate> generateBatchTemplates(int[] batchSizes) {
		List<ApiBatchTemplate> batchTemplateList = new ArrayList<>();

		// make each Batch
		for (int i = 0; i < batchSizes.length; i++){
			ApiBatchTemplate batch = new ApiBatchTemplate();
			batch.setName("" + i);
			batchTemplateList.add(batch);
			int batchSize = batchSizes[i];
			ApiAssociateAssignment[] assignments = new ApiAssociateAssignment[batchSize];
			batch.setAssociateAssignments(assignments);
			// make each Assignment
			for (int x = 0; x < batchSize; x++){
				ApiAssociateAssignment assign = new ApiAssociateAssignment();
				assignments[x] = assign;
				// make the Associate
				ApiAssociateTemplate assoc = new ApiAssociateTemplate();
				assign.setAssociate(assoc);
				assoc.setFirstName("" + x);
				assoc.setLastName("" + i);
			} // end Assignment for loop
		} // end Batch for loop

		return batchTemplateList;
	}
}