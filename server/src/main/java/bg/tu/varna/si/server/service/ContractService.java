package bg.tu.varna.si.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.ContractList;
import bg.tu.varna.si.server.db.entity.CarStatusEntity;
import bg.tu.varna.si.server.db.entity.ContractEntity;
import bg.tu.varna.si.server.repository.CarRepository;
import bg.tu.varna.si.server.repository.ClientRepository;
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

	public ContractList getAllContracts() {
		List<ContractEntity> contractEntities = contractRepository.findAll();
		ContractList contracts = new ContractList();

		for (ContractEntity entity : contractEntities) {
			contracts.getContracts().add(fromEntity(entity));
		}
		return contracts;
	}

	public Optional<Contract> getContractById(Long id) {

		Optional<ContractEntity> entity = contractRepository.findById(id);

		if (!entity.isPresent()) {
			return Optional.empty();
		}

		ContractEntity contractEntity = entity.get();
		return Optional.of(fromEntity(contractEntity));
	}

	public Contract createContract(Contract contract) {
		Optional<ContractEntity> contractEntity = contractRepository.findById(contract.getId());
		if (contractEntity.isPresent()) {

			throw new IllegalArgumentException("Record with that ID already exists");

		}
		ContractEntity entity = new ContractEntity();
		entity.setCar(carRepository.findByRegNumber(contract.getCar().getRegNumber()));
		entity.setClient(clientRepository.findByDriversLicense(contract.getClient().getDriversLicense()));
		entity.setStart(contract.getStart());
		entity.setEnd(contract.getEnd());
		entity.setOperator(userRepository.findByUsername(contract.getOperator().getUsername()));
		CarStatusEntity statusOnStart = new CarStatusEntity(contract.getStatusOnStart().getStatus(),
				contract.getStatusOnStart().getDescription());
		entity.setStatusOnStart(statusOnStart);

		return fromEntity(contractRepository.save(entity));

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
		CarStatusEntity statusOnStart = new CarStatusEntity(contract.getStatusOnStart().getStatus(),
				contract.getStatusOnStart().getDescription());
		entity.setStatusOnStart(statusOnStart);
		CarStatusEntity statusOnEnd = new CarStatusEntity(contract.getStatusOnEnd().getStatus(),
				contract.getStatusOnEnd().getDescription());
		entity.setStatusOnEnd(statusOnEnd);

		return Optional.of(fromEntity(contractRepository.save(entity)));
	}

	public void deleteContractEntity(Long id) {

		contractRepository.deleteById(id);
	}

}
