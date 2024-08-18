package carshop_service.application;

import carshop_service.constant.ClientRole;
import carshop_service.constant.ClientState;
import carshop_service.entity.Client;
import carshop_service.exception.NoSuchChooseException;
import carshop_service.handler.ConsoleEntityHandler;
import carshop_service.handler.EntityHandler;
import carshop_service.in.ConsoleInfoWriter;
import carshop_service.in.Writer;
import carshop_service.out.ConsoleViewer;
import carshop_service.out.Viewer;
import carshop_service.service.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ApplicationFacadeTest {

    private ApplicationFacade applicationFacade;
    private LogService logService;
    private OrderService orderService;
    private CarService carService;
    private ClientService clientService;
    private Viewer viewer;
    private Writer writer;
    private EntityHandler entityHandler;
    private Client client;

    public ApplicationFacadeTest(){
        this.logService = Mockito.mock(LogServiceImpl.class);
        this.carService = Mockito.mock(CarServiceImpl.class);
        this.orderService = Mockito.mock(OrderServiceImpl.class);
        this.clientService = Mockito.mock(ClientServiceImpl.class);
        this.viewer = Mockito.mock(ConsoleViewer.class);
        this.writer = Mockito.mock(ConsoleInfoWriter.class);
        this.entityHandler = Mockito.mock(ConsoleEntityHandler.class);
        applicationFacade =
                new ApplicationFacade(
                logService,orderService,carService,clientService,viewer,writer,entityHandler
                );
        this.client = Client.builder().build();
    }


    @Test
    @SneakyThrows
    @DisplayName("Смена состояния BEGIN_STATE -> BEGIN_CHOOSE_STATE")
    public void changeStateFromBeginToBeginChoose(){
        client.setState(ClientState.BEGIN_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.BEGIN_CHOOSE_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Choose_Main_Menu -> Admin_Menu")
    public void changeStateFromСhooseMainMenuToAdminMenu(){
        client.setState(ClientState.CHOOSE_MAIN_MENU_STATE);
        client.setRole(ClientRole.ADMIN);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.ADMIN_MENU_STATE);
    }
    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Choose_Main_Menu -> Client_Menu")
    public void changeStateFromСhooseMainMenuToClientMenu(){
        client.setState(ClientState.CHOOSE_MAIN_MENU_STATE);
        client.setRole(ClientRole.CLIENT);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CLIENT_MENU_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Choose_Main_Menu -> Manager_Menu")
    public void changeStateFromСhooseMainMenuToManagerMenu(){
        client.setState(ClientState.CHOOSE_MAIN_MENU_STATE);
        client.setRole(ClientRole.MANAGER);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.MANAGER_MENU_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Begin_Choose -> Registration")
    public void changeStateFromBeginChooseToRegistration(){
        Mockito.when(writer.getChoose()).thenReturn(1);
        client.setState(ClientState.BEGIN_CHOOSE_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.REGISTRATION_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Begin_Choose -> Authorization")
    public void changeStateFromBeginChooseToAuthorization(){
        Mockito.when(writer.getChoose()).thenReturn(2);
        client.setState(ClientState.BEGIN_CHOOSE_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.AUTHORIZATION_STATE);
    }

    @Test
    @DisplayName("Вывод ошибки при не правильном вводе Begin_Choose -> Begin_Choose")
    public void changeStateFromBeginChooseToBeginChooseException(){
        Mockito.when(writer.getChoose()).thenReturn(3);
        client.setState(ClientState.BEGIN_CHOOSE_STATE);
        Assertions.assertThrows(NoSuchChooseException.class,()->applicationFacade.checkState(client));
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Admin_Menu -> Menu_Choose")
    public void changeStateFromAdminMenuToMenuChoose(){
        client.setState(ClientState.ADMIN_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.MENU_CHOOSE_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Client_Menu -> Menu_Choose")
    public void changeStateFromClientMenuToMenuChoose(){
        client.setState(ClientState.CLIENT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.MENU_CHOOSE_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Manager_Menu -> Menu_Choose")
    public void changeStateFromManagerMenuToMenuChoose(){
        client.setState(ClientState.MANAGER_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.MENU_CHOOSE_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Order_Output_Menu -> Order_Filter")
    public void changeStateFromOrderOutputMenuToOrderFilter(){
        Mockito.when(writer.getChoose()).thenReturn(1);
        client.setState(ClientState.ORDER_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.ORDER_FILTER_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Order_Output_Menu -> Choose_Main_Menu")
    public void changeStateFromOrderOutputMenuToChooseMainMenu(){
        Mockito.when(writer.getChoose()).thenReturn(2);
        client.setState(ClientState.ORDER_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CHOOSE_MAIN_MENU_STATE);
    }

    @Test
    @DisplayName("Вывод ошибки при не правильном вводе Order_Output_Menu -> Order_Output_Menu")
    public void changeStateFromOrderOutputMenuToOrderOutputMenuException(){
        Mockito.when(writer.getChoose()).thenReturn(3);
        client.setState(ClientState.ORDER_OUTPUT_MENU_STATE);
        Assertions.assertThrows(NoSuchChooseException.class,()->applicationFacade.checkState(client));
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Magazine_Output_Menu -> Order_Filter")
    public void changeStateFromMagazineOutputMenuToMagazineFilter(){
        Mockito.when(writer.getChoose()).thenReturn(1);
        client.setState(ClientState.MAGAZINE_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.MAGAZINE_FILTER_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Magazine_Output_Menu -> Choose_Main_Menu")
    public void changeStateFromMagazineOutputMenuToChooseMainMenu(){
        Mockito.when(writer.getChoose()).thenReturn(2);
        client.setState(ClientState.MAGAZINE_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CHOOSE_MAIN_MENU_STATE);
    }

    @Test
    @DisplayName("Вывод ошибки при не правильном вводе Magazine_Output_Menu -> Magazine_Output_Menu")
    public void changeStateFromMagazineOutputMenuToMagazineOutputMenuException(){
        Mockito.when(writer.getChoose()).thenReturn(3);
        client.setState(ClientState.MAGAZINE_OUTPUT_MENU_STATE);
        Assertions.assertThrows(NoSuchChooseException.class,()->applicationFacade.checkState(client));
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Client_Output_Menu -> Client_Sort")
    public void changeStateFromClientOutputMenuToClientSort(){
        Mockito.when(writer.getChoose()).thenReturn(1);
        client.setState(ClientState.CLIENT_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CLIENT_SORT_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Client_Output_Menu -> Client_Filter")
    public void changeStateFromClientOutputMenuToClientFilter(){
        Mockito.when(writer.getChoose()).thenReturn(2);
        client.setState(ClientState.CLIENT_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CLIENT_FILTER_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Client_Output_Menu -> Choose_Main_Menu")
    public void changeStateFromClientOutputMenuToChooseMainMenu(){
        Mockito.when(writer.getChoose()).thenReturn(3);
        client.setState(ClientState.CLIENT_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CHOOSE_MAIN_MENU_STATE);
    }

    @Test
    @DisplayName("Вывод ошибки при не правильном вводе Client_Output_Menu -> Client_Output_Menu")
    public void changeStateFromClientOutputMenuToClientOutputMenuException(){
        Mockito.when(writer.getChoose()).thenReturn(4);
        client.setState(ClientState.CLIENT_OUTPUT_MENU_STATE);
        Assertions.assertThrows(NoSuchChooseException.class,()->applicationFacade.checkState(client));
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Car_Output_Menu -> Car_Filter")
    public void changeStateFromCarOutputMenuToCarFilter(){
        Mockito.when(writer.getChoose()).thenReturn(1);
        client.setState(ClientState.CAR_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CAR_FILTER_STATE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Смена состояния Car_Output_Menu -> Choose_Main_Menu")
    public void changeStateFromCarOutputMenuToChooseMainMenu(){
        Mockito.when(writer.getChoose()).thenReturn(2);
        client.setState(ClientState.CAR_OUTPUT_MENU_STATE);
        applicationFacade.checkState(client);
        Assertions.assertEquals(client.getState(),ClientState.CHOOSE_MAIN_MENU_STATE);
    }

    @Test
    @DisplayName("Вывод ошибки при не правильном вводе Car_Output_Menu -> Car_Output_Menu")
    public void changeStateFromCarOutputMenuToCarOutputMenuException(){
        Mockito.when(writer.getChoose()).thenReturn(3);
        client.setState(ClientState.CAR_OUTPUT_MENU_STATE);
        Assertions.assertThrows(NoSuchChooseException.class,()->applicationFacade.checkState(client));
    }

}
