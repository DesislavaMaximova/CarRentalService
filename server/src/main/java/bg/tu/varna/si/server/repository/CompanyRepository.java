package bg.tu.varna.si.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.tu.varna.si.server.db.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

	CompanyEntity findByName(String name);


}
