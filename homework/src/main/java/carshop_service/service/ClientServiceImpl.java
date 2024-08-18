package carshop_service.service;

import carshop_service.dao.ClientDao;
<<<<<<< Updated upstream
import carshop_service.dao.ClientDaoImpl;
import carshop_service.entity.Client;
=======
import carshop_service.dto.ClientDto;
import carshop_service.entity.Client;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.mapper.ClientMapper;
import lombok.AllArgsConstructor;
>>>>>>> Stashed changes

import java.util.List;

public class ClientServiceImpl implements ClientService {
<<<<<<< Updated upstream
    private ClientDao dao;
    public ClientServiceImpl(){
        this.dao = new ClientDaoImpl();
    }

    @Override
    public boolean isExist(Client client) {
        return dao.isExist(client);
    }

    @Override
    public void addClient(Client client) {
        this.dao.addClient(client);
    }

    @Override
    public Client getClient(int id) {
        return this.dao.getClient(id);
    }

    @Override
    public List<Client> getAllClients() {
        return this.dao.getAllClients();
    }

    @Override
    public void updateClient(Client client) {
        this.dao.updateClient(client);
=======

    private final ClientMapper mapper = ClientMapper.INSTANCE;
    private final ClientDao clientDao;

    @Override
    public boolean isExist(Client client) throws NoSuchEntityException {
        if (!clientDao.isExist(client)) throw new NoSuchEntityException("Client");
        return clientDao.isExist(client);
    }

    @Override
    public void addClient(Client client) throws DuplicateEntityException {
        if (clientDao.getClient(client.getId()) != null) throw new DuplicateEntityException("Client");
        this.clientDao.addClient(client);
    }

    @Override
    public ClientDto getClient(int id) throws NoSuchEntityException {
        Client client = clientDao.getClient(id);
        if (client == null) throw new NoSuchEntityException("Client");
        return mapper.clientToClientDto(client);
    }

    @Override
    public List<ClientDto> getAllClients() throws DataBaseEmptyException {
        List<Client> clients = clientDao.getAllClients();
        if (clients.isEmpty()) throw new DataBaseEmptyException("Client");
        return mapper.listOfClientsToClientsDto(clients);
    }

    @Override
    public void updateClient(Client client) throws NoSuchEntityException {
        if (clientDao.getClient(client.getId()) == null) throw new NoSuchEntityException("Client");
        this.clientDao.updateClient(client);
>>>>>>> Stashed changes
    }
}
