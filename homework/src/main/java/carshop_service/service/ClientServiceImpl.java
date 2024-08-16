package carshop_service.service;

import carshop_service.dao.ClientDao;
import carshop_service.entity.Client;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Промежуточное звено между приложением и dao
 * Обрабатывает ошибки, которые могут возникнуть при работе с client dao
 */
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    @Override
    public boolean isExist(Client client) throws NoSuchEntityException {
        if(!clientDao.isExist(client)) throw new NoSuchEntityException("Client");
        return clientDao.isExist(client);
    }

    @Override
    public void addClient(Client client) throws DuplicateEntityException {
        if(clientDao.getClient(client.getId()) != null) throw new DuplicateEntityException("Client");
        this.clientDao.addClient(client);
    }

    @Override
    public Client getClient(int id) throws NoSuchEntityException{
        Client client = clientDao.getClient(id);
        if(client == null) throw  new NoSuchEntityException("Client");
        return client;
    }

    @Override
    public List<Client> getAllClients() throws DataBaseEmptyException {
        List<Client> clients = clientDao.getAllClients();
        if(clients.isEmpty()) throw new DataBaseEmptyException("Client");
        return clients;
    }

    @Override
    public void updateClient(Client client) throws NoSuchEntityException {
        if(clientDao.getClient(client.getId()) == null) throw new NoSuchEntityException("Client");
        this.clientDao.updateClient(client);
    }
}
