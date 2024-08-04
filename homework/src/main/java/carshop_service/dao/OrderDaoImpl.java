package carshop_service.dao;

import carshop_service.constant.OrderState;
import carshop_service.constant.OrderType;
import carshop_service.entity.Order;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private HashMap<Integer, Order> orders;
    public OrderDaoImpl(){
        this.orders = new HashMap<>();
        Order order1 = new Order(0,0, OrderState.OK, OrderType.PROCESSING, LocalDateTime.now());
        Order order2 = new Order(1,1, OrderState.OK, OrderType.SALE, LocalDateTime.now());
        Order order3 = new Order(2,2, OrderState.OK, OrderType.SALE, LocalDateTime.now());
        Order order4 = new Order(3,3, OrderState.OK, OrderType.PROCESSING, LocalDateTime.now());
        this.orders.put(order1.getId(),order1);
        this.orders.put(order2.getId(),order2);
        this.orders.put(order3.getId(),order3);
        this.orders.put(order4.getId(),order4);
    }

    @Override
    public void addOrder(Order order){
        this.orders.put(order.getId(),order);
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orders.values().stream().toList();
    }

    @Override
    public Order getOrder(int id) {
        return this.orders.get(id);
    }

    @Override
    public void updateOrder(Order order) {
        this.orders.put(order.getId(),order);
    }

    @Override
    public void deleteOrder(Order order) {
        this.orders.remove(order.getId());
    }
}
