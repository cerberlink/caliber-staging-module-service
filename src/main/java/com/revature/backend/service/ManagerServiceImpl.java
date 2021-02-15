package com.revature.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.backend.model.Associate;
import com.revature.backend.model.Manager;
import com.revature.backend.repository.BackendRepo;
import com.revature.backend.repository.ManagerRepository;

@Service("selectAllManagers")
public class ManagerServiceImpl implements ManagerService {
	
	private static Logger log = Logger.getLogger(ManagerServiceImpl.class);
	
	@Autowired
	ManagerRepository managerRepo;
	
	@Autowired
	BackendRepo backendRepo;

	/* 
	 * This class will return a list of Manager objects as well as Manager - Associate mappings.
	 * 
	 * getAllManagers() has been completed. A map that returns Manager and Associate objects has been implemented.
	 * 
	 */
	
	
	@Override
	public List<Manager> saveAll(List<Manager> mList) {
		List<Manager> result = new ArrayList<>();

		for(Manager m : mList)
		{
			result.add(managerRepo.save(m));
		}
		 return result;
		
	}

	public List<Manager> getAllManagers() {
		List<Manager> managerList = new ArrayList<>();
		
		try {
			log.info("finding managers");
			managerList = managerRepo.findAllManagers();
		} catch(Exception e) {
			
			if(managerList.size() != 0) {
				log.info("Attempting to retrieve Manager objects.");
				return managerList;
				
			} 
			log.warn("Unable to retrieve managers - Service Layer", e);
				return null;
		}
		log.info("Size of manager list is " + managerList.size());
		return managerList;
	}
	
	public Manager getManagerById(int id) {
		return managerRepo.findById(id);
	}
	

	@Override
	public Map<Manager, Integer> getAllManagersAndAssociates() {
		
		List<Manager> managers;
		managers = getAllManagers();
		Map <Manager, Integer> map = new HashMap<Manager, Integer>();
		
		for(Manager manager : managers) {
			
			List<Associate> associates 
				= backendRepo.findAssociatesByManagerId(manager.getId());
			int associateSize = associates.size();
			
			map.put(manager, associateSize);
		}
		return map;
	}





	
	// method to add in managers to the database
	// Session storage can possibly add managers
	
	

}
