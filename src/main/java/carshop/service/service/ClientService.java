package carshop.service.service;



import carshop.service.dto.ClientDto;
import carshop.service.entity.Client;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;

import java.util.List;

public interface ClientService {
    boolean isExist(Client client) throws NoSuchEntityException;
    void addClient(Client client) throws DuplicateEntityException;
    ClientDto getClient(int id) throws NoSuchEntityException;
    List<ClientDto> getAllClients() throws DataBaseEmptyException;
    void updateClient(Client client) throws NoSuchEntityException;
}
