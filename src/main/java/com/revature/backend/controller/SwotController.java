package com.revature.backend.controller;

import static com.revature.backend.util.ClientMessageUtil.CREATION_FAILED;
import static com.revature.backend.util.ClientMessageUtil.DELETION_FAILED;
import static com.revature.backend.util.ClientMessageUtil.SUCCESSFULLY_CREATED;
import static com.revature.backend.util.ClientMessageUtil.SUCCESSFULLY_DELETED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.backend.model.AnalysisItem;
import com.revature.backend.model.Swot;
import com.revature.backend.service.SwotService;
import com.revature.backend.util.ClientMessage;

@RestController("swotController")
@RequestMapping("/swot")
public class SwotController {

	@Autowired
	private SwotService swotService;

	/**
	 * 
	 * @param swot
	 * @return a 201 HTTP status with a message indicating whether or not creation was successful
	 * 
	 * POST request to create a new SWOT for an Associate with AnalysisItems within
	 * it as a collection.
	 * 
	 * Takes in a Swot as a nested JSON object: the AnalysisItems will be found
	 * within a JSON array.
	 * 
	 * Returns a 201 status with a message for the client within the HTTP body as a
	 * JSON object if successful.
	 */
	@PostMapping(path = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ClientMessage> createSwot(@RequestBody Swot swot) {
		ClientMessage body = swotService.createNewSwot(swot) ? SUCCESSFULLY_CREATED : CREATION_FAILED;
		System.out.println(swot);
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	/**
	 * 
	 * @param associateId
	 * @return a list of all SWOTs for the corresponding associate
	 * 
	 * GET request for fetching all SWOTs based on an Associate's id as found in the
	 * RESTful URL.
	 * 
	 * Returns the List of SWOTs as a JSON array with a 200 code if successful.
	 */
	@GetMapping(path = "/view/{associateId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Swot>> viewSwot(@PathVariable("associateId") int associateId) {
		List<Swot> swotList = swotService.retrieveAllSwotByAssociateId(associateId);
		return ResponseEntity.ok(swotList);
	}

	/**
	 * 
	 * @return List of all SWOTs in the database with a 200 HTTP status.
	 * 
	 * GET request for fetching all existing SWOTs in the system. Returns the List
	 * as a JSON array.
	 * 
	 * This will more than likely be removed in the future and was only used for
	 * proof of concept.
	 */
	@GetMapping(path = "/view/all", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Swot>> viewAllSwot() {
		List<Swot> swotList = swotService.retrieveAllSwot();
		return ResponseEntity.ok(swotList);
	}
	
	/**
	 * 
	 * @param swotId
	 * @return ResponseEntity with a 200 HTTP status indicating whether or not deletion was successful
	 * 
	 * DELETE request for deleting a SWOT. Returns ResponseEntity with a message indicating whether
	 * or not deletion was successful.
	 */
	@DeleteMapping(path = "/delete/{swotId}")
	public ResponseEntity<ClientMessage> deleteSwot(@PathVariable("swotId") int swotId) {
		ClientMessage body = swotService.deleteSwot(swotId) ? SUCCESSFULLY_DELETED : DELETION_FAILED;
		return ResponseEntity.ok(body);
	}

	/**
	 * 
	 * @param analysisItem
	 * @return ResponseEntity with 201 HTTP status indicating whether or not creation was successful
	 * 
	 * POST request to create a new AnalysisItem for a particular SWOT.
	 * 
	 * Takes in an AnalysisItem from a JSON object.
	 * 
	 * Returns a 201 HTTP status with an informative client message in the body.
	 */
	@PostMapping(path = "/item/new", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ClientMessage> createItem(@RequestBody AnalysisItem analysisItem) {
		ClientMessage body = swotService.createNewItem(analysisItem) ? SUCCESSFULLY_CREATED : CREATION_FAILED;
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	/**
	 * 
	 * @param analysisItem
	 * @return ResponseEntity<AnalysisItem> containing the updated analysis item or a 204 status code in event of failure
	 * 
	 * PUT request to update an AnalysisItem.
	 * 
	 * Takes in an AnalysisItem *with* altered fields from a JSON object.
	 * 
	 * If the update failed, returns a 204 status code.
	 * 
	 * If successful, returns a 200 status code and the updated object.
	 */
	@PutMapping(path = "/item/update/{analysisItemId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AnalysisItem> updateItem(@RequestBody AnalysisItem analysisItem) {
		AnalysisItem updatedItem = swotService.updateItem(analysisItem);
		if (updatedItem == null) { // A null object denotes that the update had failed or done nothing.
			return ResponseEntity.noContent().build(); // 204 would be the proper response for a PUT that changes nothing.
		} else { // Success, return the item.
			return ResponseEntity.ok(updatedItem);
		}
	}

	/**
	 * 
	 * @param analysisItemId
	 * @return ResponseEntity<ClientMessage> indicating whether the delete was successful
	 * 
	 * DELETE request to delete an AnalysisItem from a particular SWOT.
	 * 
	 * Takes in the id of the AnalysisItem as a variable from the URL.
	 * 
	 * Returns an informative client message and a 200 status code.
	 */
	@DeleteMapping(path = "/item/delete/{analysisItemId}")
	public ResponseEntity<ClientMessage> deleteSwotItem(@PathVariable("analysisItemId") int analysisItemId) {
		ClientMessage body = swotService.deleteItem(analysisItemId) ? SUCCESSFULLY_DELETED : DELETION_FAILED;
		return ResponseEntity.ok(body);
	}
}
