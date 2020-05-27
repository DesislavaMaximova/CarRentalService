package bg.tu.varna.si.server.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/{comapnyId}/users/{id}")
	public ResponseEntity<Optional<User>> UserById(@PathVariable("id") long id) {
		return ResponseEntity.ok().body(userService.getUserByID(id));
	}


}
