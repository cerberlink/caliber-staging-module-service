package com.revature.backend.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.backend.model.Associate;
import com.revature.backend.model.AssociateStatus;
import com.revature.backend.model.Batch;
import com.revature.backend.model.dto.AssociateDTO;
import com.revature.backend.service.AssociateServiceImpl;
import com.revature.backend.service.BackendService;
import com.revature.backend.service.BatchServiceImpl;

@RestController
public class AssociateController {

	@Autowired
	BackendService backendService;
	
	@Autowired
	AssociateServiceImpl assocService;
	
	@Autowired
	BatchServiceImpl batchService;

	/**
	 * Gets associates for a given manager
	 * @param manager - id of manager
	 * @return - list of associates for that manager
	 */
	@GetMapping("/associates")
	public ResponseEntity<List<AssociateDTO>> getAssociates(@RequestParam int manager) {
		List<AssociateDTO> body = null;
		List<Associate> associates = backendService.findAssociatesByManagerId(manager);

		if (associates == null || associates.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			body = new ArrayList<>();
			for (Associate a : associates) {
				body.add(new AssociateDTO(a));
			}
			return new ResponseEntity<>(body, HttpStatus.OK);
		}
	}

	/**
	 * Given a manager id, will return associates assigned to that manager and have
	 * enter staging in the last 7 days.
	 * 
	 * @param manager
	 * @return
	 */

	@GetMapping("/associates/new")
	public ResponseEntity<List<AssociateDTO>> getNewAssociates(@RequestParam int manager) {
		List<AssociateDTO> body = null;
		List<Associate> associates = backendService.findNewAssociatesByManagerId(manager);
		if (associates == null || associates.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			body = new ArrayList<>();
			for (Associate a : associates) {
				body.add(new AssociateDTO(a));
			}
			return new ResponseEntity<>(body, HttpStatus.OK);
		}
	}
	
	/**
	 * Given a LinkedHashMap, gets the associate's id and tries to look them up. If
	 * they exist, gets the batch's id and tries to find it. If it exists, assigns
	 * that batch to the associate given. Also sets the status of the associate.
	 * 
	 * @param assocMap a LinkedHashMap holding the info we need to update
	 * @return returns a response string indicating whether the update was a success or not
	 */
	@PutMapping("/associates")
	public ResponseEntity<String> updateAssociate(@RequestBody LinkedHashMap<String, Integer> assocMap){
		Associate associate = assocService.getAssociateById(assocMap.get("associate_id"));
		if (associate == null ) {
			return new ResponseEntity<>("Could not find associate", HttpStatus.NO_CONTENT);
		} else {
			if (assocMap.get("status_id") != null ) {
				switch(assocMap.get("status_id")) {
				case 0:
					associate.setStatus(AssociateStatus.STAGING);
					break;
				case 1:
					associate.setStatus(AssociateStatus.PROJECT);
					break;
				case 2:
					associate.setStatus(AssociateStatus.RELEASED);
					break;
				default:
					associate.setStatus(associate.getStatus());
					break;
				}
				assocService.updateAssociate(associate);
			}
			
			if(assocMap.get("batch_id") != null) {
				Batch batch = batchService.getBatchById(assocMap.get("batch_id"));
				if (batch == null ) {
					return new ResponseEntity<>("Could not find batch", HttpStatus.NO_CONTENT);
				} else {
					associate.setBatch(batch);
					assocService.updateAssociate(associate);
					return new ResponseEntity<>("Associate updated successfully", HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>("Could not find batch", HttpStatus.NO_CONTENT);
			}
		}
	}
}
