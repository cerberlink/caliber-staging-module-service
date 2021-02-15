package com.revature.backend.service;

import java.util.List;

import com.revature.backend.model.Batch;
import com.revature.backend.repository.BatchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("batchService")
public class BatchServiceImpl implements BatchService {

	@Autowired
    BatchRepository batchRepo;
	
	/**
	 *  The method get all batch object from the database
	 */
    @Override
    public List<Batch> getAllBatches() {
        return null;
    }
    
	/**
	 *  accept a list of batch object as argument and the method save individual batch as individual record to the database 
	 */
    @Override
    public List<Batch> saveBatches(List<Batch> bList) {
        // call the appropriate Repo method to save (aka insert) batches into DB
        List<Batch> list = batchRepo.saveAll(bList);
        return list;
    }
    
    
    /**
	 *  accept id as argument and get the record associate with the id
	 */
    public Batch getBatchById(int id) {
    	// TODO call the appropriate Repo method to save (aka insert) batches into DB
    	return batchRepo.findById(id);
    }
    
}
