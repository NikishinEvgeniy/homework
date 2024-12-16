package carshop_service.service;

import carshop_service.dao.OrderDao;
import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.mapper.OrderMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

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
