package carshop_service.application;

import carshop_service.dao.CarDaoImpl;
import carshop_service.dao.ClientDaoImpl;
import carshop_service.dao.LogDaoImpl;
import carshop_service.dao.OrderDaoImpl;
import carshop_service.handler.ConsoleEntityHandler;
import carshop_service.in.ConsoleInfoWriter;
import carshop_service.out.ConsoleViewer;
import carshop_service.service.CarServiceImpl;
import carshop_service.service.ClientServiceImpl;
import carshop_service.service.LogServiceImpl;
import carshop_service.service.OrderServiceImpl;
import java.util.Scanner;

/**
 * Контекст приложения, там, где собирается все приложение.
 */
public class ApplicationContext {

    public void initialize(){
        ConfigLoader configLoader = new ConfigLoader("application.properties");
        DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration(
                configLoader.getProperty("car_service.url"),
                configLoader.getProperty("car_service.username"),
                configLoader.getProperty("car_service.password")
        );
        ApplicationFacade applicationFacade =
                new ApplicationFacade(
                        new LogServiceImpl(new LogDaoImpl(dataBaseConfiguration)),
                        new OrderServiceImpl(new OrderDaoImpl(dataBaseConfiguration)),
                        new CarServiceImpl(new CarDaoImpl(dataBaseConfiguration)),
                        new ClientServiceImpl(new ClientDaoImpl(dataBaseConfiguration)),
                        new ConsoleViewer(),
                        new ConsoleInfoWriter(new Scanner(System.in)),
                        new ConsoleEntityHandler()
                );
        applicationFacade.initialize();
    }
}
