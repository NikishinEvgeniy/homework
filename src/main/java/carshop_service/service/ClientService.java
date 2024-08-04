package carshop_service.service;


import carshop_service.entity.Client;

import java.util.List;

public interface ClientService {
    boolean isExist(Client client);
    void addClient(Client client);
    Client getClient(int id);
    List<Client> getAllClients();
    void updateClient(Client client);
}
