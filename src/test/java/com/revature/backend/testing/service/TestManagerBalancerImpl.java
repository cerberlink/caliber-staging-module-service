/**
 * This file contains unit tests for the class ManagerBalancerImpl, which implements
 * the ManagerBalancer interface.
 * 
 * @author Andrew Curry
 */
package com.revature.backend.testing.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.backend.model.Associate;
import com.revature.backend.model.Batch;
import com.revature.backend.model.Manager;
import com.revature.backend.service.ManagerBalancerImpl;

@SpringBootTest()
public class TestManagerBalancerImpl {

    // ----------
    // SETUP
    // ----------

    // This is wiring an Impl because I want to test some of the helper methods which are
    // defined in the Impl, but are not specified in the interface
	
	
	@InjectMocks
    private ManagerBalancerImpl managerBalancerImpl;
    @BeforeEach    
    public void setUp()
    {
    	
    }
    // ----------
    // TESTS
    // ----------

    // ----------
    // groupIntoBatches() TESTS
    // ----------

    /**
     * Tests a few different possibilities, expecting success in all of them
     */
    @Test
    public void testGroupIntoBatches(){
        testGroupIntoBatchesHelper(new int[] {});
        testGroupIntoBatchesHelper(new int[] {5, 7, 1});
        testGroupIntoBatchesHelper(new int[] {7, 5, 1});
        testGroupIntoBatchesHelper(new int[] {4, 4, 4, 3, 2, 1});
        testGroupIntoBatchesHelper(new int[] {9, 18, 17, 1, 2, 3});
    }

    // ----------
    // sortBatchesBySize() TESTS
    // ----------

    /**
     * Tests a few different possibilities, expecting success in all of them
     */
    @Test
    public void testSortBatchesBySize() {
        testSortBatchesBySizeHelper(new int[] {});
        testSortBatchesBySizeHelper(new int[] { 5, 7, 1});
        testSortBatchesBySizeHelper(new int[] { 7, 5, 1});
        testSortBatchesBySizeHelper(new int[] { 5, 7, 1, 4 });
        testSortBatchesBySizeHelper(new int[] { 5, 7, 1, 4, 3, 18, 20, 55 });
        testSortBatchesBySizeHelper(new int[] { 15, 18, 20, 55 });
    }

    // ----------
    // findManagerWithLeast() TESTS
    // ----------
    
    /**
     * Tests a few different possibilities, expecting success in all of them
     */
    @Test
    public void testFindManagerWithLeast(){
        testFindManagerWithLeastHelper(new int[] {20, 30});
        testFindManagerWithLeastHelper(new int[] {15, 25, 27});
        testFindManagerWithLeastHelper(new int[] {27, 25, 15});
        testFindManagerWithLeastHelper(new int[] {15, 25, 0, 1, 2, 3});
        testFindManagerWithLeastHelper(new int[] {15, 25, 100, 1, 2, 3});
    }

    // ----------
    // assignAssociatesEvenly() TESTS
    // ----------

    /**
     * Tests a few different possibilities, expecting success in all of them
     */
    @Test
    public void testAssignAssociatesEvenly(){
        testAssignAssociatesEvenlyHelper(new int[] {20, 30}, new int[] {5, 7, 1});
        testAssignAssociatesEvenlyHelper(new int[] {20, 30, 15}, new int[] {5, 7, 1, 20});
        testAssignAssociatesEvenlyHelper(new int[] {20}, new int[] {5, 7, 1, 20});
        testAssignAssociatesEvenlyHelper(
            new int[] {20, 20, 19}, new int[] {17, 19, 25, 8});
        testAssignAssociatesEvenlyHelper(
            new int[] {14, 8, 11, 5, 0}, new int[] {17, 19, 25, 14, 1, 3, 31, 27});
    }

    // ----------
    // balanceNewBatches() TESTS
    // ----------

    /**
     * Tests everything all at once, just to make sure it all works
     */
    @Test
    public void testBalanceNewBatches(){
        testBalanceNewBatchesHelper(new int[] {20, 30}, new int[] {5, 7, 1});
        testBalanceNewBatchesHelper(new int[] {20, 30, 15}, new int[] {5, 7, 1, 20});
        testBalanceNewBatchesHelper(new int[] {10, 11, 12}, new int[] {15, 17, 11});
        testBalanceNewBatchesHelper(new int[] {20, 40}, new int[] {25, 17, 31});
        testBalanceNewBatchesHelper(
            new int[] {20, 40, 10, 15}, new int[] {25, 17, 31, 8, 12, 19, 2, 16, 5, 20});
    }
    
    // ----------
	// HELPER METHODS
    // ----------

