package bg.tu.varna.si.server.db.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTRACT")
public class ContractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	private UserEntity operator;

	@OneToOne(fetch = FetchType.LAZY)
	private ClientEntity client;

	@OneToOne(fetch = FetchType.LAZY)
	private CarEntity car;

	private Date start;

	@OneToOne(fetch = FetchType.LAZY)
	private CarStatusEntity statusOnStart;

	private Date end;
	
	@OneToOne(fetch = FetchType.LAZY)
	private CarStatusEntity statusOnEnd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getOperator() {
		return operator;
	}

	public void setOperator(UserEntity operator) {
		this.operator = operator;
	}

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public CarEntity getCar() {
		return car;
	}

	public void setCar(CarEntity car) {
		this.car = car;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public CarStatusEntity getStatusOnStart() {
		return statusOnStart;
	}

	public void setStatusOnStart(CarStatusEntity statusOnStart) {
		this.statusOnStart = statusOnStart;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public CarStatusEntity getStatusOnEnd() {
		return statusOnEnd;
	}

	public void setStatusOnEnd(CarStatusEntity statusOnEnd) {
		this.statusOnEnd = statusOnEnd;
	}

	

}
