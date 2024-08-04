package carshop_service.dao;

import carshop_service.constant.UserState;
import carshop_service.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClientDaoTest {
    private ClientDaoImpl clientDao = new ClientDaoImpl();

    @Test
    @DisplayName(" Ошибка при получение клиента ")
    public void getClientTest_false(){
        Assertions.assertEquals(null,clientDao.getClient(3232));
    }



    @Test
    @DisplayName(" Добавление нового клиента ")
    public void saveClientTest_true(){
        Client client = new Client("Name","Surname","lpgfgd","32","admin", UserState.BEGIN_STATE);
        clientDao.addClient(client);
        Assertions.assertEquals(client,clientDao.getClient(client.getId()));
    }

    @Test
    @DisplayName(" Обновление информации о клиенте ")
    public void updateClientTest_true(){
        Client client = new Client("Name","Surname","lpgfgd","32","admin", UserState.BEGIN_STATE);
        clientDao.addClient(client);
        client.setCountOfBuy(3232);
        clientDao.updateClient(client);
        Assertions.assertEquals(client,clientDao.getClient(client.getId()));
    }

    @Test
    @DisplayName(" Проверка на существование информации о клиенте ")
    public void isExistClientTest_true(){
        Client client = new Client("Name","Surname","lpgfgd","32","admin", UserState.BEGIN_STATE);
        clientDao.addClient(client);
        Assertions.assertEquals(true,clientDao.isExist(client));
    }

    @Test
    @DisplayName(" Ошибка при проверке на существование информации о клиенте ")
    public void isExistClientTest_false(){
        Client client = new Client("Name","Surname","lpgfgd","32","admin", UserState.BEGIN_STATE);
        Assertions.assertEquals(false,clientDao.isExist(client));
    }
}
