package carshop_service.dao;

import carshop_service.entity.Client;

import java.util.List;

public interface ClientDao {
    boolean isExist(Client client);
    void addClient(Client client);
    Client getClient(int id);
    List<Client> getAllClients();
    void updateClient(Client client);
}
