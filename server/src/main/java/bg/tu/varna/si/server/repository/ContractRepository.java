package bg.tu.varna.si.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.tu.varna.si.server.db.entity.ContractEntity;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long>{
	
	List<ContractEntity> findByCompanyId(long companyId);

}
