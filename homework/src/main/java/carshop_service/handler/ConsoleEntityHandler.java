package carshop_service.handler;

import carshop_service.constant.OrderState;
import carshop_service.constant.OrderType;
import carshop_service.constant.UserRole;
import carshop_service.constant.UserState;
import carshop_service.entity.Car;
import carshop_service.entity.Client;
import carshop_service.entity.Order;
import carshop_service.exception.IncorrectRoleException;
import carshop_service.exception.IncorrectStateException;
import carshop_service.exception.NoSuchCarException;
import carshop_service.exception.NoSuchClientException;
import carshop_service.in.Writer;
import carshop_service.out.Viewer;
import carshop_service.service.CarService;
import carshop_service.service.ClientService;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ConsoleEntityHandler implements EntityHandler{

    public Client createClientByLoginPassword(Viewer consoleViewer, Writer consoleInfoWriter){
        consoleViewer.showEnterLogin();
        String login = consoleInfoWriter.getLogin();
        consoleViewer.showEnterPassword();
        String password = consoleInfoWriter.getPassword();
        consoleViewer.showEnterRole();
        String role = consoleInfoWriter.getRole();
        String[] roles = UserRole.getAllRoles();
        boolean isTrueRole = Arrays.stream(roles).anyMatch(x -> x.equals(role));
        if(!isTrueRole) throw new IncorrectRoleException(); // Обработка исключения
        return new Client(login,password,role);
    }
    public Client createFullClient(Viewer consoleViewer, Writer consoleInfoWriter){
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
        String[] roles = UserRole.getAllRoles();
        boolean isTrueRole = Arrays.stream(roles).anyMatch(x -> x.equals(role));
        if(!isTrueRole) throw new IncorrectRoleException(); // Обработка исключения
        return new Client(name,surname,login,password,role, UserState.BEGIN_STATE);
    }
    public void fillTheEmptyClientRegistration(Client client, Viewer consoleViewer, Writer consoleWriter){
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
        String[] roles = UserRole.getAllRoles();
        boolean isTrueRole = Arrays.stream(roles).anyMatch(x -> x.equals(role));
        if(!isTrueRole) throw new IncorrectRoleException();
        client.setPassword(password);
        client.setLogin(login);
        client.setRole(role);
        client.setName(name);
        client.setSurname(surname);
    }
    public void fillTheEmptyClientAuthorization(Client client, Viewer consoleViewer, Writer consoleWriter){
        consoleViewer.showEnterLogin();
        String login = consoleWriter.getLogin();
        consoleViewer.showEnterPassword();
        String password = consoleWriter.getPassword();
        consoleViewer.showEnterRole();
        String role = consoleWriter.getRole();
        String[] roles = UserRole.getAllRoles();
        boolean isTrueRole = Arrays.stream(roles).anyMatch(x -> x.equals(role));
        if(!isTrueRole) throw new IncorrectRoleException(); // Обработка исключения
        client.setLogin(login);
        client.setPassword(password);
        client.setRole(role);
    }
    public Client updateClientDescription(Client oldClient, Viewer consoleViewer, Writer consoleWriter){
        fillTheEmptyClientRegistration(oldClient,consoleViewer,consoleWriter);
        return oldClient;
    }
    public Car createCarByBrandModelAndOther(Viewer consoleViewer, Writer consoleInfoWriter){
        consoleViewer.showEnterCarModel();
        String model = consoleInfoWriter.getModel();
        consoleViewer.showEnterCarBrand();
        String brand = consoleInfoWriter.getBrand();
        consoleViewer.showEnterCarPrice();
        double price = consoleInfoWriter.getPrice(); // Обработка исключения, если это не число
        consoleViewer.showEnterCondition();
        String condition = consoleInfoWriter.getCondition();
        consoleViewer.showEnterCarYear();
        int yearOfRelease = consoleInfoWriter.getYearOfRelease();
        return new Car(brand,model,price,yearOfRelease,condition);
    }
    public Order createOrderByIdCarAndOther(Viewer consoleViewer, Writer consoleInfoWriter,
                                             CarService carService, ClientService clientService){
        consoleViewer.showEnterCarId();
        int carId = consoleInfoWriter.getId();
        if(carService.getCar(carId) == null) throw new NoSuchCarException();
        consoleViewer.showEnterClientId();
        int clientId = consoleInfoWriter.getId();
        if(clientService.getClient(clientId) == null) throw new NoSuchClientException();
        consoleViewer.showEnterCondition();
        String condition = consoleInfoWriter.getCondition();
        String[] conditions = OrderState.getAllStates();
        boolean isTrueState = Arrays.stream(conditions).anyMatch(x -> x.equals(condition));
        if(!isTrueState) throw new IncorrectStateException(); // Ошибочка, отделить
        consoleViewer.showEnterOrderType();
        String type = consoleInfoWriter.getOrderType();
        String[] types = OrderType.getAllStates();
        boolean isTrueType = Arrays.stream(types).anyMatch(x -> x.equals(type));
        if(!isTrueType) throw new IncorrectStateException(); // Ошибочка, отделить
        return new Order(carId,clientId,condition,type, LocalDateTime.now());
    }
    public Car createUpdateCarByBrandModelAndOther(Car car, Viewer consoleViewer, Writer consoleInfoWriter){
        Car carUpdate = createCarByBrandModelAndOther(consoleViewer, consoleInfoWriter);
        carUpdate.setId(car.getId());
        return carUpdate;
    }
    public Order createUpdateOrderStatus(Order order, Viewer consoleViewer, Writer consoleInfoWriter){
        consoleViewer.showEnterCondition();
        String condition = consoleInfoWriter.getCondition();
        String[] conditions = OrderState.getAllStates();
        boolean isTrueState = Arrays.stream(conditions).anyMatch(x -> x.equals(condition));
        if(!isTrueState) throw new IncorrectStateException(); // Ошибочка, отделить
        order.setState(condition);
        return order;
    }
}
