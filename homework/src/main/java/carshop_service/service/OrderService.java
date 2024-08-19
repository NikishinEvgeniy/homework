package carshop_service.service;

import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;

import java.util.List;

public interface OrderService {

    void addOrder(Order order) throws DuplicateEntityException;
    List<OrderDto> getAllOrders() throws DataBaseEmptyException;
    OrderDto getOrder(int id) throws NoSuchEntityException;
    void updateOrder(Order order) throws NoSuchEntityException;
    void deleteOrder(int id) throws NoSuchEntityException;

}
