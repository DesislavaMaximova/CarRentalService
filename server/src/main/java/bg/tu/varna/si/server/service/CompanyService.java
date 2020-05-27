package bg.tu.varna.si.server.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Company;
import bg.tu.varna.si.model.CompanyList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu.varna.si.server.db.entity.CarEntity;
import bg.tu.varna.si.server.db.entity.CompanyEntity;
import bg.tu.varna.si.server.db.entity.UserEntity;
import bg.tu.varna.si.server.repository.CarRepository;
import bg.tu.varna.si.server.repository.CompanyRepository;
import bg.tu.varna.si.server.repository.UserRepository;
import javassist.NotFoundException;

@Service
public class CompanyService extends BaseService {
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CarRepository carRepository;

	public CompanyList getAllCompanies() {

		List<CompanyEntity> companiesEntity = companyRepository.findAll();

		CompanyList companies = new CompanyList();
		for (CompanyEntity entity : companiesEntity) {
			companies.getListCompany().add(fromEntity(entity));
		}

		return companies;
	}

	public Optional<Company> getByID(Long id) {

		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
		if (!companyEntity.isPresent()) {
			return Optional.empty();
		} else {
			CompanyEntity entity = companyEntity.get();
			Company company = fromEntity(entity);
			return Optional.of(company);
		}
	}

	public Company createCompany(Company company) {
		CompanyEntity companyEntity = companyRepository.findByName(company.getName());
		if (companyEntity != null) {
			throw new IllegalArgumentException("Company with this name already exists!");
		}
		companyEntity = new CompanyEntity();
		companyEntity.setName(company.getName());

		return fromEntity(companyRepository.save(companyEntity));
	}

	public Company updateCompany(Company company) throws NotFoundException {
		CompanyEntity companyEntity = companyRepository.findByName(company.getName());
		if (!Objects.isNull(companyEntity.getId())) {
			throw new NotFoundException("Car with this registration number not found!");
		}

		companyEntity.setName(company.getName());
		List<UserEntity> employees = new LinkedList<UserEntity>();
		for (User e : company.getEmployees()) {
			employees.add(userRepository.findByUsername(e.getUsername()));

		}
		companyEntity.setEmployees(employees);
		List<CarEntity> cars = new LinkedList<CarEntity>();
		for (Car car : company.getCars()) {
			cars.add(carRepository.findByRegNumber(car.getRegNumber()));
		}
		companyEntity.setCars(cars);

		return fromEntity(companyRepository.save(companyEntity));
	}

	public void deleteCompanyEntity(Long id) {

		companyRepository.deleteById(id);
	}

	public Optional<UserList> getAllEmployees(long id) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

		if (companyEntity.isPresent()) {
			UserList userList = new UserList();

			List<UserEntity> userEntities = companyEntity.get().getEmployees();
			List<User> users = new LinkedList<User>();
			for (UserEntity entity : userEntities) {
				users.add(fromEntity(entity));
			}

			userList.setUsers(users);

			return Optional.of(userList);
		}

		return Optional.empty();

	}

	public Optional<User> createEmployee(long id, User user) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
		UserEntity entity = userRepository.findByUsername(user.getUsername());
		if (companyEntity.isPresent() && entity == null) {
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername(user.getUsername());
			userEntity.setPassword(user.getPassword());
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setEmail(user.getEmail());
			userEntity.setRole(user.getRole());
			userRepository.save(userEntity);

			CompanyEntity company = companyEntity.get();
			List<UserEntity> employees = company.getEmployees();
			if (employees == null) {
				employees = new LinkedList<UserEntity>();
			}

			employees.add(userEntity);
			companyRepository.save(company);

			User returnUser = fromEntity(userEntity);
			return Optional.of(returnUser);
		}

		return Optional.empty();
	}

	public Optional<User> updateEmployee(long idCompany, long idEmpl, User user) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(idCompany);
		Optional<UserEntity> userEntity = userRepository.findById(idEmpl);

		if (companyEntity.isPresent() && userEntity.isPresent()) {
			
			
			userEntity.get().setPassword(user.getPassword());;
			userEntity.get().setFirstName(user.getFirstName());
			userEntity.get().setLastName(user.getLastName());
			userEntity.get().setEmail (user.getEmail());

			userRepository.save(userEntity.get());

			User userUpdate = fromEntity(userEntity.get());
			return Optional.of(userUpdate);
		}
		return Optional.empty();
	}
}
