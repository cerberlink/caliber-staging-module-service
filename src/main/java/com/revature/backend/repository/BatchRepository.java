package com.revature.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.backend.model.Batch;


@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer>{
	public Batch findById(int id);
}
