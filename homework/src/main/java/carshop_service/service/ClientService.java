package carshop_service.service;


import carshop_service.entity.Client;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;

import java.util.List;

/**
 * Интерфейс, нужен для правильного соблюдения принципа DI
 */
public interface ClientService {
    boolean isExist(Client client) throws NoSuchEntityException;
    void addClient(Client client) throws DuplicateEntityException;
    Client getClient(int id) throws NoSuchEntityException;
    List<Client> getAllClients() throws DataBaseEmptyException;
    void updateClient(Client client) throws NoSuchEntityException;
}
