package bg.tu.varna.si.model;

public class CarStatus {
	
	private Status status;
	
	private String description;
	
	public CarStatus() {
		
	}
	
	public CarStatus(Status status, String description) {

		this.status = status;
		this.description = description;
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
