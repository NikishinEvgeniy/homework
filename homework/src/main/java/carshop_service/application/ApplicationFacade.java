package carshop_service.application;

import carshop_service.constant.LogAction;
import carshop_service.constant.OrderType;
import carshop_service.entity.*;
import carshop_service.exception.*;
import carshop_service.handler.EntityHandler;
import carshop_service.in.Writer;
import carshop_service.out.Viewer;
import carshop_service.service.*;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static carshop_service.constant.ClientState.*;

@AllArgsConstructor
public class ApplicationFacade {

    private final LogService logService;
    private final OrderService orderService;
    private final CarService carService;
    private final ClientService clientService;
    private final Viewer consoleViewer;
    private final Writer consoleInfoWriter;
    private final EntityHandler consoleEntityHandler;

    /**
     * В постоянном режиме проверяет состояние пользователя.
     * Все состояния можно увидеть в пакете constant
     */
 public void initialize(){
        Client user = Client.builder()
                .state(BEGIN_STATE)
                .build();
        while (true){
            try {
                checkState(user);
            } catch (NoSuchEntityException | NoSuchChooseException |
                     ClientIsExistException | IncorrectRoleException | IncorrectStateException |
                     IOException | DataBaseEmptyException | DuplicateEntityException |
                     IncorrectTypeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     *
     * @param user -пользователь, который сейчас использует программу. Со своим состоянием
     * Изначально создается пустой пользователь, только с начальным состоянием
     * В зависимости от регистрации и авторизации происходит заполнение данного объекта
     * При регистрации, созданный объект просто заполняется.
     * При авторизации, в созданном объекте заполняются поля
     * login, password, role -> после чего происходит поиск в БД.
     * При нахождении пользователя, его контактная информация копируется в ранее созданный объект.
     * Идея приложения - проверка состояний пользователя и последующее их изменение при выборе определенного пункта
     * @throws IOException
     * @throws NoSuchEntityException
     * @throws NoSuchChooseException
     * @throws ClientIsExistException
     * @throws IncorrectRoleException
     * @throws IncorrectStateException
     *
     */
    public void checkState(Client user) throws IOException,
            NoSuchEntityException, NoSuchChooseException,
            ClientIsExistException, IncorrectRoleException, IncorrectStateException, DataBaseEmptyException, DuplicateEntityException, IncorrectTypeException {
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

            case REGISTRATION_STATE: // *
                consoleViewer.showRegistrationTitle();
                consoleEntityHandler.fillTheEmptyClientRegistration(user,consoleViewer,consoleInfoWriter);
                saveClientAfterRegistration(user);
                user.setState(AUTHORIZATION_STATE);
                logService.addLog( consoleEntityHandler.createLog(LogAction.REGISTRATION_ACTION, user.getId()));
                break;

            case AUTHORIZATION_STATE: // *
                consoleViewer.showAuthorizationTitle();
                Client tempUser = consoleEntityHandler.createClientByLoginPassword(consoleViewer,consoleInfoWriter);
                Client client = getClientAfterAuthorization(tempUser);
                fillTheAuthorizationUserByClient(user,client);
                user.setState(CHOOSE_MAIN_MENU_STATE);
                logService.addLog( consoleEntityHandler.createLog(LogAction.AUTHORIZATION_ACTION, user.getId()));

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

            case MENU_CHOOSE_STATE: // *
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
                        consoleViewer.showSimpleCar(carUpOld);
                        consoleViewer.showUpdateTitle();
                        Car carUpNew = consoleEntityHandler
                                .createUpdateCarByBrandModelAndOther(carUpOld,consoleViewer,consoleInfoWriter);
                        carService.updateCar(carUpNew);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.UPDATE_CAR_ACTION, user.getId()));

                        break;
                    case 4:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId();
                        carService.deleteCar(id);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.DELETE_CAR_ACTION, user.getId()));

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
                        logService.addLog( consoleEntityHandler.createLog(LogAction.ADD_ORDER_ACTION, user.getId()));
                        break;
                    case 6:
                        Car carAdd = consoleEntityHandler.createCarByBrandModelAndOther(consoleViewer,consoleInfoWriter);
                        carService.addCar(carAdd);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.ADD_CAR_ACTION, user.getId()));
                        break;
                    case 7:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId();
                        Order orderFromDb = orderService.getOrder(id);
                        consoleViewer.showSimpleOrder(orderFromDb);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    case 8:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId();
                        Order orderUpOld = orderService.getOrder(id);
                        consoleViewer.showSimpleOrder(orderUpOld);
                        consoleViewer.showUpdateTitle();
                        Order orderUpNew = consoleEntityHandler
                                .createUpdateOrderStatus(orderUpOld,consoleViewer,consoleInfoWriter);
                        orderService.updateOrder(orderUpNew);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.UPDATE_ORDER_ACTION, user.getId()));
                        break;
                    case 9:
                        consoleViewer.showEnterId();
                        id = consoleInfoWriter.getId();
                        orderService.deleteOrder(id);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.CANCEL_ORDER_ACTION, user.getId()));
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
                        id = consoleInfoWriter.getId();
                        client = clientService.getClient(id);
                        consoleViewer.showSimpleClient(client);
                        consoleViewer.showUpdateTitle();
                        consoleEntityHandler
                                .updateClientDescription(client,consoleViewer,consoleInfoWriter) ;
                        clientService.updateClient(client);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.UPDATE_CLIENT_ACTION, user.getId()));
                        break;
                    case 12:
                        Client client1 = consoleEntityHandler.createFullClient(consoleViewer,consoleInfoWriter);
                        clientService.isExist(client1);
                        clientService.addClient(client1);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        logService.addLog( consoleEntityHandler.createLog(LogAction.ADD_CLIENT_ACTION, user.getId()));
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

            case MAGAZINE_FILTER_STATE: // *
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
                        logs = logs.stream().filter(x -> x.getAction().toString().equals(action)).collect(Collectors.toList());
                        consoleViewer.showMagazineTitle();
                        consoleViewer.showAllLogsPage(logs);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;


            case ORDER_FILTER_STATE: // *
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
                        orders = orders.stream().filter(x -> x.getType().toString().equals(type)).collect(Collectors.toList());
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
                                .filter(x -> x.getDateTime().getMonthValue() == month &&
                                        x.getDateTime().getDayOfMonth() == day &&
                                        x.getDateTime().getHour() == hour)
                                .collect(Collectors.toList());
                        consoleViewer.showAvaibleOrdersTitle();
                        consoleViewer.showAllOrdersPage(orders);
                        consoleViewer.showSuccessTitle();
                        user.setState(CHOOSE_MAIN_MENU_STATE);
                        break;
                    default: throw new NoSuchChooseException();
                }
                break;

            case CAR_FILTER_STATE: // *
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

            case CLIENT_SORT_STATE: // *
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

            case CLIENT_FILTER_STATE: // *
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
        return switch (user.getRole()) {
            case ADMIN -> choose <= 14;
            case MANAGER -> choose <= 9;
            case CLIENT -> choose <= 2;
        };
    }

    /**
     * Сохраняет клиента, после регистрации. Так как в начале формируется пустой объект, после
     * он заполняется и заносится в БД. Если такой пользователь уже существует, то выводится ошибка
     * @param user
     */
    public void saveClientAfterRegistration(Client user) throws NoSuchEntityException, DuplicateEntityException {
        clientService.isExist(user);
        clientService.addClient(user);
    }

    /**
     * Получение пользователя после авторизации. Так как в начале формируется пустой объект, после
     * объект заполняется КЛЮЧЕВЫМИ полями login,password,role. Для последующего поиска его в бд.
     * Если все найдено, то return client
     * Иначе выбрасываем ошибку.
     * @param tempUser
     * @return
     */
    public Client getClientAfterAuthorization(Client tempUser) throws NoSuchEntityException, DataBaseEmptyException {
            clientService.isExist(tempUser);
            List<Client> tempClient = clientService.getAllClients();
            Client client = tempClient.stream().filter(x -> x.equals(tempUser)).toList().getFirst();
            return client;
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
}
