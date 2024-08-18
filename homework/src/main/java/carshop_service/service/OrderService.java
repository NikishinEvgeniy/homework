package carshop_service.service;

import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;

import java.util.List;

public interface OrderService {
<<<<<<< Updated upstream
    void addOrder(Order order);
    List<Order> getAllOrders();
    Order getOrder(int id);
    void updateOrder(Order order);
    void deleteOrder(Order order);
=======
    void addOrder(Order order) throws DuplicateEntityException;
    List<OrderDto> getAllOrders() throws DataBaseEmptyException;
    OrderDto getOrder(int id) throws NoSuchEntityException;
    void updateOrder(Order order) throws NoSuchEntityException;
    void deleteOrder(int id) throws NoSuchEntityException;
>>>>>>> Stashed changes
}
