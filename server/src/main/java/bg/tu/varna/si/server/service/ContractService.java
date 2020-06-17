package bg.tu.varna.si.server.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.ContractList;
import bg.tu.varna.si.server.db.entity.CarEntity;
import bg.tu.varna.si.server.db.entity.CarStatusEntity;
import bg.tu.varna.si.server.db.entity.CompanyEntity;
import bg.tu.varna.si.server.db.entity.ContractEntity;
import bg.tu.varna.si.server.repository.CarRepository;
import bg.tu.varna.si.server.repository.ClientRepository;
import bg.tu.varna.si.server.repository.CompanyRepository;
import bg.tu.varna.si.server.repository.ContractRepository;
import bg.tu.varna.si.server.repository.UserRepository;

@Service
public class ContractService extends BaseService {

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;
	


	public ContractList getAllContracts(long companyId) {

		List<ContractEntity> contractEntities = contractRepository.findByCompanyId(companyId);


			ContractList contractsList = new ContractList();

			List<Contract> contracts = new LinkedList<Contract>();
			for (ContractEntity entity : contractEntities) {
				contracts.add(fromEntity(entity));
			}

			contractsList.setContracts(contracts);

			return contractsList;
		
	}

	public Optional<Contract> getContractById(Long id) {

		Optional<ContractEntity> entity = contractRepository.findById(id);

		if (!entity.isPresent()) {
			return Optional.empty();
		}

		ContractEntity contractEntity = entity.get();
		return Optional.of(fromEntity(contractEntity));
	}

	public Optional<Contract> createContract(long companyId, Contract contract) {

		Optional<CompanyEntity> companyEntity = companyRepository.findById(companyId);
		Optional<ContractEntity> contractEntity = contractRepository.findById(contract.getId());
		if (contractEntity.isPresent()) {

			throw new IllegalArgumentException("Record with that ID already exists");

		}
		if (!companyEntity.isPresent()) {
			return Optional.empty();
		} else {
			ContractEntity entity = new ContractEntity();
			CarEntity contractedCar = carRepository.findByRegNumber(contract.getCar().getRegNumber());
			entity.setCar(contractedCar);
			contractedCar.setAvailable(false);
			
			entity.setClient(clientRepository.findByDriversLicense(contract.getClient().getDriversLicense()));
			entity.setStart(contract.getStart());
			entity.setEnd(contract.getEnd());
			entity.setOperator(userRepository.findByUsername(contract.getOperator().getUsername()));
			CarStatusEntity statusOnStart = new CarStatusEntity();
			statusOnStart.setStatus(contract.getStatusOnStart().getStatus());
			statusOnStart.setDescription(contract.getStatusOnStart().getDescription());
			entity.setStatusOnStart(statusOnStart);
			entity.setCompanyId(contract.getCompanyId());
			entity.setActive(contract.isActive());
			entity.setPrice(contract.getPrice());

			
			
			return Optional.of(fromEntity(contractRepository.save(entity)));
		}

	}

	public Optional<Contract> updateContract(Contract contract) {
		Optional<ContractEntity> contractEntity = contractRepository.findById(contract.getId());

		if (!contractEntity.isPresent()) {
			return Optional.empty();
		}

		ContractEntity entity = contractEntity.get();
		entity.setCar(carRepository.findByRegNumber(contract.getCar().getRegNumber()));
		entity.setClient(clientRepository.findByDriversLicense(contract.getClient().getDriversLicense()));
		entity.setStart(contract.getStart());
		entity.setEnd(contract.getEnd());
		entity.setOperator(userRepository.findByUsername(contract.getOperator().getUsername()));

		CarStatusEntity statusOnStart = new CarStatusEntity();
		statusOnStart.setStatus(contract.getStatusOnStart().getStatus());
		statusOnStart.setDescription(contract.getStatusOnStart().getDescription());
		entity.setStatusOnStart(statusOnStart);

		CarStatusEntity statusOnEnd = new CarStatusEntity();
		statusOnEnd.setStatus(contract.getStatusOnEnd().getStatus());
		statusOnEnd.setDescription(contract.getStatusOnEnd().getDescription());
		entity.setStatusOnEnd(statusOnEnd);
		entity.setActive(contract.isActive());

		return Optional.of(fromEntity(contractRepository.save(entity)));
	}
	

	public void deleteContractEntity(Long id) {

		contractRepository.deleteById(id);
	}

}
