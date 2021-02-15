package com.revature.backend.model.dto;

public class AnalysisItemDto {
	
	// Primary key.
	private int id;
	
	// Name of the item.
	private String name;
	
	// The primary key of the Swot object.
	private int swotId;
	
	// The AnalysisType, converted to a String for easier access.
	private String type;
	
	// The Analysis note
	private String note;

	public AnalysisItemDto() {}

	public AnalysisItemDto(int id, String content, int swotId, String type, String note) {
		super();
		this.id = id;
		this.name = content;
		this.swotId = swotId;
		this.type = type;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return name;
	}

	public void setContent(String content) {
		this.name = content;
	}

	public int getSwotId() {
		return swotId;
	}

	public void setSwotId(int swotId) {
		this.swotId = swotId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "AnalysisItemDto [id=" + id + ", name=" + name + ", swotId=" + swotId + ", type=" + type + ", note=" + note + "]";
	}
	
}
