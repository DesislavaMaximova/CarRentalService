package bg.tu.varna.si.server.service;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarStatus;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.Company;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.server.db.entity.CarEntity;
import bg.tu.varna.si.server.db.entity.CarStatusEntity;
import bg.tu.varna.si.server.db.entity.ClientEntity;
import bg.tu.varna.si.server.db.entity.CompanyEntity;
import bg.tu.varna.si.server.db.entity.ContractEntity;
import bg.tu.varna.si.server.db.entity.UserEntity;

public abstract class BaseService {

	protected Company fromEntity(CompanyEntity entity) {

		Company company = new Company();
		company.setId(entity.getId());
		company.setName(entity.getName());

		List<UserEntity> employeeEntities = entity.getEmployees();
		if (employeeEntities != null) {
			List<User> employees = new LinkedList<>();

			for (UserEntity userEntity : employeeEntities) {
				employees.add(fromEntity(userEntity));
			}
			company.setEmployees(employees);
		}

		List<CarEntity> carEntities = entity.getCars();
		if (carEntities != null) {
			List<Car> cars = new LinkedList<>();

			for (CarEntity carEntity : carEntities) {
				cars.add(fromEntity(carEntity));
			}
			company.setCars(cars);
		}

		return company;
	}

	protected Car fromEntity(CarEntity entity) {
		Car car = new Car();
		car.setCarId(entity.getId());
		car.setBrand(entity.getBrand());
		car.setCategory(entity.getCategory());
		car.setForSmokers(entity.getForSmokers());
		car.setImage(entity.getImage());
		car.setKilometrage(entity.getKilometrage());
		car.setPriceForDay(entity.getPriceForDay());
		car.setRegNumber(entity.getRegNumber());
		car.setType(entity.getType());
		car.setAvailable(entity.isAvailable());

		return car;
	}

	protected Contract fromEntity(ContractEntity entity) {
		Contract contract = new Contract();
		contract.setCar(fromEntity(entity.getCar()));
		contract.setClient(fromEntity(entity.getClient()));
		contract.setOperator(fromEntity(entity.getOperator()));
		contract.setStart(entity.getStart());
		contract.setEnd(entity.getEnd());
		contract.setStatusOnStart(fromEntity(entity.getStatusOnStart()));
		contract.setStatusOnEnd(fromEntity(entity.getStatusOnEnd()));
		contract.setId(entity.getId());
		contract.setPrice(entity.getPrice());

		return contract;
	}

	protected User fromEntity(UserEntity entity) {
		User user = new User(entity.getRole());
		user.setId(entity.getId());
		user.setUsername(entity.getUsername());
		String firstName = entity.getFirstName();
		if (firstName != null) {
			user.setFirstName(firstName);
		}
		String lastName = entity.getLastName();
		if (lastName != null) {
			user.setLastName(lastName);
		}
		String email = entity.getEmail();
		if (email != null) {
			user.setEmail(email);
		}

		return user;
	}

	protected CarStatus fromEntity(CarStatusEntity entity) {
		if (entity == null) {
			return new CarStatus();
		}
		return new CarStatus(entity.getStatus(), entity.getDescription());
	}

	protected Client fromEntity(ClientEntity entity) {
		
		Client client = new Client();
		client.setId(entity.getId());
		client.setFirstName(entity.getFirstName());
		client.setLastName(entity.getLastName());
		client.setDriversLicense(entity.getDriversLicense());
		client.setEmail(entity.getEmail());
		client.setTelephone(entity.getTelephone());
		client.setRating(entity.getRating());
		
		return client;
	}

}
