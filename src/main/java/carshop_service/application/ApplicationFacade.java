package carshop_service.application;

import carshop_service.constant.LogAction;
import carshop_service.constant.OrderType;
import carshop_service.entity.Car;
import carshop_service.entity.Client;
import carshop_service.entity.Log;
import carshop_service.entity.Order;
import carshop_service.exception.*;
import carshop_service.handler.ConsoleEntityHandler;
import carshop_service.handler.EntityHandler;
import carshop_service.in.ConsoleInfoWriter;
import carshop_service.in.Writer;
import carshop_service.out.ConsoleViewer;
import carshop_service.out.Viewer;
import carshop_service.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static carshop_service.constant.UserRole.*;
import static carshop_service.constant.UserState.*;

public class ApplicationFacade {
    private LogService logService;
    private OrderService orderService;
    private CarService carService;
    private ClientService clientService;
    private Viewer consoleViewer;
    private Writer consoleInfoWriter;
    private EntityHandler consoleEntityHandler;

    /**
     * В постоянном режиме проверяет состояние пользователя.
     * Все состояния можно увидеть в пакете constant
     * @throws IOException
     */
 public void initialize() throws IOException {
        Client user = new Client(BEGIN_STATE);
        while (true){
            checkState(user);
        }
    }

    /**
     *
     * @param user - пользователь, который сейчас использует программу. Со своим состоянием
     *             Изначально создается пустой пользователь, только с начальным состоянием
     *             В зависимости от регистрации и авторизации происходит заполнение данного объекта
     *             При регистрации, созданный объект просто заполняется.
     *             При авторизации, в созданном объекте заполняются поля
     *             login, password, role -> после чего происходит поиск в БД.
     *             При нахождении пользователя, его контактная информация копируется в ранее созданный объект.
     *             Идея приложения - проверка состояний пользователя и последующее их изменение при выборе определенного пункта
     * @throws IOException
     */
    public void checkState(Client user) throws IOException {
        int choose,id;
        switch (user.getState()){

            case BEGIN_STATE:
                consoleViewer.showMainPage();
                user.setState(BEGIN_CHOOSE_STATE);
                break;

            case BEGIN_CHOOSE_STATE:
                consoleViewer.showEnterChoose();
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1: user.setState(REGISTRATION_STATE);  break;
                    case 2: user.setState(AUTHORIZATION_STATE)  ;break;
                    default: throw new NoSuchChooseException();
                }
                break;

            case REGISTRATION_STATE:
                consoleViewer.showRegistrationTitle();
                consoleEntityHandler.fillTheEmptyClientRegistration(user,consoleViewer,consoleInfoWriter);
                saveClientAfterRegistration(user);
                user.setState(AUTHORIZATION_STATE);
                logService.addLog(new Log(user.getId(), LogAction.REGISTRATION_ACTION));
                break;

            case AUTHORIZATION_STATE:
                consoleViewer.showAuthorizationTitle();
                Client tempUser = consoleEntityHandler.createClientByLoginPassword(consoleViewer,consoleInfoWriter);
                Client client = getClientAfterAuthorization(tempUser);
                fillTheAuthorizationUserByClient(user,client);
                user.setState(CHOOSE_MAIN_MENU_STATE);
                logService.addLog(new Log(user.getId(), LogAction.AUTHORIZATION_ACTION));
                break;

            case CHOOSE_MAIN_MENU_STATE:
                switch (user.getRole()){
                    case ADMIN: user.setState(ADMIN_MENU_STATE); break;
                    case CLIENT: user.setState(CLIENT_MENU_STATE); break;
                    case MANAGER: user.setState(MANAGER_MENU_STATE); break;
                }
                break;

            case ADMIN_MENU_STATE:
                consoleViewer.showMainMenuTitle();
                consoleViewer.showAdminCommandPage();
                user.setState(MENU_CHOOSE_STATE);
                break;

            case CLIENT_MENU_STATE:
                consoleViewer.showMainMenuTitle();
                consoleViewer.showClientCommandPage();
                user.setState(MENU_CHOOSE_STATE);
                break;

            case MANAGER_MENU_STATE:
                consoleViewer.showMainMenuTitle();
                consoleViewer.showManagerCommandPage();
                user.setState(MENU_CHOOSE_STATE);
                break;

            case MENU_CHOOSE_STATE:
                consoleViewer.showEnterChoose();
                choose = consoleInfoWriter.getChoose();
                if(!checkChoose(choose,user)) throw new NoSuchChooseException();
                switch (choose){
                    case 1:
                        List<Car> cars = carService.getAllCars();
                        consoleViewer.showAvaibleCarsTitle();
                        consoleViewer.showAllCarsPage(cars);
                        consoleViewer.showSuccessTitle();
                        consoleViewer.showFilterMenu();
                        consoleViewer.showEnterChoose();
                        user.setState(CAR_OUTPUT_MENU_STATE);
                        break;
                    case 2:
                        List<Order> orders = orderService.getAllOrders();
                        consoleViewer.showAvaibleOrdersTitle();
                        consoleViewer.showAllOrdersPage(orders);
                        consoleViewer.showSuccessTitle();
                        consoleViewer.showFilterMenu();
                        consoleViewer.showEnterChoose();
                        user.setState(ORDER_OUTPUT_MENU_STATE);
                        break;
                    case 3:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId();
                        Car carUpOld = carService.getCar(id);
                        if(carUpOld != null) {
                            consoleViewer.showSimpleCar(carUpOld);
                            consoleViewer.showUpdateTitle();
                            Car carUpNew = consoleEntityHandler.createUpdateCarByBrandModelAndOther(carUpOld,consoleViewer,consoleInfoWriter);
                            carService.updateCar(carUpNew);
                            consoleViewer.showSuccessTitle();
                            user.setState(CHOOSE_MAIN_MENU_STATE);
                            logService.addLog(new Log(user.getId(), LogAction.UPDATE_CAR_ACTION));
                        }
                        else throw new NoSuchCarException();
                        break;
                    case 4:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId(); // Ошибка с водом обработать
                        Car carFromDb = carService.getCar(id);
                        if(carFromDb != null) {
                            carService.deleteCar(carFromDb);
                            consoleViewer.showSuccessTitle();
                            user.setState(CHOOSE_MAIN_MENU_STATE);
                            logService.addLog(new Log(user.getId(), LogAction.DELETE_CAR_ACTION));
                        }
                        else throw new NoSuchCarException();
                        break;
                    case 5:
                        consoleViewer.showCreateOrderTitle();
                        consoleViewer.showEnterData();
                        Order order = consoleEntityHandler.createOrderByIdCarAndOther(
                                consoleViewer,consoleInfoWriter,carService,clientService);
                        orderService.addOrder(order);
                        consoleViewer.showSuccessTitle();
                        if(order.getType().equals(OrderType.SALE)) {
                            client = clientService.getClient(order.getClientId());
                            client.setCountOfBuy(client.getCountOfBuy() + 1);
                        }
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog(new Log(user.getId(), LogAction.ADD_ORDER_ACTION));
                        break;
                    case 6:
                        Car carAdd = consoleEntityHandler.createCarByBrandModelAndOther(consoleViewer,consoleInfoWriter);
                        carService.addCar(carAdd);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog(new Log(user.getId(), LogAction.ADD_CAR_ACTION));
                        break;
                    case 7:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId(); // Ошибка с водом обработать
                        Order orderFromDb = orderService.getOrder(id);
                        if(orderFromDb != null) {
                            consoleViewer.showSimpleOrder(orderFromDb);
                            consoleViewer.showSuccessTitle();
                            user.setState(CHOOSE_MAIN_MENU_STATE);
                        }
                        else throw new NoSuchOrderException();
                        break;
                    case 8:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId(); // Ошибка с водом обработать
                        Order orderUpOld = orderService.getOrder(id);
                        if(orderUpOld != null) {
                            consoleViewer.showSimpleOrder(orderUpOld);
                            consoleViewer.showUpdateTitle();
                            Order orderUpNew = consoleEntityHandler.createUpdateOrderStatus(orderUpOld,consoleViewer,consoleInfoWriter);
                            orderService.updateOrder(orderUpNew);
                            consoleViewer.showSuccessTitle();
                            user.setState(CHOOSE_MAIN_MENU_STATE);
                            logService.addLog(new Log(user.getId(), LogAction.UPDATE_ORDER_ACTION));
                        }
                        else throw new NoSuchCarException();
                        break;
                    case 9:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId(); // Ошибка с водом обработать
                        Order orderFromBd = orderService.getOrder(id);
                        if(orderFromBd != null) {
                            orderService.deleteOrder(orderFromBd);
                            consoleViewer.showSuccessTitle();
                            user.setState(CHOOSE_MAIN_MENU_STATE);
                            logService.addLog(new Log(user.getId(), LogAction.CANCEL_ORDER_ACTION));
                        }
                        else throw new NoSuchCarException();
                        break;
                    case 10:
                        List<Client> clients = clientService.getAllClients();
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        consoleViewer.showOutputMenu();
                        consoleViewer.showEnterChoose();
                        user.setState(CLIENT_OUTPUT_MENU_STATE);
                        break;
                    case 11:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId(); // Ошибка с водом обработать
                        client = clientService.getClient(id);
                        if(client != null) {
                            consoleViewer.showSimpleClient(client);
                            consoleViewer.showUpdateTitle();
                            consoleEntityHandler.updateClientDescription(client,consoleViewer,consoleInfoWriter) ;
                            clientService.updateClient(client);
                            consoleViewer.showSuccessTitle();
                            user.setState(CHOOSE_MAIN_MENU_STATE);
                            logService.addLog(new Log(user.getId(), LogAction.UPDATE_CLIENT_ACTION));
                        }
                        else throw new NoSuchCarException();
                        break;
                    case 12:
                        Client client1 = consoleEntityHandler.createFullClient(consoleViewer,consoleInfoWriter);
                        clientService.addClient(client1);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog(new Log(user.getId(), LogAction.ADD_CLIENT_ACTION));
                        break;
                    case 13:
                        List<Log> logs = logService.getAllLogs();
                        consoleViewer.showMagazineTitle();
                        consoleViewer.showAllLogsPage(logs);
                        consoleViewer.showSuccessTitle();
                        consoleViewer.showFilterMenu();
                        consoleViewer.showEnterChoose();
                        user.setState(MAGAZINE_OUTPUT_MENU_STATE);
                        break;
                    case 14:
                        logService.exportLogsInTextFile();
                        consoleViewer.showSuccessTitle();
                        consoleViewer.showEnterChoose();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                }
                break;

            case ORDER_OUTPUT_MENU_STATE:
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1: user.setState(ORDER_FILTER_STATE); break;
                    case 2: user.setState(CHOOSE_MAIN_MENU_STATE); break;
                    default: throw new NoSuchChooseException();
                }
                break;