	/**
     * uses the given array of manager sizes to populate the given manager map.
     * Optionally, returns the number of associates assigned to the manager with the
     * least associates.
     */
    public int buildManagerMap(int[] managerSizes, Map<Manager, Integer> managerMap){
        int leastAssociates = managerSizes[0]; // to verify answer later
        for (int i = 0; i < managerSizes.length; i++){
            Manager manager = new Manager();
            manager.setId(i);
            int numAssociates = managerSizes[i];
            managerMap.put(manager, numAssociates);
            if (numAssociates < leastAssociates) leastAssociates = numAssociates;
        }
        return leastAssociates;
    }

    /**
     * Builds a 2D-array of test/mock associates, where the length of each child
     * array is equal to the corresponding int in lengths.
     * 
     * The associates will be assigned to mock batches, but all other fields will be
     * left blank.
     */
    private Associate[][] buildTestBatches(int[] lengths) {
        Associate[][] testBatches = new Associate[lengths.length][];
        for (int x = 0; x < lengths.length; x++) {
            int L = lengths[x];
            Associate[] batchArray = new Associate[L];
            Batch mockBatch = new Batch();
            mockBatch.setName("batch" + x);
            for (int i = 0; i < L; i++) {
                batchArray[i] = new Associate();
                batchArray[i].setBatch(mockBatch);
            }
            testBatches[x] = batchArray;
        }
        return testBatches;
    }

    /**
     * Determines the best balance score for managers/associates that will be generated
     * according to the given parameters in various tests.
     */
    private int calculateExpectedBalanceScore(int[] managerSizes, int[] batchSizes){
        for (int numAssociates : batchSizes){
            Arrays.sort(managerSizes); // so the smallest one is first
            managerSizes[0] = managerSizes[0] + numAssociates;
        }
        Arrays.sort(managerSizes); // smallest first, biggest last
        return managerSizes[managerSizes.length - 1] - managerSizes[0];
    }

    /**
     * Determines the difference between the manager with the most associates and the
     * manager with the least associates in the given map.
     */
    private int calculateActualBalanceScore(Map<Manager, Integer> managerMap){
        int lowestNumAssociates = -1;
        int highestNumAssociates = 0;
        for (int numAssociates : managerMap.values()){
            if (numAssociates < lowestNumAssociates || lowestNumAssociates == -1)
                lowestNumAssociates = numAssociates;
            if (numAssociates > highestNumAssociates) 
                highestNumAssociates = numAssociates;
        }
        return highestNumAssociates - lowestNumAssociates;
    }

    /**
     * Returns a new int[], with the same data as the given array, sorted into descending
     * order.
     * Will move things around in the given array.
     * This is a sloppy way of doing things for tests.
     */
    private int[] descendingSortIntArray(int[] given){
        Arrays.sort(given);
        int arrayLength = given.length;
        int[] result = new int[arrayLength];
        int rIndex = arrayLength - 1;
        for (int i = 0; i < arrayLength; i++){
            result[rIndex] = given[i];
            rIndex -= 1;
        }

        return result;
    }

    // ----------
    // groupIntoBatches() HELPERS
    // ----------

	/**
     * Builds a List of mock associates (which are placed in batches, each batch sized
     * according to a size from sizes), and then verifies that groupIntoBatches splits
     * them up correctly. The order of the mock list will be random.
     * 
     * @param lengths
     */
    public void testGroupIntoBatchesHelper(int[] sizes){
        List<Associate> associateList = generateAssociateList(sizes);
        Associate[][] groupedAssociates = managerBalancerImpl.groupIntoBatches(associateList);
        areAssociatesGroupedCorrectly(groupedAssociates, sizes);
    }

	/**
     * Generates a randomly-ordered list of associates, where associates are grouped into
     * batches, where each batch is sized according to a size from sizes
     */
    private List<Associate> generateAssociateList(int[] sizes) {
        List<Associate> associateList = new ArrayList<>();

        for (int i = 0; i < sizes.length; i++){
            Batch b = new Batch();
            b.setName("" + i);
            int size = sizes[i];
            for (int x = 0; x < size; x++){
                Associate a = new Associate();
                a.setBatch(b);
                associateList.add(a);
            }// end inner for loop
        } // end outer for loop

        Collections.shuffle(associateList); // randomizes order
        return associateList;
    }

    /**
     * Determines if the given 2-d array of associates is correctly grouped into batches;
     * that is, each sub-array contains associates from exactly one batch.
     * The sizes array will be used to verify that the batches are of the correct size
     * (eg, that all of the associates were included and were not modified)
     */
    private boolean areAssociatesGroupedCorrectly(
        Associate[][] groupedAssociates, int[] sizes) {
        
        assertEquals(sizes.length, groupedAssociates.length);
        for (Associate[] group : groupedAssociates){
            if (group.length == 0) continue; // shouldn't happen?
            Batch b = group[0].getBatch();
            int batchIndex = Integer.parseInt(b.getName()); // i set up the names this way
            assertEquals(sizes[batchIndex], group.length);
            // now make sure every associate in this group is in batch b
            for (int i = 1; i < group.length; i++){
                Associate a = group[i];
                assertEquals(a.getBatch(), b);
            }
        }
		return true; // no problems found
	}

