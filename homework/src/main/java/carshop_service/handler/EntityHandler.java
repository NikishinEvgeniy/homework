package carshop_service.handler;

import carshop_service.entity.Car;
import carshop_service.entity.Client;
import carshop_service.entity.Order;
import carshop_service.in.Writer;
import carshop_service.out.Viewer;
import carshop_service.service.CarService;
import carshop_service.service.ClientService;


public interface EntityHandler {
    Client createClientByLoginPassword(Viewer consoleViewer, Writer consoleInfoWriter);
    Car createCarByBrandModelAndOther(Viewer consoleViewer, Writer consoleInfoWriter);
    Order createOrderByIdCarAndOther(Viewer consoleViewer, Writer consoleInfoWriter,
                                             CarService carService, ClientService clientService);
    Car createUpdateCarByBrandModelAndOther(Car car, Viewer consoleViewer, Writer consoleInfoWriter);
    Order createUpdateOrderStatus(Order order, Viewer consoleViewer, Writer consoleInfoWriter);
    void fillTheEmptyClientAuthorization(Client client, Viewer consoleViewer, Writer consoleWriter);
    Client updateClientDescription(Client oldClient, Viewer consoleViewer, Writer consoleWriter);
    void fillTheEmptyClientRegistration(Client client, Viewer consoleViewer, Writer consoleWriter);
    Client createFullClient(Viewer consoleViewer, Writer consoleInfoWriter);
}
