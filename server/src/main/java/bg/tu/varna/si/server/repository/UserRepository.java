package bg.tu.varna.si.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.tu.varna.si.server.db.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);


}
