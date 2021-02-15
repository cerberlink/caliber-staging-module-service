package com.revature.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Objects;

@Entity
@Table(name = "analysis_items")
public class AnalysisItem {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// IDENTITY was preferred to AUTO.
	private int id;

	@Column(name = "name")
	private String name;

	//@JsonIgnore	// Meant to help when trying to create a SWOT, this is a bad solution.
	@JsonBackReference // Meant to help when trying to create a SWOT. - Prevents recursion in retrieve requests, this is the better solution.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "swot_analysis_id")
	private Swot swot;

	@Column(name = "type")
	private AnalysisType type;
	
	@Column(name = "note")
	private String note;



	public AnalysisItem() {}

	public AnalysisItem(String name, Swot swot, AnalysisType type, String note) {
		super();
		this.name = name;
		this.swot = swot;
		this.type = type;
		this.note = note;
	}

	public AnalysisItem(int id, String name, Swot swot, AnalysisType type, String note) {
		super();
		this.id = id;
		this.name = name;
		this.swot = swot;
		this.type = type;
		this.note = note;
	}

	public Swot getSwot() {
		return swot;
	}

	public void setSwot(Swot swot) {
		this.swot = swot;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public AnalysisType getType() {
		return this.type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(AnalysisType type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AnalysisItem))
			return false;
		AnalysisItem that = (AnalysisItem) o;
		return getId() == that.getId() && Objects.equals(getName(), that.getName())
				&& Objects.equals(swot, that.swot) && getType() == that.getType() && getNote().equals(that.getNote());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), swot, getType(), getNote());
	}

	//This does not print 'swot' because it causes a stack overflow error when either the AnalysisItem or Swot toString are called
	//if printing is required, print the id instead of the whole object or otherwise edit the swot toString to prevent recursive printing
	public String toString() {
		return "Analysis_Item(id=" + this.getId() + ", name=" + this.getName() + ", type=" + this.getType() + ", note=" + this.getNote() + ")";
	}
}
