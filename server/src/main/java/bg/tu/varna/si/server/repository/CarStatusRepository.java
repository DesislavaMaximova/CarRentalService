package bg.tu.varna.si.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.tu.varna.si.server.db.entity.CarStatusEntity;

public interface CarStatusRepository extends JpaRepository<CarStatusEntity, Long> {


}
