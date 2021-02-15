package com.revature.backend.repository;

import java.util.List;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.backend.model.Manager;

@Transactional
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	
	@Query(value = "SELECT * FROM manager", nativeQuery = true)
	List<Manager> findAllManagers();
	
	@Query(value = "SELECT * FROM manager WHERE id=?", nativeQuery= true)
	public List<Manager> saveAll(List<Manager> mList);
	
	public Manager findById(int id);

}
