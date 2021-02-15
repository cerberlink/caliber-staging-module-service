package com.revature.backend.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.backend.model.Associate;

//Abandon all hope, ye who enter here 

/*
 * This interface and its clone (no we still don't know why there are two of these backend repos, or why there is a backend repo to begin with) 
 * stand as a testament to poor decision making
 * 
 * Start your p3 by refactoring significant portions of this codebase 
 * You will save yourself from the nonsense that we ended up just having to deal with.
 */
 			// Yours Truly,
			// The dev team who inherited this project before you

@Transactional
@Repository
public interface BackendRepository extends JpaRepository<Associate, Long> {

  @Query(value = "SELECT * FROM associate WHERE manager_id = ?1", nativeQuery = true)
  List<Associate> findAssociatesByManagerId(int id);

}
