package com.revature.backend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "associate")
public class Associate {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "salesforce_id")
	private String salesforceId;

	@Column(name = "email")
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne(fetch = FetchType.EAGER) // removed CascadeType.ALL, this will cause problems with deletion.
	@JoinColumn(name = "manager_id")
	private Manager manager;

	@ManyToOne(fetch = FetchType.EAGER) // removed CascadeType.ALL, this will cause problems with deletion.
	@JoinColumn(name = "batch_id")
	private Batch batch;

	@Column(name = "status")
	private AssociateStatus status;

	public Associate() {}

	public Associate(int id, String salesforceId, String email, String firstName, String lastName, Manager manager,
			Batch batch, AssociateStatus status) {
		super();
		this.id = id;
		this.salesforceId = salesforceId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.manager = manager;
		this.batch = batch;
		this.status = status;
	}

	public Associate(String salesforceId, String email, String firstName, String lastName, Manager manager, Batch batch,
			AssociateStatus status) {
		super();
		this.salesforceId = salesforceId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.manager = manager;
		this.batch = batch;
		this.status = status;
	}

	public int getId() {
		return this.id;
	}

	public String getSalesforceId() {
		return this.salesforceId;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Manager getManager() {
		return this.manager;
	}

	public Batch getBatch() {
		return this.batch;
	}

	public AssociateStatus getStatus() {
		return this.status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSalesforceId(String salesforce_id) {
		this.salesforceId = salesforce_id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public void setLastName(String last_name) {
		this.lastName = last_name;
	}

	public void setManager(Manager manager_id) {
		this.manager = manager_id;
	}

	public void setBatch(Batch batch_id) {
		this.batch = batch_id;
	}

	public void setStatus(AssociateStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Associate))
			return false;
		Associate associate = (Associate) o;
		return getId() == associate.getId() && Objects.equals(getSalesforceId(), associate.getSalesforceId())
				&& Objects.equals(getEmail(), associate.getEmail())
				&& Objects.equals(getFirstName(), associate.getFirstName())
				&& Objects.equals(getLastName(), associate.getLastName())
				&& Objects.equals(getManager(), associate.getManager())
				&& Objects.equals(getBatch(), associate.getBatch()) && getStatus() == associate.getStatus();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getSalesforceId(), getEmail(), getFirstName(), getLastName(), getManager(),
				getBatch(), getStatus());
	}

	public String toString() {
		return "Associate(id=" + this.getId() + ", salesforce_id=" + this.getSalesforceId() + ", email="
				+ this.getEmail() + ", first_name=" + this.getFirstName() + ", last_name=" + this.getLastName()
				+ ", manager_id=" + this.getManager() + ", batch_id=" + this.getBatch() + ", status=" + this.getStatus()
				+ ")";
	}
}
