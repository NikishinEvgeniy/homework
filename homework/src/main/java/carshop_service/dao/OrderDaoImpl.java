package carshop_service.dao;

import carshop_service.application.DataBaseConfiguration;
import carshop_service.constant.OrderState;
import carshop_service.constant.OrderType;
import carshop_service.constant.SqlQuery;
import carshop_service.entity.Order;
import lombok.AllArgsConstructor;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных orders
 */

@AllArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final DataBaseConfiguration database;

    @Override
    public void addOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.ADD_ORDER_QUERY);
            preparedStatement.setInt(1,order.getClientId());
            preparedStatement.setInt(2,order.getCarId());
            preparedStatement.setString(3,order.getState().toString());
            preparedStatement.setString(4,order.getType().toString());
            preparedStatement.setString(5,order.getDateTime().toString());
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
    }

    @Override
    public List<Order> getAllOrders() {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        List<Order> orders = new LinkedList<>();
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SqlQuery.GET_ORDERS_QUERY);
            while (resultSet.next()){
                orders.add(
                                Order.builder()
                                .id(resultSet.getInt("id"))
                                .clientId(resultSet.getInt("client_id"))
                                .carId(resultSet.getInt("car_id"))
                                .state(OrderState.valueOf(resultSet.getString("state")))
                                .type(OrderType.valueOf(resultSet.getString("type")))
                                .dateTime(LocalDateTime.parse(resultSet.getString("date_time")))
                                .build()
                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return orders;
    }

    @Override
    public Order getOrder(int id) {
        Order order = null;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.GET_ORDER_QUERY);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = Order.builder()
                        .id(resultSet.getInt("id"))
                        .carId(resultSet.getInt("car_id"))
                        .clientId(resultSet.getInt("client_id"))
                        .state(OrderState.valueOf(resultSet.getString("state")))
                        .type(OrderType.valueOf(resultSet.getString("type")))
                        .dateTime(LocalDateTime.parse(resultSet.getString("date_time")))
                        .build();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(preparedStatement != null) preparedStatement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_ORDER_QUERY);
            preparedStatement.setInt(1,order.getClientId());
            preparedStatement.setInt(2,order.getCarId());
            preparedStatement.setString(3,order.getState().toString());
            preparedStatement.setString(4,order.getType().toString());
            preparedStatement.setString(5,order.getDateTime().toString());
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
    }

    @Override
    public void deleteOrder(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            preparedStatement = connection.prepareStatement(SqlQuery.DELETE_ORDER_QUERY);
            preparedStatement.setInt(1,id);
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
    }
}
