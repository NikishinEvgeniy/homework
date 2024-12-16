package carshop.service.service;


import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;

import java.util.List;

public interface OrderService {

    void addOrder(Order order) throws DuplicateEntityException;
    List<OrderDto> getAllOrders() throws DataBaseEmptyException;
    OrderDto getOrder(int id) throws NoSuchEntityException;
    void updateOrder(Order order) throws NoSuchEntityException;
    void deleteOrder(int id) throws NoSuchEntityException;

}
