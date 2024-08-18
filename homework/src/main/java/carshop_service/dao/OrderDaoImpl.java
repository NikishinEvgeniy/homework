package carshop_service.dao;

import carshop_service.constant.OrderState;
import carshop_service.constant.OrderType;
import carshop_service.entity.Order;
<<<<<<< Updated upstream

=======
import lombok.AllArgsConstructor;

import java.sql.*;
>>>>>>> Stashed changes
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

<<<<<<< Updated upstream
=======

/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных orders
 */
@AllArgsConstructor
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        this.orders.put(order.getId(),order);
=======
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.ADD_ORDER_QUERY);
            preparedStatement.setInt(1,order.getClientId());
            preparedStatement.setInt(2,order.getCarId());
            if(order.getState() != null) preparedStatement.setString(3,order.getState().toString());
            else preparedStatement.setString(3,null);
            if(order.getType() != null) preparedStatement.setString(4,order.getType().toString());
            else preparedStatement.setString(4,null);
            if(order.getDateTime() != null) preparedStatement.setString(5,order.getDateTime().toString());
            else preparedStatement.setString(5,null);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        this.orders.put(order.getId(),order);
=======
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_ORDER_QUERY);
            preparedStatement.setInt(1,order.getClientId());
            preparedStatement.setInt(2,order.getCarId());
            if(order.getState() != null) preparedStatement.setString(3,order.getState().toString());
            else preparedStatement.setString(3,null);
            if(order.getType() != null) preparedStatement.setString(4,order.getType().toString());
            else preparedStatement.setString(4,null);
            if(order.getDateTime() != null) preparedStatement.setString(5,order.getDateTime().toString());
            else preparedStatement.setString(5,null);
            preparedStatement.setInt(6,order.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
>>>>>>> Stashed changes
    }

    @Override
    public void deleteOrder(Order order) {
        this.orders.remove(order.getId());
    }
}
