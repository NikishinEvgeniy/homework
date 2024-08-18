package carshop_service.service;

import carshop_service.dao.OrderDao;
<<<<<<< Updated upstream
import carshop_service.dao.OrderDaoImpl;
import carshop_service.entity.Order;
=======
import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.mapper.OrderMapper;
import lombok.AllArgsConstructor;
>>>>>>> Stashed changes

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

<<<<<<< Updated upstream
    public OrderServiceImpl(){
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    public void addOrder(Order order) {
=======
    private final OrderDao orderDao;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;


    @Override
    public void addOrder(Order order) throws DuplicateEntityException {
        if (orderDao.getOrder(order.getId()) != null) throw new DuplicateEntityException("Order");
>>>>>>> Stashed changes
        this.orderDao.addOrder(order);
    }

    @Override
<<<<<<< Updated upstream
    public List<Order> getAllOrders() {
        return this.orderDao.getAllOrders();
    }

    @Override
    public Order getOrder(int id) {
        return this.orderDao.getOrder(id);
    }

    @Override
    public void updateOrder(Order order) {
=======
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
>>>>>>> Stashed changes
        this.orderDao.updateOrder(order);
    }

    @Override
<<<<<<< Updated upstream
    public void deleteOrder(Order order) {
        this.orderDao.deleteOrder(order);
=======
    public void deleteOrder(int id) throws NoSuchEntityException {
        if (orderDao.getOrder(id) == null) throw new NoSuchEntityException("Order");
        this.orderDao.deleteOrder(id);
>>>>>>> Stashed changes
    }
}
