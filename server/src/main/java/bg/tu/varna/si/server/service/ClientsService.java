package bg.tu.varna.si.server.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.ClientList;
import bg.tu.varna.si.server.db.entity.ClientEntity;
import bg.tu.varna.si.server.db.entity.CompanyEntity;
import bg.tu.varna.si.server.repository.CompanyRepository;

public class ClientsService extends BaseService{
	@Autowired
	private CompanyRepository companyRepository;

	
	public Optional<ClientList> getAllClients(long id) {
	Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

	if (companyEntity.isPresent()) {
		ClientList clientList = new ClientList();

		List<ClientEntity> clientEntities = companyEntity.get().getClients();
		List<Client> clients = new LinkedList<Client>();
		for (ClientEntity entity : clientEntities) {
			clients.add((Client) fromEntity(entity));
		}

		clientList.setClients(clients);

		return Optional.of(clientList);
	}

	return Optional.empty();
}


}
