package bg.tu.varna.si.server.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu.varna.si.model.UserRequest;
import bg.tu.varna.si.server.db.entity.UserEntity;
import bg.tu.varna.si.server.repository.UserRepository;
import javassist.NotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	private User fromEntity(UserEntity userEntity) {
		
		User user = new User(userEntity.getRole());
		user.setId(userEntity.getId());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setUsername(userEntity.getUsername());
		user.setPassword(userEntity.getPassword());
		user.setEmail(userEntity.getEmail());
		
		return user;
	}

	public UserList getAllUsers() {

		List<UserEntity> userEntities = userRepository.findAll();
		UserList users = new UserList();
		for (UserEntity userEntity : userEntities) {
			users.getUsers().add(fromEntity(userEntity));
		}
		return users;
	}

	public Optional<User> getUserByID(Long id) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (!userEntity.isPresent()) {
			return Optional.empty();
		}
		UserEntity entity = userEntity.get();
		User user = fromEntity(entity);
		return Optional.of(user);
	}

	public User createUser(UserRequest userReg) {
		UserEntity entity = userRepository.findByUsername(userReg.getAuth().getUsername());
		if (entity != null) {
			throw new AuthenticationServiceException ("The username is already taken!");
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userReg.getAuth().getUsername());
		userEntity.setPassword(userReg.getAuth().getPassword());
		userEntity.setFirstName(userReg.getUser().getFirstName());
		userEntity.setLastName(userReg.getUser().getLastName());
		userEntity.setEmail(userReg.getUser().getEmail());
		userEntity.setRole(userReg.getUser().getRole());

		userRepository.save(userEntity);
		User user = fromEntity(userEntity);

		return user;

	}

	public User updateUser(UserRequest userRequest) throws NotFoundException {
		UserEntity entity = userRepository.findByUsername(userRequest.getAuth().getUsername());
		if (!Objects.isNull(entity.getId())) {
			throw new NotFoundException("User with this username not found!");
		}

		entity.setUsername(userRequest.getAuth().getUsername());
		entity.setPassword(userRequest.getAuth().getPassword());
		entity.setFirstName(userRequest.getUser().getFirstName());
		entity.setLastName(userRequest.getUser().getLastName());
		entity.setEmail(userRequest.getUser().getEmail());
		entity.setRole(userRequest.getUser().getRole());

		userRepository.save(entity);
		User user = fromEntity(entity);

		return user;
	}

	public void deleteUserEntity(Long id) {

		userRepository.deleteById(id);

	}
}
