package com.revature.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.backend.model.Swot;

@Repository
public interface SwotRepository extends JpaRepository<Swot, Integer> {

	// TODO: There was a conflict here and I wasn't sure which one was correct.
//	@Query(value = "select * from swot_analysis where associate =?1", nativeQuery = true)
	@Query(value = "select * from swot_analysis where associate_id =?1", nativeQuery = true)
	List<Swot> findAllByAssociateId(int associateId);
	Swot findById(int swotId);
}