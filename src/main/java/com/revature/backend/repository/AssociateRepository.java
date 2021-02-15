package com.revature.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.backend.model.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Integer>{
	public Associate findById(int id);
}
