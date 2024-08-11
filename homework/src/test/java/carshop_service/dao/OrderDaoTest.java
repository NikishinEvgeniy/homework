package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Order;
import carshop_service.entity.StandartOrderBuilder;
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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Testcontainers
public class OrderDaoTest {
    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("car_service")
            .withUsername("evgen")
            .withPassword("admin");
    private static OrderDao orderDao;

    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException {
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        ConfigLoader configLoader = new ConfigLoader(jdbcUrl,password,username);
        orderDao = new OrderDaoImpl(configLoader);
        Database database = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(
                        new JdbcConnection(DriverManager.getConnection(jdbcUrl,username,password))
                );
        Liquibase liquibase = new Liquibase("database/changelog.xml",new ClassLoaderResourceAccessor(),database);
        liquibase.update();
    }

    @Test
    @DisplayName("Заказ достается из базы данных")
    void getOrderTest(){
        int id = 2;
        Assertions.assertNotNull(orderDao.getOrder(id));
    }

    @Test
    @DisplayName("Заказ обновляется в базе данных")
    void updateOrderTest(){
        int id = 2;
        Order order = orderDao.getOrder(id);
        Order build = new StandartOrderBuilder()
                .dateTime(LocalDateTime.now())
                .id(order.getId())
                .build();
        orderDao.updateOrder(build);
        Assertions.assertNotEquals(order,orderDao.getOrder(id));
    }

    @Test
    @DisplayName("Заказ удаляется из базы данных")
    void getDeleteTest(){
        int id = 1;
        orderDao.deleteOrder(id);
        Assertions.assertNull(orderDao.getOrder(id));
    }

    @Test
    @DisplayName("Список заказов достается из базы данных")
    void getAllOrdersTest(){
        List<Order> orders = orderDao.getAllOrders();
        Assertions.assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("Заказ добавляется в базу данных")
    void addOrderTest(){
        String type = "TEST";
        Order order = new StandartOrderBuilder()
                .dateTime(LocalDateTime.now())
                .type(type)
                .build();
        orderDao.addOrder(order);
        Assertions.assertTrue(orderDao.getAllOrders().stream().anyMatch(x -> x.getType().equals(type)));
    }
}
