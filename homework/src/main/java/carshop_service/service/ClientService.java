package carshop_service.service;


import carshop_service.dto.ClientDto;
import carshop_service.entity.Client;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;

import java.util.List;

public interface ClientService {
    boolean isExist(Client client) throws NoSuchEntityException;
    void addClient(Client client) throws DuplicateEntityException;
    ClientDto getClient(int id) throws NoSuchEntityException;
    List<ClientDto> getAllClients() throws DataBaseEmptyException;
    void updateClient(Client client) throws NoSuchEntityException;
}
