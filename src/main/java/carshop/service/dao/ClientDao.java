package carshop.service.dao;


import carshop.service.entity.Client;
import ya.lab.loggable_starter.annotation.Loggable;
import ya.lab.loggable_starter.constant.LogAction;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface ClientDao {
    boolean isExist(Client client);
    @Loggable(action = LogAction.ADD_CLIENT_ACTION)
    void addClient(Client client);
    Client getClient(int id);
    List<Client> getAllClients();
    @Loggable(action = LogAction.UPDATE_CLIENT_ACTION)
    void updateClient(Client client);
}
