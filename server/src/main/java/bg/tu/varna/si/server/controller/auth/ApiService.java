package bg.tu.varna.si.server.controller.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.server.service.CarService;
import bg.tu.varna.si.server.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiService {

	@Autowired
	private CarService carService;

	@Autowired
	private UserService userService;

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
	
}

