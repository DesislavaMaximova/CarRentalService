package bg.tu.varna.si.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.tu.varna.si.server.db.entity.CarEntity;

public interface CarRepository extends JpaRepository <CarEntity, Long> {

	CarEntity findByRegNumber (String reqNumber);

}
