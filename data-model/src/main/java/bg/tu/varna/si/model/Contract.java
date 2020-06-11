package bg.tu.varna.si.model;

import java.util.Date;

public class Contract {
	
	private long id;

	private User operator;

	private Client client;

	private Car car;

	private Date start;

	private CarStatus statusOnStart;

	private Date end;

	private CarStatus statusOnEnd;

	private long companyId;
	
	private double price;

	
	public Contract() {
		
	}


	public long getId() {
		return id;
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


	public long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public void setId(long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Contract [id=");
		builder.append(id);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", client=");
		builder.append(client);
		builder.append(", car=");
		builder.append(car);
		builder.append(", start=");
		builder.append(start);
		builder.append(", statusOnStart=");
		builder.append(statusOnStart);
		builder.append(", end=");
		builder.append(end);
		builder.append(", statusOnEnd=");
		builder.append(statusOnEnd);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}


	

	

}
