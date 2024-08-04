package carshop_service.service;

import carshop_service.dao.ClientDao;
import carshop_service.dao.ClientDaoImpl;
import carshop_service.entity.Client;

import java.util.List;

public class ClientServiceImpl implements ClientService {
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
    }
}
