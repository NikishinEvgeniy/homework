package carshop.service.service;

import carshop.service.dao.OrderDao;
import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис, обрабатывающий ошибки, при работе с базой данных
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderMapper orderMapper) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    @Override
    public void addOrder(Order order) throws DuplicateEntityException {
        if (orderDao.getOrder(order.getId()) != null) throw new DuplicateEntityException("Order");
        this.orderDao.addOrder(order);
    }

    @Override
    public List<OrderDto> getAllOrders() throws DataBaseEmptyException {
        List<Order> orders = orderDao.getAllOrders();
        if (orders.isEmpty()) throw new DataBaseEmptyException("Order");
        return orderMapper.listOfOrdersToOrdersDto(orders);
    }

    @Override
    public OrderDto getOrder(int id) throws NoSuchEntityException {
        Order order = orderDao.getOrder(id);
        if (order == null) throw new NoSuchEntityException("Order");
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public void updateOrder(Order order) throws NoSuchEntityException {
        if (orderDao.getOrder(order.getId()) == null) throw new NoSuchEntityException("Order");
        this.orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrder(int id) throws NoSuchEntityException {
        if (orderDao.getOrder(id) == null) throw new NoSuchEntityException("Order");
        this.orderDao.deleteOrder(id);
    }
}
