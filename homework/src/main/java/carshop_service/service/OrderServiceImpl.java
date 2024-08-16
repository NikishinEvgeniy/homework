package carshop_service.service;

import carshop_service.dao.OrderDao;
import carshop_service.entity.Order;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Промежуточное звено между приложением и dao
 * Обрабатывает ошибки, которые могут возникнуть при работе с order dao
 */
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Override
    public void addOrder(Order order) throws DuplicateEntityException {
        if(orderDao.getOrder(order.getId()) != null) throw new DuplicateEntityException("Order");
        this.orderDao.addOrder(order);
    }

    @Override
    public List<Order> getAllOrders() throws DataBaseEmptyException {
        List<Order> orders = orderDao.getAllOrders();
        if(orders.isEmpty()) throw new DataBaseEmptyException("Order");
        return orders;
    }

    @Override
    public Order getOrder(int id) throws NoSuchEntityException {
        Order order = orderDao.getOrder(id);
        if(order == null) throw new NoSuchEntityException("Order");
        return order;
    }

    @Override
    public void updateOrder(Order order) throws NoSuchEntityException {
        if(orderDao.getOrder(order.getId()) == null) throw  new NoSuchEntityException("Order");
        this.orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrder(int id) throws NoSuchEntityException{
        if(orderDao.getOrder(id) == null) throw  new NoSuchEntityException("Order");
        this.orderDao.deleteOrder(id);
    }
}
