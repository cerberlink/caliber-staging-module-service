package com.revature.backend.controller;

import java.util.List;

import com.revature.backend.model.Manager;
import com.revature.backend.service.ManagerService;
import com.revature.backend.util.ClientMessage;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {

	private static Logger log = Logger.getLogger(ManagerController.class);

	@Autowired
	ManagerService managerService;

	//not Ideal path name dont use a method's name, use nouns
	//i am leaving it like this but this should be "/manager" unless that path is used already
	@PostMapping("/getmanager")
	public ResponseEntity<ClientMessage> getLoggedInManager(@RequestBody ClientMessage email) {

		List<Manager> allManager = managerService.getAllManagers();
		for (Manager m : allManager) {
			if (m.getEmail().equals(email.getMessage())) {
				log.info("Found matching manager!");
				ClientMessage message = new ClientMessage();
				message.setMessage(Integer.toString(m.getId()));
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
		}
		
		log.info("No manager found in DB with that email, creating new manager");
		
		/**
		 * If here, no manager with email exists in DB, automatically add manager to DB
		 */
		Manager m = new Manager(email.getMessage(), "No Data", "No Data");
		allManager.clear();
		allManager.add(m);
		List<Manager> mList = managerService.saveAll(allManager);
		ClientMessage message = new ClientMessage();
		message.setMessage(Integer.toString(mList.get(0).getId()));
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
