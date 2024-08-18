package carshop_service.dao;

import carshop_service.entity.Client;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface ClientDao {
    boolean isExist(Client client);
    void addClient(Client client);
    Client getClient(int id);
    List<Client> getAllClients();
    void updateClient(Client client);
}
