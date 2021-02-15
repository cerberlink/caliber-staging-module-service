package com.revature.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.backend.model.Associate;
import com.revature.backend.model.InterviewFeedback;
import com.revature.backend.model.Manager;

@Repository
public interface InterviewFeedbackRepository extends JpaRepository<InterviewFeedback, Integer>{
	
	InterviewFeedback findById(int id);
	List<InterviewFeedback> findByAssociate(Associate associate);
	List<InterviewFeedback> findByManager(Manager manager);
}