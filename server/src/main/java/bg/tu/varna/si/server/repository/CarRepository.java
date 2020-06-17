package bg.tu.varna.si.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.tu.varna.si.server.db.entity.CarEntity;

public interface CarRepository extends JpaRepository <CarEntity, Long> {

	CarEntity findByRegNumber (String reqNumber);
	
	List <CarEntity> findByCompanyIdAndAvailable (long companyId, Boolean available);

}
