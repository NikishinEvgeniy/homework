package carshop.service.service;

import carshop.service.dao.ClientDao;
import carshop.service.dao.ClientDaoImpl;
import carshop.service.entity.Client;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ClientServiceTest {
    private ClientService clientService;
    private ClientDao clientDao;

    public ClientServiceTest(){
        this.clientDao = Mockito.mock(ClientDaoImpl.class);
        this.clientService = new ClientServiceImpl(clientDao);
    }

    @Test
    @DisplayName(" Ошибка, при получении клиента из БД (клиент не найден) ")
    public void getClientTest(){
        int id = 10;
        Mockito.when(clientDao.getClient(id))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->clientService.getClient(id));
    }

    @Test
    @DisplayName(" Ошибка, при получении списка клиентов из БД (список пуст) ")
    public void getAllClientsTest(){
        Mockito.when(clientDao.getAllClients())
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(DataBaseEmptyException.class,()->clientService.getAllClients());
    }

    @Test
    @DisplayName(" Ошибка, при добавлении клиента в БД (дубликат) ")
    public void addClientTest(){
        int id = 10;
        Client client = Client.builder()
                .id(id)
                .build();
        Mockito.when(clientDao.getClient(client.getId()))
                .thenReturn(client);
        Assertions.assertThrows(DuplicateEntityException.class,()->clientService.addClient(client));
    }

    @Test
    @DisplayName(" Ошибка, при обновлении клиента в БД (клиент не найден) ")
    public void updateClientTest(){
        int id = 10;
        Client client = Client.builder()
                .id(id)
                .build();
        Mockito.when(clientDao.getClient(client.getId()))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->clientService.updateClient(client));
    }

}
