package bg.tu.varna.si.server.db.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Company")
public class CompanyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<UserEntity> employees;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<CarEntity> cars;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<ClientEntity> clients;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<ContractEntity> contracts;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserEntity> getEmployees() {
		return employees;
	}

	public void setEmployees(List<UserEntity> employees) {
		this.employees = employees;
	}

	public List<CarEntity> getCars() {
		return cars;
	}

	public void setCars(List<CarEntity> cars) {
		this.cars = cars;
	}

	public List<ClientEntity> getClients() {
		return clients;
	}

	public void setClients(List<ClientEntity> clients) {
		this.clients = clients;
	}

	public List<ContractEntity> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractEntity> contracts) {
		this.contracts = contracts;
	}

}