            case MAGAZINE_OUTPUT_MENU_STATE:
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1: user.setState(MAGAZINE_FILTER_STATE); break;
                    case 2: user.setState(CHOOSE_MAIN_MENU_STATE); break;
                    default: throw new NoSuchChooseException();
                }
                break;

            case CLIENT_OUTPUT_MENU_STATE:
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1: user.setState(CLIENT_SORT_STATE); break;
                    case 2: user.setState(CLIENT_FILTER_STATE); break;
                    case 3: user.setState(CHOOSE_MAIN_MENU_STATE); break;
                    default: throw new NoSuchChooseException();
                }
                break;
            case CAR_OUTPUT_MENU_STATE:
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1: user.setState(CAR_FILTER_STATE); break;
                    case 2: user.setState(CHOOSE_MAIN_MENU_STATE); break;
                    default: throw new NoSuchChooseException();
                }
                break;

            case MAGAZINE_FILTER_STATE:
                consoleViewer.showAvaibleFiltersTitle();
                consoleViewer.showLogsOutputVariableMenu();
                consoleViewer.showEnterChoose();
                List<Log> logs;
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterMonth();
                        int month = consoleInfoWriter.getMonth();
                        consoleViewer.showEnterDay();
                        int day = consoleInfoWriter.getDay();
                        consoleViewer.showEnterHour();
                        int hour = consoleInfoWriter.getHour();
                        logs = new ArrayList<>(logService.getAllLogs());
                        logs = logs.stream()
                                .filter(x -> x.getDateTime().getMonthValue() == month &&
                                        x.getDateTime().getDayOfMonth() == day &&
                                        x.getDateTime().getHour() == hour)
                                .collect(Collectors.toList());
                        consoleViewer.showMagazineTitle();
                        consoleViewer.showAllLogsPage(logs);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 2:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterClientId();
                        int clientId = consoleInfoWriter.getId();
                        logs = new ArrayList<>(logService.getAllLogs());
                        logs = logs.stream().filter(x -> x.getClientId() == clientId).collect(Collectors.toList());
                        consoleViewer.showMagazineTitle();
                        consoleViewer.showAllLogsPage(logs);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 3:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterAction();
                        String action = consoleInfoWriter.getAction();
                        logs = new ArrayList<>(logService.getAllLogs());
                        logs = logs.stream().filter(x -> x.getAction().equals(action)).collect(Collectors.toList());
                        consoleViewer.showMagazineTitle();
                        consoleViewer.showAllLogsPage(logs);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;


            case ORDER_FILTER_STATE:
                consoleViewer.showAvaibleFiltersTitle();
                consoleViewer.showOrderOutputVariableMenu();
                consoleViewer.showEnterChoose();
                List<Order> orders;
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterClientId();
                        int clientId = consoleInfoWriter.getId();
                        orders = new ArrayList<>(orderService.getAllOrders());
                        orders = orders.stream().filter(x -> x.getClientId() == clientId).collect(Collectors.toList());
                        consoleViewer.showAvaibleOrdersTitle();
                        consoleViewer.showAllOrdersPage(orders);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 2:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterCarId();
                        int carId = consoleInfoWriter.getId();
                        orders = new ArrayList<>(orderService.getAllOrders());
                        orders = orders.stream().filter(x -> x.getCarId() == carId).collect(Collectors.toList());
                        consoleViewer.showAvaibleOrdersTitle();
                        consoleViewer.showAllOrdersPage(orders);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 3:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterOrderType();
                        String type = consoleInfoWriter.getOrderType();
                        orders = new ArrayList<>(orderService.getAllOrders());
                        orders = orders.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
                        consoleViewer.showAvaibleOrdersTitle();
                        consoleViewer.showAllOrdersPage(orders);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 4:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterMonth();
                        int month = consoleInfoWriter.getMonth();
                        consoleViewer.showEnterDay();
                        int day = consoleInfoWriter.getDay();
                        consoleViewer.showEnterHour();
                        int hour = consoleInfoWriter.getHour();
                        orders = new ArrayList<>(orderService.getAllOrders());
                        orders = orders.stream()
                                .filter(x -> x.getDate().getMonthValue() == month &&
                                        x.getDate().getDayOfMonth() == day &&
                                        x.getDate().getHour() == hour)
                                .collect(Collectors.toList());
                        consoleViewer.showAvaibleOrdersTitle();
                        consoleViewer.showAllOrdersPage(orders);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;

            case CAR_FILTER_STATE:
                consoleViewer.showAvaibleFiltersTitle();
                consoleViewer.showCarOutputVariableMenu();
                consoleViewer.showEnterChoose();
                List<Car> cars;
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterCarBrand();
                        String brand = consoleInfoWriter.getBrand();
                        cars = new ArrayList<>(carService.getAllCars());
                        cars = cars.stream().filter(x -> x.getBrand().equals(brand)).collect(Collectors.toList());
                        consoleViewer.showAvaibleCarsTitle();
                        consoleViewer.showAllCarsPage(cars);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 2:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterCarModel();
                        String model = consoleInfoWriter.getModel();
                        cars = new ArrayList<>(carService.getAllCars());
                        cars = cars.stream().filter(x -> x.getModel().equals(model)).collect(Collectors.toList());
                        consoleViewer.showAvaibleCarsTitle();
                        consoleViewer.showAllCarsPage(cars);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 3:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterCarPrice();
                        double price = consoleInfoWriter.getPrice();
                        cars = new ArrayList<>(carService.getAllCars());
                        cars = cars.stream().filter(x -> x.getPrice() == price).collect(Collectors.toList());
                        consoleViewer.showAvaibleCarsTitle();
                        consoleViewer.showAllCarsPage(cars);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 4:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterCarYear();
                        int year = consoleInfoWriter.getYearOfRelease();
                        cars = new ArrayList<>(carService.getAllCars());
                        cars = cars.stream().filter(x -> x.getYearOfRelease() == year).collect(Collectors.toList());
                        consoleViewer.showAvaibleCarsTitle();
                        consoleViewer.showAllCarsPage(cars);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;
            case CLIENT_SORT_STATE:
                consoleViewer.showAvaibleSortsTitle();
                consoleViewer.showClientOutputVariableMenu();
                consoleViewer.showEnterChoose();
                choose = consoleInfoWriter.getChoose();
                List<Client> clients;
                switch (choose){
                    case 1:
                        clients = new ArrayList<>(clientService.getAllClients());
                        clients.sort(Comparator.comparing(Client::getName));
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 2:
                        clients = new ArrayList<>(clientService.getAllClients());
                        clients.sort(Comparator.comparing(Client::getSurname));
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 3:
                        clients = new ArrayList<>(clientService.getAllClients());
                        clients.sort(Comparator.comparingInt(Client::getCountOfBuy));
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;
            case CLIENT_FILTER_STATE:
                consoleViewer.showAvaibleFiltersTitle();
                consoleViewer.showClientOutputVariableMenu();
                consoleViewer.showEnterChoose();
                choose = consoleInfoWriter.getChoose();
                switch (choose){
                    case 1:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterName();
                        String name = consoleInfoWriter.getName();
                        clients = new ArrayList<>(clientService.getAllClients());
                        clients = clients.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 2:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterSurname();
                        String surname = consoleInfoWriter.getSurname();
                        clients = new ArrayList<>(clientService.getAllClients());
                        clients = clients.stream().filter(x -> x.getSurname().equals(surname)).collect(Collectors.toList());
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 3:
                        consoleViewer.showEnterData();
                        consoleViewer.showEnterCountOfBuy();
                        int countOfBuy = consoleInfoWriter.getCountOfBuy();
                        clients = new ArrayList<>(clientService.getAllClients());
                        clients = clients.stream().filter(x -> x.getCountOfBuy() == countOfBuy).collect(Collectors.toList());
                        consoleViewer.showAvaibleClientsTitle();
                        consoleViewer.showAllClientsPage(clients);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;
        }
    }

    /**
     *
     * @param choose - переменная, хранящая выбор пользователя
     * @param user - пользователь, нужен для просмотри его роли
     * @return на основании роли пользователя и выбора, выдается true,
     * при правильном доступе, false, при выборе недоступного меню
     */
    public boolean checkChoose(int choose, Client user){
        switch (user.getRole()){
            case ADMIN: return choose <= 14;
            case MANAGER: return choose <= 9;
            case CLIENT: return choose <= 2;
        }
        return false;
    }

    /**
     * Сохраняет клиента, после регистрации. Так как в начале формируется пустой объект, после
     * он заполняется и заносится в БД. Если такой пользователь уже существует, то выводится ошибка
     * @param user
     */
    public void saveClientAfterRegistration(Client user){
        if(!clientService.isExist(user)) clientService.addClient(user);
        else throw new ClientIsExistException();
    }

    /**
     * Получение пользователя после авторизации. Так как в начале формируется пустой объект, после
     * объект заполняется КЛЮЧЕВЫМИ полями login,password,role. Для последующего поиска его в бд.
     * Если все найдено, то return client
     * Иначе выбрасываем ошибку.
     * @param tempUser
     * @return
     */
    public Client getClientAfterAuthorization(Client tempUser){
        if(clientService.isExist(tempUser)) {
            List<Client> tempClient = clientService.getAllClients();
            Client client = tempClient.stream().filter(x -> x.equals(tempUser)).toList().getFirst();
            return client;
        }
        else throw new NoSuchClientException();
    }

    /**
     * После поиска клиента в бд и его получении, заполняем остальные поля начального объекта с КЛЮЧЕВЫМИ
     * полями, контатной информацией.
     *  Картинка:  до метода Client { login, password, role }, после метода Client { name, surname,login,password,role,id,countOfBuy }
     * @param user
     * @param client
     */
    public void fillTheAuthorizationUserByClient(Client user,Client client){
        user.setRole(client.getRole());
        user.setSurname(client.getSurname());
        user.setName(client.getName());
        user.setId(client.getId());
        user.setCountOfBuy(client.getCountOfBuy());
    }



    public ApplicationFacade(){
        this.consoleViewer = new ConsoleViewer();
        this.consoleInfoWriter = new ConsoleInfoWriter();
        this.clientService = new ClientServiceImpl();
        this.carService = new CarServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.consoleEntityHandler = new ConsoleEntityHandler();
        this.logService = new LogServiceImpl();
    }
}
