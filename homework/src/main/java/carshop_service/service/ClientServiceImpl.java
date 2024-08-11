package carshop_service.service;

import carshop_service.dao.ClientDao;
import carshop_service.entity.Client;
import lombok.AllArgsConstructor;
import java.util.List;


@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientDao dao;

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
