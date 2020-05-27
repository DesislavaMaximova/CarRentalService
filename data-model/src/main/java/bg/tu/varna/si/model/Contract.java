package bg.tu.varna.si.model;

import java.sql.Date;

public class Contract {
	private long id;

	private User operator;

	private Client client;

	private Car car;

	private Date start;

	private CarStatus statusOnStart;
	private Date end;

	private CarStatus statusOnEnd;

	public Contract( User operator, Client client, Car car, Date start, CarStatus statusOnStart, Date end,
			CarStatus statusOnEnd) {
		
		this.operator = operator;
		this.client = client;
		this.car = car;
		this.start = start;
		this.statusOnStart = statusOnStart;
		this.end = end;
		this.statusOnEnd = statusOnEnd;
	}
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public CarStatus getStatusOnStart() {
		return statusOnStart;
	}

	public void setStatusOnStart(CarStatus statusOnStart) {
		this.statusOnStart = statusOnStart;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public CarStatus getStatusOnEnd() {
		return statusOnEnd;
	}

	public void setStatusOnEnd(CarStatus statusOnEnd) {
		this.statusOnEnd = statusOnEnd;
	}

}
