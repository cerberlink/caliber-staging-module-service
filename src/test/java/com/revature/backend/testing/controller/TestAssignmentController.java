/**
 * This class contains unit tests for the AssignmentController class
 * 
 * @author Andrew Curry
 */

package com.revature.backend.testing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.revature.backend.controller.AssignmentController;
import com.revature.backend.model.Associate;
import com.revature.backend.model.Batch;
import com.revature.backend.model.api.ApiAssociateAssignment;
import com.revature.backend.model.api.ApiAssociateTemplate;
import com.revature.backend.model.api.ApiBatchTemplate;
import com.revature.backend.service.ManagerBalancer;
import com.revature.backend.service.ManagerService;
import com.revature.backend.util.BatchRetriever;
import com.revature.backend.util.BatchWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {AssignmentController.class})
public class TestAssignmentController {
    
    // ----------
    // SETUP
    // ----------

    @MockBean
    private ManagerBalancer mockManagerBalancer;

    @MockBean
    private ManagerService mockManagerService;

    @MockBean
    private BatchWriter mockBatchWriter;

    @MockBean
    private BatchRetriever mockBatchRetriever;

    @Autowired
    private AssignmentController assignmentController;

    // ----------
    // TESTS
    // ----------

    // ----------
    // convertToBatch TESTS
    // ----------

    /**
     * Generate a dummy ApiBatchTemplate and make sure it is converted properly.
     */
    @Test
    public void testConvertToBatch(){
        // the Templates have many more fields than this, but we're only checking what
        // gets converted to a Batch
        String salesforceId = "salesforceId";
        String name = "name";
        String skill = "skill";
        String location = "location";
        // the input
        ApiBatchTemplate template = new ApiBatchTemplate();
        template.setBatchId(salesforceId);
        template.setName(name);
        template.setSkill(skill);
        template.setLocation(location);
        // the expected and actual output
        Batch expected = new Batch(salesforceId, name, skill, location);
        Batch actual = assignmentController.convertToBatch(template);
        assertEquals(expected, actual);
    }

    // ----------
    // convertToAssociate TESTS
    // ----------

    /**
     * Generate a dummy ApiAssociateTemplate and make sure it is converted properly.
     */
    @Test
    public void testConvertToAssociate(){
        // the Templates have more fields than this, but we're only checking what
        // gets converted to an Associate
        String salesforceId = "salesforceId";
        String email = "email";
        String firstName = "firstName";
        String lastName = "lastName";
        // the input
        ApiAssociateTemplate template = new ApiAssociateTemplate();
        template.setSalesforceId(salesforceId);
        template.setEmail(email);
        template.setFirstName(firstName);
        template.setLastName(lastName);
        // the expected and actual output
        Associate expected 
            = new Associate(salesforceId, email, firstName, lastName, null, null, null);
        Associate actual = assignmentController.convertToAssociate(template);
        assertEquals(expected, actual);
    }

    // ----------
    // addNewBatches TESTS
    // ----------

    /**
     * Generates some dummy data and makes sure it is processed correctly.
     * Assumes the convert methods work (as they are tested separately)
     */
    @Test
    public void testAddNewBatches(){
        ApiAssociateTemplate assocTemplate = new ApiAssociateTemplate();
        ApiAssociateAssignment assignment = new ApiAssociateAssignment();
        assignment.setAssociate(assocTemplate);
        ApiAssociateAssignment[] assignmentArray 
            = new ApiAssociateAssignment[]{assignment};
        ApiBatchTemplate batchTemplate = new ApiBatchTemplate();
        batchTemplate.setAssociateAssignments(assignmentArray);
        List<ApiBatchTemplate> batchTemplateList = new ArrayList<>();
        batchTemplateList.add(batchTemplate);
        when(mockBatchRetriever.retrieveNewlyStagingBatches())
            .thenReturn(batchTemplateList);
        assignmentController.addNewBatches();
        // could go into more detail to make sure addNewBatches() behaves correctly...
        verify(mockBatchWriter).writeNewlyStagingBatches(any());
        verify(mockManagerService).getAllManagersAndAssociates();
        verify(mockManagerBalancer).balanceNewBatches(any(), any());
        verify(mockBatchWriter).writeNewlyStagingAssociates(any());
    }

    /**
     * Tests that the assignmentController properly detects that there are no new batches.
     */
    @Test
    public void testAddNewBatchesZero(){
        List<ApiBatchTemplate> batchTemplates = new ArrayList<>();
        when(mockBatchRetriever.retrieveNewlyStagingBatches()).thenReturn(batchTemplates);
        assignmentController.addNewBatches();
        // shouldn't persist anything to the database
        verifyNoInteractions(mockBatchWriter, mockManagerBalancer, mockManagerService);
    }
}
