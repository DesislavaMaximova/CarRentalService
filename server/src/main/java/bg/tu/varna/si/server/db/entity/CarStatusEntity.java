package bg.tu.varna.si.server.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import bg.tu.varna.si.model.Status;

@Entity
@Table(name = "Car_Status")
public class CarStatusEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Status status;
	
	private String description;

	public CarStatusEntity(Status status, String description) {
		super();
		this.status = status;
		this.description = description;
	}

	public long getId() {
		return id;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
