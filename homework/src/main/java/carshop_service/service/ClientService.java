package carshop_service.service;


import carshop_service.dto.ClientDto;
import carshop_service.entity.Client;

import java.util.List;

public interface ClientService {
<<<<<<< Updated upstream
    boolean isExist(Client client);
    void addClient(Client client);
    Client getClient(int id);
    List<Client> getAllClients();
    void updateClient(Client client);
=======
    boolean isExist(Client client) throws NoSuchEntityException;
    void addClient(Client client) throws DuplicateEntityException;
    ClientDto getClient(int id) throws NoSuchEntityException;
    List<ClientDto> getAllClients() throws DataBaseEmptyException;
    void updateClient(Client client) throws NoSuchEntityException;
>>>>>>> Stashed changes
}
