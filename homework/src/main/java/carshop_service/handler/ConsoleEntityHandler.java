package carshop_service.handler;

import carshop_service.constant.*;
import carshop_service.entity.*;
import carshop_service.exception.*;
import carshop_service.in.Writer;
import carshop_service.out.Viewer;
import carshop_service.service.CarService;
import carshop_service.service.ClientService;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Консольная реализация собирания сущностей, через ввод и вывод информации пользователя в консоль
 */
public class ConsoleEntityHandler implements EntityHandler{

    public Client createClientByLoginPassword(Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectRoleException {
        consoleViewer.showEnterLogin();
        String login = consoleInfoWriter.getLogin();
        consoleViewer.showEnterPassword();
        String password = consoleInfoWriter.getPassword();
        consoleViewer.showEnterRole();
        String role = consoleInfoWriter.getRole();
        return Client.builder()
                .login(login)
                .password(password)
                .role(ClientRole.valueOf(role))
                .checkRole()
                .build();
    }
    public Client createFullClient(Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectRoleException, IncorrectStateException {
        consoleViewer.showEnterName();
        String name = consoleInfoWriter.getName();
        consoleViewer.showEnterSurname();
        String surname = consoleInfoWriter.getSurname();
        consoleViewer.showEnterLogin();
        String login = consoleInfoWriter.getLogin();
        consoleViewer.showEnterPassword();
        String password = consoleInfoWriter.getPassword();
        consoleViewer.showEnterRole();
        String role = consoleInfoWriter.getRole();
        return Client.builder()
                .name(name)
                .state(ClientState.BEGIN_STATE)
                .surname(surname)
                .login(login)
                .password(password)
                .role(ClientRole.valueOf(role))
                .checkState()
                .checkRole()
                .build();
    }
    public void fillTheEmptyClientRegistration(Client client, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException {
        consoleViewer.showEnterName();
        String name = consoleWriter.getName();
        consoleViewer.showEnterSurname();
        String surname = consoleWriter.getSurname();
        consoleViewer.showEnterLogin();
        String login = consoleWriter.getLogin();
        consoleViewer.showEnterPassword();
        String password = consoleWriter.getPassword();
        consoleViewer.showEnterRole();
        String role = consoleWriter.getRole();
        boolean isTrueRole = Arrays.stream(ClientRole.values()).anyMatch(x -> x.toString().equals(role));
        if(!isTrueRole) throw new IncorrectRoleException();
        client.setPassword(password);
        client.setLogin(login);
        client.setRole(ClientRole.valueOf(role));
        client.setName(name);
        client.setSurname(surname);
    }
    public Client updateClientDescription(Client oldClient, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException {
        fillTheEmptyClientRegistration(oldClient,consoleViewer,consoleWriter);
        return oldClient;
    }
    public Car createCarByBrandModelAndOther(Viewer consoleViewer, Writer consoleInfoWriter){
        consoleViewer.showEnterCarModel();
        String model = consoleInfoWriter.getModel();
        consoleViewer.showEnterCarBrand();
        String brand = consoleInfoWriter.getBrand();
        consoleViewer.showEnterCarPrice();
        double price = consoleInfoWriter.getPrice();
        consoleViewer.showEnterCondition();
        String condition = consoleInfoWriter.getCondition();
        consoleViewer.showEnterCarYear();
        int yearOfRelease = consoleInfoWriter.getYearOfRelease();
        return Car.builder()
                .brand(brand)
                .model(model)
                .price(price)
                .yearOfRelease(yearOfRelease)
                .condition(condition)
                .build();
    }
    public Order createOrderByIdCarAndOther(Viewer consoleViewer, Writer consoleInfoWriter,
                                             CarService carService, ClientService clientService) throws NoSuchEntityException, IncorrectStateException, IncorrectTypeException {
        consoleViewer.showEnterCarId();
        int carId = consoleInfoWriter.getId();
        if(carService.getCar(carId) == null) throw new NoSuchEntityException("Car");
        consoleViewer.showEnterClientId();
        int clientId = consoleInfoWriter.getId();
        if(clientService.getClient(clientId) == null) throw new NoSuchEntityException("Car");
        consoleViewer.showEnterCondition();
        String condition = consoleInfoWriter.getCondition();
        consoleViewer.showEnterOrderType();
        String type = consoleInfoWriter.getOrderType();
        return Order.builder()
                .carId(carId)
                .clientId(clientId)
                .state(OrderState.valueOf(condition))
                .type(OrderType.valueOf(type))
                .dateTime(LocalDateTime.now())
                .checkState()
                .checkType()
                .build();
    }
    public Car createUpdateCarByBrandModelAndOther(Car car, Viewer consoleViewer, Writer consoleInfoWriter){
        Car carUpdate = createCarByBrandModelAndOther(consoleViewer, consoleInfoWriter);
        carUpdate.setId(car.getId());
        return carUpdate;
    }
    public Order createUpdateOrderStatus(Order order, Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectStateException {
        consoleViewer.showEnterCondition();
        String condition = consoleInfoWriter.getCondition();
        boolean isTrueState = Arrays.stream(ClientState.values()).anyMatch(x -> x.toString().equals(condition));
        if(!isTrueState) throw new IncorrectStateException();
        order.setState(OrderState.valueOf(condition));
        return order;
    }
    public Log createLog(LogAction action, int id){
        return Log.builder()
                .clientId(id)
                .dateTime(LocalDateTime.now())
                .action(action)
                .build();
    }
}
