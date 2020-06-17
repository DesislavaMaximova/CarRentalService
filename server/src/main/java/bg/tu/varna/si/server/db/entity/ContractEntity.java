package bg.tu.varna.si.server.db.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	private long companyId;

	private Date start;
	
	private Date end;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "status_on_start_id", referencedColumnName = "id")
	private CarStatusEntity statusOnStart;
	
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "status_on_end_id", referencedColumnName = "id")
	private CarStatusEntity statusOnEnd;
    
    
    private boolean active;
	
	private double price;

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	

}