    // ----------
    // sortBatchesBySize() HELPERS
    // ----------

    /**
     * Builds a mock group of batches (each batch sized according to a length from 
     * lengths), and then tests to make sure the managerBalancerImpl sorts it correctly.
     * 
     * @param lengths
     */
    private void testSortBatchesBySizeHelper(int[] lengths) {
        Associate[][] testBatches = buildTestBatches(lengths);
        managerBalancerImpl.sortBatchesBySize(testBatches);
        assertTrue(areBatchesSortedBySize(testBatches));
    }

    /**
     * Determines if the given 2-d array of associates is sorted in decreasing order of
     * size (largest batches first, smallest last)
     * 
     * @param batches
     * @return
     */
    private boolean areBatchesSortedBySize(Associate[][] batches) {
        if (batches.length < 2) return true; // trivially true
        int lastLength = batches[0].length;
        for (int i = 1; i < batches.length; i++){
            int currentLength = batches[i].length;
            if (currentLength > lastLength) return false;
            else lastLength = currentLength;
        }
        return true; // if no problems, it's sorted
    }

    // ----------
    // testFindManagerWithLeast() HELPERS
    // ----------

    /**
     * Runs a single test case, where each int in managerSizes corresponds to a manager
     * who has that many associates already assigned to them. It will generate a map of
     * managers accordingly, and verify that the correct one is selected.
     * 
     * NOTE: does not care how the implementation breaks ties for manager with least.
     * 
     * @param managerSizes 
     */
    private void testFindManagerWithLeastHelper(int[] managerSizes) {
        Map<Manager, Integer> managerMap = new HashMap<>();
        int leastAssociates = buildManagerMap(managerSizes, managerMap);

        Manager result = managerBalancerImpl.findManagerWithLeast(managerMap);
        int resultAssociates = managerMap.get(result);
        assertEquals(leastAssociates, resultAssociates);
    }
    
    // ----------
    // assignAssociatesEvenly() HELPERS
    // ----------

    /**
     * Runs a single test case on assignAssociatesEvenly, creating manager and incoming
     * batch data based on the parameters.
     * 
     * To evaluate the even-ness of the assignment, the manager with most and manager
     * with least are compared; the difference between the two is the "balance score".
     * The expected balance score will be compared to the actual. Additionally, each
     * associate will be checked to make sure that it has been assigned to one of the
     * managers in the managerMap.
     * 
     * It is assumed by this test that the findManagerWithLeast method works.
     * It is assumed that the actual associate objects are assigned according to the
     * changes made to the map (that is, the updated managerMap will be trusted to be
     * accurate)
     * 
     * @param managerSizes
     * @param batchSizes
     */
    private void testAssignAssociatesEvenlyHelper(int[] managerSizes, int[] batchSizes) {
        // build the managers and associates
        Map<Manager, Integer> managerMap = new HashMap<>();
        buildManagerMap(managerSizes, managerMap);

        batchSizes = descendingSortIntArray(batchSizes);
        Associate[][] batches = buildTestBatches(batchSizes);
        // calculate the expected balance score
        int expectedBalanceScore 
            = calculateExpectedBalanceScore(managerSizes, batchSizes);
        // run the actual implementation and verify it
        managerBalancerImpl.assignAssociatesEvenly(managerMap, batches);
        // is each associate assigned to a manager?
        for (Associate[] currentBatch : batches){
            for (Associate associate : currentBatch){
                Manager manager = associate.getManager();
                assertNotNull(manager);
                assertTrue(managerMap.keySet().contains(manager));
            } 
        }
        // is it correctly balanced?
        int actualBalanceScore = calculateActualBalanceScore(managerMap);
        assertEquals(expectedBalanceScore, actualBalanceScore);
    }

    // ----------
    // balanceNewBatchesHelper() HELPERS
    // ----------

    /**
     * Runs a single test case of the entire process, using manager and associate data
     * generated from the given parameters.
     */
    private void testBalanceNewBatchesHelper(int[] managerSizes, int[] batchSizes) {
        Map<Manager, Integer> managerMap = new HashMap<>();
        buildManagerMap(managerSizes, managerMap);
        List<Associate> associates = generateAssociateList(batchSizes);
        // what's the expected balance score?
        batchSizes = descendingSortIntArray(batchSizes);
        int expectedBalanceScore 
            = calculateExpectedBalanceScore(managerSizes, batchSizes);
        // call the implementation
        managerBalancerImpl.balanceNewBatches(managerMap, associates);
        // what's the actual balance score?
        int actualBalanceScore = calculateActualBalanceScore(managerMap);
        assertEquals(expectedBalanceScore, actualBalanceScore);
	}
}
