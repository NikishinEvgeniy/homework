package carshop.service.dao;

import carshop.service.application.DataBaseConfiguration;
import carshop.service.constant.OrderState;
import carshop.service.constant.OrderType;
import carshop.service.container.PostgreSQLContainerConfiguration;
import carshop.service.entity.Order;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.List;


public class OrderDaoTest {
    private static PostgreSQLContainer<?> postgreContainer;
    private static OrderDao orderDao;

    @BeforeAll
    public static void setUp() {
        postgreContainer = new PostgreSQLContainerConfiguration().getPostgreSQLContainer();
        String password = postgreContainer.getPassword();
        String username = postgreContainer.getUsername();
        DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration(postgreContainer.getJdbcUrl()
                ,username,password);
        orderDao = new OrderDaoImpl(dataBaseConfiguration);
    }


    @AfterAll
    public static void closeConnection(){
        postgreContainer.close();
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
        Order build = Order.builder()
                .dateTime(LocalDateTime.now())
                .type(OrderType.PROCESSING)
                .state(OrderState.UPDATE)
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
        Order order = Order.builder()
                .dateTime(LocalDateTime.now())
                .clientId(10)
                .type(OrderType.SALE)
                .state(OrderState.UPDATE)
                .build();
        orderDao.addOrder(order);
        Assertions.assertTrue(orderDao.getAllOrders().stream().anyMatch(x -> x.getClientId() == 10));
    }
}
