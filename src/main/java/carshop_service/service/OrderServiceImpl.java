package carshop_service.service;

import carshop_service.dao.OrderDao;
import carshop_service.dao.OrderDaoImpl;
import carshop_service.entity.Order;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    public OrderServiceImpl(){
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    public void addOrder(Order order) {
        this.orderDao.addOrder(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderDao.getAllOrders();
    }

    @Override
    public Order getOrder(int id) {
        return this.orderDao.getOrder(id);
    }

    @Override
    public void updateOrder(Order order) {
        this.orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        this.orderDao.deleteOrder(order);
    }
}
