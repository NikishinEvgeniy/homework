package carshop_service.handler;

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


public interface EntityHandler {
    Client createClientByLoginPassword(Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectRoleException;
    Car createCarByBrandModelAndOther(Viewer consoleViewer, Writer consoleInfoWriter);
    Order createOrderByIdCarAndOther(Viewer consoleViewer, Writer consoleInfoWriter,
                                             CarService carService, ClientService clientService) throws NoSuchCarException, NoSuchClientException, IncorrectStateException;
    Car createUpdateCarByBrandModelAndOther(Car car, Viewer consoleViewer, Writer consoleInfoWriter);
    Order createUpdateOrderStatus(Order order, Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectStateException;
    void fillTheEmptyClientAuthorization(Client client, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException;
    Client updateClientDescription(Client oldClient, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException;
    void fillTheEmptyClientRegistration(Client client, Viewer consoleViewer, Writer consoleWriter) throws IncorrectRoleException;
    Client createFullClient(Viewer consoleViewer, Writer consoleInfoWriter) throws IncorrectRoleException;
}
