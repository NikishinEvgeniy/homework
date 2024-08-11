package carshop_service.dao;

import carshop_service.entity.Order;
import java.util.List;

public interface OrderDao {
    void addOrder(Order order);
    List<Order> getAllOrders();
    Order getOrder(int id);
    void updateOrder(Order order);
    void deleteOrder(int id);
}
