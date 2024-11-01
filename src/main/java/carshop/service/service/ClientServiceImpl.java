package carshop.service.service;

import carshop.service.dao.ClientDao;
import carshop.service.dto.ClientDto;
import carshop.service.entity.Client;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис, обрабатывающий ошибки, при работе с базой данных
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientMapper mapper = ClientMapper.INSTANCE;
    private final ClientDao clientDao;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

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
    }
}
