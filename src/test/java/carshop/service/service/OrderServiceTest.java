package carshop.service.service;

import carshop.service.dao.OrderDao;
import carshop.service.dao.OrderDaoImpl;
import carshop.service.entity.Order;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.mapper.OrderMapper;
import carshop.service.mapper.OrderMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.LinkedList;

public class OrderServiceTest {
    private OrderDao orderDao;
    private OrderService orderService;
    private OrderMapper orderMapper;

    public OrderServiceTest(){
        this.orderDao = Mockito.mock(OrderDaoImpl.class);
        this.orderMapper = Mockito.mock(OrderMapperImpl.class);
        this.orderService = new OrderServiceImpl(orderDao,orderMapper);
    }

    @Test
    @DisplayName(" Ошибка, при получении заказа из БД (заказ не найден) ")
    public void getOrderTest(){
        int id = 10;
        Mockito.when(orderDao.getOrder(id))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->orderService.getOrder(id));
    }

    @Test
    @DisplayName(" Ошибка, при получении списка заказов из БД (список заказов пуст) ")
    public void getAllOrdersTest(){
        Mockito.when(orderDao.getAllOrders())
                .thenReturn(new LinkedList<>());
        Assertions.assertThrows(DataBaseEmptyException.class,()->orderService.getAllOrders());
    }

    @Test
    @DisplayName(" Ошибка, при добавлении заказа в БД (дубликат) ")
    public void addOrderTest(){
        int id = 10;
        Order order = Order.builder()
                .id(id)
                .build();
        Mockito.when(orderDao.getOrder(order.getId()))
                .thenReturn(order);
        Assertions.assertThrows(DuplicateEntityException.class,()->orderService.addOrder(order));
    }

    @Test
    @DisplayName(" Ошибка, при обновлении заказа в БД (заказ не найден) ")
    public void updateOrderTest(){
        int id = 10;
        Order order = Order.builder()
                .id(id)
                .build();
        Mockito.when(orderDao.getOrder(order.getId()))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->orderService.updateOrder(order));
    }

    @Test
    @DisplayName(" Ошибка, при удалении заказа в БД (заказ не найден) ")
    public void deleteOrderTest(){
        int id = 10;
        Mockito.when(orderDao.getOrder(id))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->orderService.deleteOrder(id));
    }
}
