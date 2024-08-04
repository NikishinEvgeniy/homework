package carshop_service.application;

import carshop_service.constant.UserState;
import carshop_service.entity.Client;
import carshop_service.exception.ClientIsExistException;
import carshop_service.exception.NoSuchClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class ApplicationFacadeTest {
    private ApplicationFacade facade = new ApplicationFacade();

    @Test
    @DisplayName(" сохранение клиента после регистрации")
    void saveClientAfterRegistration_true(){
        Client client = new Client("Client","Client","newLogin","newPassword","admin", UserState.BEGIN_STATE);
        facade.saveClientAfterRegistration(client);
    }
    @Test
    @DisplayName(" вывод ошибки, при сохранении клиента, который уже был в бд")
    void saveClientAfterRegistration_false(){
        Client client = new Client("Client","Client","login","password","admin", UserState.BEGIN_STATE);
        Assertions.assertThrows(ClientIsExistException.class,() -> facade.saveClientAfterRegistration(client));
    }
    @Test
    @DisplayName(" вывод ошибки при попытке получение клиента по не существующим login|password|role ")
    void getClientAfterAuthorization_false(){
        Client client = new Client("daas","passdsadaword","admin");
        Assertions.assertThrows(NoSuchClientException.class,() -> facade.getClientAfterAuthorization(client));
    }
    @Test
    @DisplayName(" получение клиента по существующим данным ")
    void getClientAfterAuthorization_true(){
        Client client = new Client("login","password","admin");
        Assertions.assertDoesNotThrow( () -> facade.getClientAfterAuthorization(client) );
    }

    @Test
    @DisplayName(" вывод ошибки ( для админа ) при выборе не существующего пункта меню ")
    void chooseMenuForAdmin_false(){
        Client user = new Client(UserState.BEGIN_STATE);
        int choose = 32323232;
        user.setRole("admin");
        Assertions.assertEquals(false,facade.checkChoose(choose,user));
    }

    @Test
    @DisplayName(" выбор существующего пункта меню и получение состояния ( для админа ) ")
    void chooseMenuForAdmin_true(){
        Client user = new Client(UserState.BEGIN_STATE);
        int choose = 13;
        user.setRole("admin");
        Assertions.assertEquals(true,facade.checkChoose(choose,user));
    }

    @Test
    @DisplayName(" вывод ошибки ( для менеджера ) при выборе не существующего пункта меню ")
    void chooseMenuForManager_false(){
        Client user = new Client(UserState.BEGIN_STATE);
        int choose = 13;
        user.setRole("manager");
        Assertions.assertEquals(false,facade.checkChoose(choose,user));
    }

    @Test
    @DisplayName(" выбор существующего пункта меню и получение состояния ( для менеджера ) ")
    void chooseMenuForManager_true(){
        Client user = new Client(UserState.BEGIN_STATE);
        int choose = 9;
        user.setRole("manager");
        Assertions.assertEquals(true,facade.checkChoose(choose,user));
    }

    @Test
    @DisplayName(" вывод ошибки ( для клиента ) при выборе не существующего пункта меню ")
    void chooseMenuForСlient_false(){
        Client user = new Client(UserState.BEGIN_STATE);
        int choose = 3;
        user.setRole("client");
        Assertions.assertEquals(false,facade.checkChoose(choose,user));
    }

    @Test
    @DisplayName(" выбор существующего пункта меню и получение состояния ( для клиента )) ")
    void chooseMenuForClient_true(){
        Client user = new Client(UserState.BEGIN_STATE);
        int choose = 1;
        user.setRole("client");
        Assertions.assertEquals(true,facade.checkChoose(choose,user));
    }

}