package bg.tu.varna.si.server.controller.admin;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.varna.si.model.Company;
import bg.tu.varna.si.model.CompanyList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu.varna.si.model.UserRequest;
import bg.tu.varna.si.server.service.CompanyService;
import bg.tu.varna.si.server.service.UserService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@GetMapping("/companies")
	public ResponseEntity<CompanyList> getAllCompanies() {
		return ResponseEntity.ok().body(companyService.getAllCompanies());
	}

	@GetMapping("/companies/{id}")
	public ResponseEntity<Optional<Company>> getCompanyById(@PathVariable("id") long id) {
		return ResponseEntity.ok().body(companyService.getByID(id));
	}

	@PostMapping("/companies")
	public ResponseEntity<Company> createCompany(@RequestBody Company company) throws Exception {
		return ResponseEntity.ok().body(companyService.createCompany(company));
	}

	@PutMapping("/companies/{id}")
	public ResponseEntity<Company> updateCompany(@PathVariable("id") long id, @RequestBody Company company)
			throws NotFoundException {
		return ResponseEntity.ok().body(companyService.updateCompany(company));
	}

	@DeleteMapping("/companies/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) {
		return (ResponseEntity<Void>) ResponseEntity.noContent();
	}

	@GetMapping("/companies/{id}/users")
	public ResponseEntity<Optional<UserList>> getAllEmployees(@PathVariable("id") long id) {
		Optional<UserList> allEmployees = companyService.getAllEmployees(id);
		
		LOGGER.info("Company {} Employees size: {}", id, allEmployees.get().getUsers().size());
		
		return ResponseEntity.ok().body(allEmployees);
	}
	

	@PostMapping("/companies/{id}/users")
	public ResponseEntity<Optional<User>> createEmployee(@PathVariable("id") long id, @RequestBody User user) {
		
		LOGGER.info("Creating user for company {} \n {}", id, user);
		
		return ResponseEntity.ok().body(companyService.createEmployee(id, user));
	}

	@PutMapping("/companies/{idCompany}/users/{idEmployee}")
	public ResponseEntity<Optional<User>> updateEmployee(@PathVariable ("idCompany") long idCompany, @PathVariable ("idEmployee")long idEmployee,
			@RequestBody User user) {
		return ResponseEntity.ok().body(companyService.updateEmployee(idCompany, idEmployee, user));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") long id) {
		return ResponseEntity.ok().body(userService.getUserByID(id));
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserRequest user)
			throws NotFoundException {

		return ResponseEntity.ok().body(userService.updateUser(user));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
		
		userService.deleteUserEntity(id);
		
		return ResponseEntity.ok().build();
	}
	
}
