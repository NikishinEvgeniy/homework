package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Order;
import carshop_service.entity.StandartOrderBuilder;
import lombok.AllArgsConstructor;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private ConfigLoader configLoader;

    @Override
    public void addOrder(Order order){
        Connection connection = configLoader.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String addOrderQuery = "INSERT INTO orders (client_id,car_id,state,type,date_time) " +
                    " VALUES(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(addOrderQuery);
            preparedStatement.setInt(1,order.getClientId());
            preparedStatement.setInt(2,order.getCarId());
            preparedStatement.setString(3,order.getState());
            preparedStatement.setString(4,order.getType());
            preparedStatement.setString(5,order.getDateTime().toString());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<Order> getAllOrders() {
        Connection connection = configLoader.getConnection();
        List<Order> orders = new LinkedList<>();
        Statement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getOrdersQuery = "SELECT * FROM orders";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getOrdersQuery);
            while (resultSet.next()){
                orders.add(
                        new StandartOrderBuilder()
                                .id(resultSet.getInt("id"))
                                .clientId(resultSet.getInt("client_id"))
                                .carId(resultSet.getInt("car_id"))
                                .state(resultSet.getString("state"))
                                .type(resultSet.getString("type"))
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
                connection.close();
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return orders;
    }

    @Override
    public Order getOrder(int id) {
        Connection connection = configLoader.getConnection();
        PreparedStatement preparedStatement = null;
        Order order = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getOrderQuery = "SELECT * FROM orders WHERE id = ?";
            preparedStatement = connection.prepareStatement(getOrderQuery);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            order = new StandartOrderBuilder()
                    .id(resultSet.getInt("id"))
                    .carId(resultSet.getInt("car_id"))
                    .clientId(resultSet.getInt("client_id"))
                    .state(resultSet.getString("state"))
                    .type(resultSet.getString("type"))
                    .dateTime(LocalDateTime.parse(resultSet.getString("date_time")))
                    .build();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        Connection connection = configLoader.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String updateOrderQuery = "UPDATE orders " +
                    " SET client_id = ?, car_id = ?,state = ?, type = ?, date_time = ? " +
                    " WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateOrderQuery);
            preparedStatement.setInt(1,order.getClientId());
            preparedStatement.setInt(2,order.getCarId());
            preparedStatement.setString(3,order.getState());
            preparedStatement.setString(4,order.getType());
            preparedStatement.setString(5,order.getDateTime().toString());
            preparedStatement.setInt(6,order.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteOrder(int id) {
        Connection connection = configLoader.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getOrderQuery = "DELETE FROM orders WHERE id = ?";
            preparedStatement = connection.prepareStatement(getOrderQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
