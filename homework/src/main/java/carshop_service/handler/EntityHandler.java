package carshop_service.handler;

import carshop_service.constant.LogAction;
import carshop_service.entity.Car;
import carshop_service.entity.Client;
import carshop_service.entity.Log;
import carshop_service.entity.Order;
import carshop_service.exception.*;
import carshop_service.in.Writer;
import carshop_service.out.Viewer;
import carshop_service.service.CarService;
import carshop_service.service.ClientService;


/**
 * Интерфейс, нужный для правильного соблюдения принципа DI
 * Методы - собирают сущности.
 */
public interface EntityHandler {
    Client createClientByLoginPassword(Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectRoleException;
    Car createCarByBrandModelAndOther(Viewer consoleViewer, Writer consoleInfoWriter);
    Order createOrderByIdCarAndOther(Viewer consoleViewer, Writer consoleInfoWriter,
                                             CarService carService, ClientService clientService) throws NoSuchEntityException, IncorrectStateException, IncorrectTypeException;
    Car createUpdateCarByBrandModelAndOther(Car car, Viewer consoleViewer, Writer consoleInfoWriter);
    Order createUpdateOrderStatus(Order order, Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectStateException;
    Client updateClientDescription(Client oldClient, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException;
    void fillTheEmptyClientRegistration(Client client, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException;
    Client createFullClient(Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectRoleException, IncorrectStateException;
    Log createLog(LogAction action, int id);
}
