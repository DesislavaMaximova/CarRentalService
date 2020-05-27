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
		car.setBrand(entity.getBrand());
		car.setCategory(entity.getCategory());
		car.setForSmokers(entity.getForSmokers());
		car.setImage(entity.getImage());
		car.setKilometrage(entity.getKilometrage());
		car.setPriceForDay(entity.getPriceForDay());
		car.setRegNumber(entity.getRegNumber());
		car.setType(entity.getType());
		
		return car;
	}

	protected Contract fromEntity(ContractEntity contractEntity) {

		return new Contract(fromEntity(contractEntity.getOperator()), fromEntity(contractEntity.getClient()),
				fromEntity(contractEntity.getCar()), contractEntity.getStart(),
				fromEntity(contractEntity.getStatusOnStart()), contractEntity.getEnd(),
				fromEntity(contractEntity.getStatusOnEnd()));

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
		if(lastName != null) {
			user.setLastName(lastName);
		}
		String email = entity.getEmail();
		if(email != null) {
			user.setEmail(email);
		}
		
		return user;
	}

	protected CarStatus fromEntity(CarStatusEntity entity) {
		return new CarStatus(entity.getStatus(), entity.getDescription());
	}

	private Client fromEntity(ClientEntity entity) {
		return new Client(entity.getUsername(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getRole(),
				entity.getRating(), entity.getTelephone(), entity.getDriversLicense());
	}


}
