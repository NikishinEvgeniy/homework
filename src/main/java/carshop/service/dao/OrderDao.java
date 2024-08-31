package carshop.service.dao;


import carshop.service.entity.Order;
import ya.lab.loggable_starter.annotation.Loggable;
import ya.lab.loggable_starter.constant.LogAction;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface OrderDao {
    @Loggable(action = LogAction.ADD_ORDER_ACTION)
    void addOrder(Order order);
    List<Order> getAllOrders();
    Order getOrder(int id);
    @Loggable(action = LogAction.UPDATE_ORDER_ACTION)
    void updateOrder(Order order);
    @Loggable(action = LogAction.DELETE_ORDER_ACTION)
    void deleteOrder(int id);
}
