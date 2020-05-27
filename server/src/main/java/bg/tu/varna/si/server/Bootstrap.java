package bg.tu.varna.si.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bg.tu.varna.si.model.Role;
import bg.tu.varna.si.server.db.entity.UserEntity;
import bg.tu.varna.si.server.repository.UserRepository;

@SpringBootApplication
public class Bootstrap implements CommandLineRunner {
	
	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}
	
	@Override
    public void run(String... args) {
		
//		UserEntity entity = new UserEntity();
//		entity.setEmail("admin@admin.com");
//		entity.setFirstName("Admin");
//		entity.setLastName("Adminov");
//		entity.setPassword("admin");
//		entity.setUsername("admin");
//		entity.setRole(Role.ADMINISTRATOR);
//		
//		repository.save(entity);
//		
//		UserEntity operator = new UserEntity();
//		operator.setEmail("operator@operator.com");
//		operator.setFirstName("Operator");
//		operator.setLastName("Operatorov");
//		operator.setPassword("op");
//		operator.setUsername("op");
//		operator.setRole(Role.OPERATOR);
//		
//		repository.save(operator);
		
	}

}
