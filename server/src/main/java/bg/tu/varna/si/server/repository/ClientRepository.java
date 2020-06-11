package bg.tu.varna.si.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.tu.varna.si.server.db.entity.ClientEntity;

public interface ClientRepository  extends JpaRepository <ClientEntity, Long> {
	
	ClientEntity findByDriversLicense(String driversLicense);
	
}
