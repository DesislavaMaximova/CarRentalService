package bg.tu.varna.si.server.service;

import java.io.Console;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.ClientList;
import bg.tu.varna.si.server.db.entity.CarEntity;
import bg.tu.varna.si.server.db.entity.ClientEntity;
import bg.tu.varna.si.server.db.entity.CompanyEntity;
import bg.tu.varna.si.server.repository.ClientRepository;
import bg.tu.varna.si.server.repository.CompanyRepository;

@Service
public class ClientsService extends BaseService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClientsService.class);
	
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ClientRepository clientRepository;

	public ClientList getAllClients(long id) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
		List<ClientEntity> clientEntities = clientRepository.findAll();
		ClientList clients = new ClientList();
		if (companyEntity.isPresent() && clientEntities != null) {
			for (ClientEntity clientEntity : clientEntities) {
				clients.getClients().add(fromEntity(clientEntity));
			}
		}
		return clients;
	}
	
	public Optional<Client> createClient(long id, Client client) {

		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

		Optional<ClientEntity> entity = clientRepository.findById(client.getId());
		
		if (companyEntity.isPresent() && !entity.isPresent()) {
			ClientEntity clientEntity = new ClientEntity();
			
			clientEntity.setDriversLicense(client.getDriversLicense());
			clientEntity.setEmail(client.getEmail());
			clientEntity.setFirstName(client.getFirstName());
			clientEntity.setLastName(client.getLastName());
			clientEntity.setRating(client.getRating());
			clientEntity.setTelephone(client.getTelephone());
		
		
			clientRepository.save(clientEntity);

			CompanyEntity company = companyEntity.get();
			List<ClientEntity> clients = company.getClients();
			if (clients == null) {
				clients = new LinkedList<ClientEntity>();
			}

			clients.add(clientEntity);
			companyRepository.save(company);

			Client returnClient = fromEntity(clientEntity);
			
			LOGGER.info("Creating client: {}", returnClient);
			
			return Optional.of(returnClient);
		}

		return Optional.empty();
	}
	
	public Optional<Client> getByID(long companyId, long clientId) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(companyId);
		Optional<ClientEntity> entity = clientRepository.findById(clientId);

		if (!entity.isPresent() || !companyEntity.isPresent()) {
			return Optional.empty();
		}
		ClientEntity clientEntity = entity.get();
		return Optional.of(fromEntity(clientEntity));
	}
	
	public Optional<Client> updateClient(long id, long clientId, Client client) {

		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

		Optional<ClientEntity> entity = clientRepository.findById(clientId);
		
		if (companyEntity.isPresent() && entity.isPresent()) {
			ClientEntity updatedClientEntity = entity.get();
			
			updatedClientEntity.setFirstName(client.getFirstName());
			updatedClientEntity.setLastName(client.getLastName());
			updatedClientEntity.setDriversLicense(client.getDriversLicense());
			updatedClientEntity.setEmail(client.getEmail());
			updatedClientEntity.setTelephone(client.getTelephone());
			updatedClientEntity.setRating(client.getRating());
		
			clientRepository.save(updatedClientEntity);


			Client updatedClient = fromEntity(updatedClientEntity);
			
			LOGGER.info("Updated client: {}", updatedClient);
			
			return Optional.of(updatedClient);
		}

		return Optional.empty();
	}
	
}
