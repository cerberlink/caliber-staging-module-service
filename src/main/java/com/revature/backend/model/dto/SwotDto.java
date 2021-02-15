package com.revature.backend.model.dto;

import java.sql.Timestamp;

public class SwotDto {
	
	private int id;
	
	// Primary key for Associate object.
	private int associateId;
	
	// Primary key for Manager object.
	private int managerId;
	
	// Timestamp for creation of SWOT.
	private Timestamp createdOn;
	
	// Timestamp for most recent modification of SWOT.
	private Timestamp lastModified;
	
	//Description of the SWOT
	private String description;
	
	public SwotDto() {}

	public SwotDto(int id, int associateId, int managerId, Timestamp createdOn, Timestamp lastModified, String description) {
		super();
		this.id = id;
		this.associateId = associateId;
		this.managerId = managerId;
		this.createdOn = createdOn;
		this.lastModified = lastModified;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
		this.associateId = associateId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SwotDto [id=" + id + ", associateId=" + associateId + ", managerId=" + managerId + ", createdOn="
				+ createdOn + ", lastModified=" + lastModified + ", description=" + description + "]";
	}
	
}
