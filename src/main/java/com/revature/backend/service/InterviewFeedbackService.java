package com.revature.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.backend.model.Associate;
import com.revature.backend.model.InterviewFeedback;
import com.revature.backend.model.Manager;
import com.revature.backend.repository.InterviewFeedbackRepository;

@Service("interviewFeedbackService")
public class InterviewFeedbackService {

	@Autowired
	InterviewFeedbackRepository ifRepo;

	/**
	 * Return to get all feedback
	 * 
	 * @return Returns to find all possible list of feedbacks 
	 */
	public List<InterviewFeedback> retrieveAllFeedback() {
		return ifRepo.findAll();
	}

	/**
	 * Return feedback with a certain id
	 * 
	 * @param id The id of the feedback that should be return
	 * @return Returns the id of the feedback
	 */
	public InterviewFeedback retrieveFeedbackById(int id) {
		return ifRepo.findById(id);
	}

	/**
	 * Return associate if found.
	 * 
	 * @param associate The list of associate that should be return
	 * @return Returns the associate of the feedback
	 */
	public List<InterviewFeedback> retrieveFeedbackByAssociate(Associate associate) {
		return ifRepo.findByAssociate(associate);
	}

	/**
	 * Return manager if found
	 * 
	 * @param manager The list of manager that should be return
	 * @return Returns if id of manager found.
	 */
	public List<InterviewFeedback> retrieveFeedbackByManager(Manager manager) {
		return ifRepo.findByManager(manager);
	}

	/**
	 * Inserts a new feedback
	 * 
	 * @param feedback The new information of feedback should be saved into the feedback
	 */
	public void insertFeedback(InterviewFeedback feedback) {
		ifRepo.save(feedback);
	}

	/**
	 * Updates a feedback
	 * 
	 * @param feedback Feedback supposed to be updated after saved
	 */
	public void updateFeedback(InterviewFeedback feedback) {
		ifRepo.save(feedback);
	}

	/**
	 * Deletes a feedback
	 * 
	 * @param feedback Feedback supposed to be deleted
	 */
	public void deleteFeedback(InterviewFeedback feedback) {
		ifRepo.delete(feedback);
	}
}
