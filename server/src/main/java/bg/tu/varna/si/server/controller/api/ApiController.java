package bg.tu.varna.si.server.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarList;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.ClientList;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.ContractList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.server.repository.CarRepository;
import bg.tu.varna.si.server.service.CarService;
import bg.tu.varna.si.server.service.ClientsService;
import bg.tu.varna.si.server.service.ContractService;
import bg.tu.varna.si.server.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private CarService carService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ClientsService clientsService;
	
	@Autowired
	private ContractService contractService;

	
	@GetMapping("/{comapnyId}/users/{userId}")
	public ResponseEntity<Optional<User>> UserById(@PathVariable("companyId") long companyId,
			@PathVariable("userId") long userId) {
		return ResponseEntity.ok().body(userService.getUserByID(userId));
	}

	@GetMapping("/{companyId}/cars")
	public ResponseEntity<Optional<CarList>> getAllCars(@PathVariable("companyId") long companyId) {
		return ResponseEntity.ok().body(carService.getAllCars(companyId));
		
	}
	@PostMapping("/{companyId}/cars")
	public ResponseEntity<Optional<Car>> createCar(@PathVariable("companyId") long companyId, 
			@RequestBody Car car){
	return ResponseEntity.ok().body(carService.createCar(companyId, car));
	}
	
	@GetMapping("/{companyId}/cars/{carId}")
	public ResponseEntity<Optional<Car>> getCar(
			@PathVariable("companyId") long companyId, 
			@PathVariable ("carId") long carId){
		return ResponseEntity.ok().body(carService.getByID(companyId, carId));
	}
	@PutMapping("/{companyId}/cars/{carId}")
	public ResponseEntity<Optional<Car>> updateCar(
			@PathVariable ("companyId") long companyId, 
			@PathVariable ("carId") long carId,
			@RequestBody Car car){
		return ResponseEntity.ok().body(carService.updateCar(companyId, carId, car));
	}
	
	@GetMapping ("/{companyId}/clients")
	public ResponseEntity<ClientList> getAllClients(
			@PathVariable ("companyId") long companyId){
		return ResponseEntity.ok().body(clientsService.getAllClients(companyId));
	}
	
	@PostMapping ("/{companyId}/clients")
	public ResponseEntity<Optional<Client>> createClient(
			@PathVariable ("companyId") long companyId,
			@RequestBody Client client){
		return ResponseEntity.ok().body(clientsService.createClient(companyId, client));
	}
	
	@GetMapping("/{companyId}/clients/{clientId}")
	public ResponseEntity<Optional<Client>> getClient (
			@PathVariable ("companyId") long companyId, 
			@PathVariable ("clientId") long clientId) {
		return ResponseEntity.ok().body(clientsService.getByID(companyId, clientId));
	}

	@PutMapping("/{companyId}/clients/{clientId}")
	public ResponseEntity<Optional<Client>> updateClient (
			@PathVariable ("companyId") long companyId, 
			@PathVariable ("clientId") long clientId,
			@RequestBody Client client){
		return ResponseEntity.ok().body(clientsService.updateClient(companyId, clientId, client));
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") long id) {
		return ResponseEntity.ok().body(userService.getUserByID(id));
	}
	
	@GetMapping("/{companyId}/contracts")
	public  ResponseEntity<ContractList> getAllContracts(
			@PathVariable ("companyId") long companyId){
		return ResponseEntity.ok().body(contractService.getAllContracts(companyId));
	} 
	@PostMapping("/{companyId}/contracts")
	public ResponseEntity<Optional<Contract>> createContract(
			@PathVariable ("companyId") long companyId, 
			@RequestBody Contract contract){
		return ResponseEntity.ok().body(contractService.createContract(companyId, contract));
	}
	@GetMapping("/{companyId}/queries/availableCars")
	public ResponseEntity<CarList> getAvailableCars (
			@PathVariable ("companyId") long companyId) {
		return ResponseEntity.ok().body(carService.getAllAvailable(companyId, true));
	}
	@GetMapping("/{companyId}/contracts/{contractId}")
	public ResponseEntity<Optional<Contract>> getContract(@PathVariable ("companyId") long companyId,
			@PathVariable ("contractId") long contractId){
		return ResponseEntity.ok().body(contractService.getContractById(contractId));
		
	}
	@PutMapping("/{companyId}/contracts/{contractId}")
	public ResponseEntity<Optional<Contract>> updateContract(@PathVariable ("companyId") long companyId,
			@PathVariable ("contractId") long contractId,
			@RequestBody Contract contract){
		return ResponseEntity.ok().body(contractService.updateContract(contract));
	}
}

