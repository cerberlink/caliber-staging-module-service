package com.revature.backend.testing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.backend.model.AnalysisItem;
import com.revature.backend.model.AnalysisType;
import com.revature.backend.model.Associate;
import com.revature.backend.model.Manager;
import com.revature.backend.model.Swot;
import com.revature.backend.repository.AnalysisItemRepository;
import com.revature.backend.repository.SwotRepository;
import com.revature.backend.service.SwotService;

@SpringBootTest
public class TestSwotService {
	
	@Mock
	private SwotRepository sRepo;
	
	@Mock
	private AnalysisItemRepository aiRepo;
	
	@InjectMocks
	private SwotService sServ;
	
	Swot swot;
	Optional<Swot> oSwot;
	List<Swot> swotList;
    AnalysisItem aItem;
    AnalysisItem mockAI1;
	AnalysisItem mockAI2;
	AnalysisItem mockAI3;
	AnalysisItem mockAI4;
    /*
        SETUP
    */
	
	@BeforeEach
	public void setUp() throws Exception {
		swotList = new ArrayList<>();
		List<AnalysisItem> mockAnalysisItems;
		mockAnalysisItems = new ArrayList<AnalysisItem>();
		Swot mockSwot1;
    	
    	mockAI1 = new AnalysisItem(1, "Strength", null, AnalysisType.STRENGTH, "Strength comment");
		mockAI2 = new AnalysisItem(2, "Weakness", null, AnalysisType.WEAKNESS, "Weakness comment");
		mockAI3 = new AnalysisItem(3, "Opportunity", null, AnalysisType.OPPORTUNITY, "Opportunity comment");
		mockAI4 = new AnalysisItem(4, "Threat", null, AnalysisType.THREAT, "Threat comment");
		mockAnalysisItems.add(mockAI1);
		mockAnalysisItems.add(mockAI2);
		mockAnalysisItems.add(mockAI3);
		mockAnalysisItems.add(mockAI4);
		mockSwot1 = new Swot(1, 
					new Associate(1, null, null, null, null, null, null, null), 
					new Manager(1, null, null, null), 
					new Timestamp(System.currentTimeMillis()), 
					new Timestamp(System.currentTimeMillis()),
					"description");
		mockSwot1.setAnalysisItems(mockAnalysisItems);
		
    	swotList.add(mockSwot1);
    	
    	when(sRepo.findAllByAssociateId(1)).thenReturn(swotList);
    	when(sRepo.findAllByAssociateId(2)).thenReturn(null);
    	when(sRepo.findAll()).thenReturn(swotList);
    	
    	aItem = new AnalysisItem();
    	swot = new Swot();
    	aItem.setSwot(swot);
    	when(aiRepo.save(aItem)).thenReturn(aItem);
    	when(aiRepo.save(null)).thenReturn(null);
    	
    	oSwot = Optional.of(new Swot());
    	when(sRepo.findById(any(Integer.class))).thenReturn(oSwot);
    	when(sRepo.findById(null)).thenReturn(oSwot);
		when(sRepo.save(any(Swot.class))).thenReturn(swot);
		when(sServ.retrieveAllSwotByAssociateId(1)).thenReturn(swotList);
		doNothing().when(aiRepo).deleteById(1); 
    }
    
    /*
        TESTS
    */

	/**
	 * Tests that the SwotRepository returns true when a new swot is created
	 */
	@Test
	public void testCreateNewSwotSuccess() {
		assertEquals(sServ.createNewSwot(swot), true);
		
	}
    
	/**
	 * Tests that the retrieveAllSwot() function returns a List of Swots
	 */
	@Test
	public void testRetrieveAllSwotSuccess() {
		assertEquals(sServ.retrieveAllSwot(), swotList);
	}
	
	/**
	 * Tests that the retrieveAllSwotByAssociateId(id) function returns a List of Swots
	 */
	@Test
    public void testRetrieveAllSwotByAssociateIdSuccess() {
    	assertEquals(sServ.retrieveAllSwotByAssociateId(1), swotList);
    }
	
	/**
	 * Tests that the retrieveAllSwotByAssociateId(id) function returns null if the 
	 * associate with the given ID has no Swots
	 */
	@Test
	public void testRetrieveAllSwotByAssociateIdFailure() {
		assertEquals(sServ.retrieveAllSwotByAssociateId(2), null);
	}
	
	/**
	 * Tests that the AnalysisItemRepository returns true when a new AnalysisItem is saved
	 */
	@Test
	public void testCreateNewItemSuccess() {
		assertEquals(sServ.createNewItem(aItem), true);
	}
	
	/**
	 * Tests that the AnalysisItemRepository returns false when attempting to save a null value
	 */
	@Test
	public void testCreateNewItemFailure() {
		assertEquals(sServ.createNewItem(null), false);
	}
	
	/**
	 * Tests that the AnalysisItemRepository returns a copy of the updated item when updating 
	 */
	@Test 
	public void testUpdateItemSuccess() { 
		assertEquals(sServ.updateItem(aItem), aItem); 
	}

	/**
	 * Tests that the deleteItem(AnalysisItem) function calls the deleteById(id) function in 
	 * the AnalysisItemRepository
	 */
	@Test 
	public void testDeleteItemSuccess() {
		sServ.deleteItem(1);
		verify(aiRepo).deleteById(1);
	}
	
	@Test
	public void testNoteSuccess() {
		assertEquals(sServ.retrieveAllSwotByAssociateId(1).get(0).getAnalysisItems().get(0).getNote(), mockAI1.getNote());
		assertEquals(sServ.retrieveAllSwotByAssociateId(1).get(0).getAnalysisItems().get(1).getNote(), mockAI2.getNote());
		assertEquals(sServ.retrieveAllSwotByAssociateId(1).get(0).getAnalysisItems().get(2).getNote(), mockAI3.getNote());
		assertEquals(sServ.retrieveAllSwotByAssociateId(1).get(0).getAnalysisItems().get(3).getNote(), mockAI4.getNote());
	}
	
	 
}