package com.revature.backend.service;

import java.util.List;

import com.revature.backend.model.Associate;
import com.revature.backend.repository.AssociateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("associateService")
public class AssociateServiceImpl implements AssociateService {

	@Autowired
    AssociateRepository associateRepo;

    @Override
    public List<Associate> getAllAssociates() {
        return null;
    }

    /**
     * Inserts a group of associates into the DB
     * @param aList - list of associate objects
     * @return - shouldn't actually return anything, previous batch wrote it and it's too late to refactor
     * 			for this batch, please consider removing the return statement
     */
    @Override
    public List<Associate> saveAssociates(List<Associate> aList) {
        // call the appropriate Repo method to save (aka insert) associates into DB
        List<Associate> list = associateRepo.saveAll(aList);
        return list;
    }
    
    /**
     * Gets associate by given ID
     * @param id - Associate ID
     * @return - Associate with given ID
     */
    public Associate getAssociateById(int id) {
    	return associateRepo.findById(id);
    }
    
    /**
     * Updates an associate in DB
     * @param associate - updated associate object with ID of existing associate
     */
    public void updateAssociate(Associate associate) {
    	associateRepo.save(associate);
    }
    
}
