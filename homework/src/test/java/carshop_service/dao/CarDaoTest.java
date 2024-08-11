package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Car;
import carshop_service.entity.StandartCarBuilder;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public class CarDaoTest {
    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("car_serivce")
            .withUsername("evgen")
            .withPassword("admin");
    private static CarDao carDao;


    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException {
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        ConfigLoader configLoader = new ConfigLoader(jdbcUrl,password,username);
        carDao = new CarDaoImpl(configLoader);
        Connection connection = DriverManager.getConnection(jdbcUrl,username,password);
        Database database = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("database/changelog.xml",new ClassLoaderResourceAccessor(),database);
        liquibase.update();
    }

    @Test
    @DisplayName("Машина добавляется в базу данных")
    void addCarTest(){
        String brand = "TEST";
        carDao.addCar(
                new StandartCarBuilder()
                .brand(brand)
                .build()
        );
        Assertions.assertTrue(carDao.getAllCars().stream().anyMatch(x -> x.getBrand().equals(brand)));
    }

    @Test
    @DisplayName("Машина удаляется из базы данных")
    void deleteCarTest(){
        int id = 1;
        carDao.deleteCar(id);
        Assertions.assertNull(carDao.getCar(id));
    }

    @Test
    @DisplayName("Машина обновляется в базе данных")
    void updateCarTest(){
        int id = 2;
        Car carFromDao = carDao.getCar(id);
        Car car = new StandartCarBuilder()
        .id(id)
        .brand("UPDATE")
        .price(200)
        .model("UPDATE")
        .build();
        carDao.updateCar(car);
        Assertions.assertNotEquals(carFromDao,carDao.getCar(id));
    }

    @Test
    @DisplayName("Машина достается из базы данных")
    void getCarTest(){
        int id = 2;
        Assertions.assertNotNull(carDao.getCar(2));
    }
}
